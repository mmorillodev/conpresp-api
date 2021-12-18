package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.Specifications.patrimony.PatrimonySearchCriteria;
import com.conpresp.conprespapi.Specifications.patrimony.PatrimonySpecifications;
import com.conpresp.conprespapi.dto.patrimony.request.PatrimonyCreateRequest;
import com.conpresp.conprespapi.dto.patrimony.request.PatrimonyUpdateRequest;
import com.conpresp.conprespapi.dto.patrimony.response.PatrimonyBasicInfoResponse;
import com.conpresp.conprespapi.dto.patrimony.response.PatrimonyDetailsResponse;
import com.conpresp.conprespapi.service.PatrimonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/patrimony")
public class PatrimonyController {

    @Autowired
    private PatrimonyService patrimonyService;

    @PostMapping
    public ResponseEntity<?> createPatrimony(
            @Valid @RequestBody PatrimonyCreateRequest patrimonyCreateRequest,
            UriComponentsBuilder uriComponentsBuilder) {
        var id = patrimonyService.createPatrimony(patrimonyCreateRequest);
        var uri = uriComponentsBuilder.path("/patrimony/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public Page<PatrimonyBasicInfoResponse> search(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam MultiValueMap<String, String> params
    ) {
        PatrimonySearchCriteria searchCriteria = PatrimonySearchCriteria.builder()
                .denomination(params.getFirst("denomination"))
                .resolution(params.getFirst("resolution"))
                .originalUsage(params.getFirst("originalUsage"))
                .addressType(params.getFirst("addressType"))
                .addressTitle(params.getFirst("addressTitle"))
                .street(params.getFirst("street"))
                .address(params.getFirst("address"))
                .addressNumber(params.getFirst("addressNumber"))
                .district(params.getFirst("district"))
                .regionalHall(params.getFirst("regionalHall"))
                .author(params.getFirst("author"))
                .constructionYear(params.getFirst("constructionYear"))
                .architecturalStyle(params.getFirst("architecturalStyle"))
                .conservationLevel(params.getFirst("conservationLevel"))
                .modificationLevel(params.getFirst("modificationLevel"))
                .createdBy(params.getFirst("createdBy"))
                .build();

        var specification = PatrimonySpecifications.search(searchCriteria);
        var patrimonies = patrimonyService.search(specification, pageable);

        return patrimonies.map(PatrimonyBasicInfoResponse::fromPatrimony);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PatrimonyDetailsResponse> getPatrimonyByUuid(@PathVariable String uuid) {
        return patrimonyService.getPatrimonyById(uuid).map(patrimonies ->
                ResponseEntity.ok().body(PatrimonyDetailsResponse.fromPatrimony(patrimonies))
        ).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PatrimonyDetailsResponse> updatePatrimonyById(
            @PathVariable String uuid, @RequestBody @Valid PatrimonyUpdateRequest patrimonyUpdateRequest) {

        try {
            var updatedPatrimony = patrimonyService.updatePatrimony(uuid, patrimonyUpdateRequest);
            return ResponseEntity.ok(PatrimonyDetailsResponse.fromPatrimony(updatedPatrimony));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletePatrimonyByUuid(@PathVariable @Valid String uuid) {
        return patrimonyService.getPatrimonyById(uuid).map(patrimonies -> {
            patrimonyService.deleteById(uuid);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
