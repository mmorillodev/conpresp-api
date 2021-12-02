package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.Specifications.Property.PropertySearchCriteria;
import com.conpresp.conprespapi.Specifications.Property.PropertySpecifications;
import com.conpresp.conprespapi.dto.property.request.PropertyCreateRequest;
import com.conpresp.conprespapi.dto.property.request.PropertyUpdateRequest;
import com.conpresp.conprespapi.dto.property.response.PropertyBasicInfoResponse;
import com.conpresp.conprespapi.dto.property.response.PropertyDetailsResponse;
import com.conpresp.conprespapi.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<?> createProperty(
            @Valid @RequestBody PropertyCreateRequest propertyCreateRequest,
            UriComponentsBuilder uriComponentsBuilder) {
        var id = propertyService.createProperty(propertyCreateRequest);
        var uri = uriComponentsBuilder.path("/property/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public Page<PropertyBasicInfoResponse> getAllProperty(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        var property = propertyService.getAllProperty(pageable);

        return property.map(PropertyBasicInfoResponse::fromProperty);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PropertyDetailsResponse> getPropertyByUuid(@PathVariable String uuid) {
        return propertyService.getPropertyById(uuid).map(property ->
                ResponseEntity.ok().body(PropertyDetailsResponse.fromProperty(property))
        ).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public Page<PropertyBasicInfoResponse> search(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
             @RequestParam(value = "designation", required = false) String designation,
             @RequestParam(value = "resolution", required = false) String resolution,
             @RequestParam(value = "originalUsage", required = false) String originalUsage,
             @RequestParam(value = "addressType", required = false) String addressType,
             @RequestParam(value = "addressTitle", required = false) String addressTitle,
             @RequestParam(value = "street", required = false) String street,
             @RequestParam(value = "addressNumber", required = false) String addressNumber,
             @RequestParam(value = "district", required = false) String district,
             @RequestParam(value = "regionalHall", required = false) String regionalHall,
             @RequestParam(value = "author", required = false) String author,
             @RequestParam(value = "constructionYear", required = false) String constructionYear,
             @RequestParam(value = "architecturalStyle", required = false) String architecturalStyle
    ) {
            PropertySearchCriteria searchCriteria = PropertySearchCriteria.builder()
                    .designation(designation)
                    .resolution(resolution)
                    .originalUsage(originalUsage)
                    .addressType(addressType)
                    .addressTitle(addressTitle)
                    .street(street)
                    .addressNumber(addressNumber)
                    .district(district)
                    .regionalHall(regionalHall)
                    .author(author)
                    .constructionYear(constructionYear)
                    .architecturalStyle(architecturalStyle)
                    .build();

            var specification = PropertySpecifications.search(searchCriteria);
            var property = propertyService.search(specification, pageable);

            return property.map(PropertyBasicInfoResponse::fromProperty);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PropertyDetailsResponse> updatePropertyById(
            @PathVariable String uuid, @RequestBody @Valid PropertyUpdateRequest propertyUpdateRequest) {

        try {
            var updatedProperty = propertyService.updateProperty(uuid, propertyUpdateRequest);
            return ResponseEntity.ok(PropertyDetailsResponse.fromProperty(updatedProperty));
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletePropertyByUuid(@PathVariable @Valid String uuid) {
        return propertyService.getPropertyById(uuid).map(property -> {
            propertyService.deleteById(uuid);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
