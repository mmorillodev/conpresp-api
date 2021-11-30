package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.dto.property.request.PropertyCreateRequest;
import com.conpresp.conprespapi.dto.property.request.PropertyUpdateRequest;
import com.conpresp.conprespapi.dto.property.response.PropertyListResponse;
import com.conpresp.conprespapi.dto.property.response.PropertyResponse;
import com.conpresp.conprespapi.exception.ResourceCreationException;
import com.conpresp.conprespapi.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping
    public ResponseEntity<?> createProperty(
            @Valid @RequestBody PropertyCreateRequest propertyCreateRequest,
            UriComponentsBuilder uriComponentsBuilder) {
        try {
            var id = propertyService.createProperty(propertyCreateRequest);

            var uri = uriComponentsBuilder.path("/property/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(uri).build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getHomePage(@RequestParam("size") String size) {
        return ResponseEntity.ok(propertyService.propertyPage(Integer.parseInt(size)));
    }

    @GetMapping("all")
    public PropertyListResponse getAllProperty() {
        var property = propertyService.getAllProperty();

        return new PropertyListResponse(
          property.stream().map(PropertyResponse::fromProperty).collect(Collectors.toList()),
          property.size()
        );
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<?> getPropertyByUuid(@PathVariable String uuid) {
        return propertyService.getPropertyById(uuid).map(property ->
                ResponseEntity.ok().body(PropertyResponse.fromProperty(property))
        ).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PropertyResponse> updatePropertyById(
            @PathVariable String uuid, @RequestBody @Valid PropertyUpdateRequest propertyUpdateRequest) {

        try {
            var updatedProperty = propertyService.updateProperty(uuid, propertyUpdateRequest);
            return ResponseEntity.ok(PropertyResponse.fromProperty(updatedProperty));
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
