package com.conpresp.conprespapi.dto.property.response;

import com.conpresp.conprespapi.dto.property.request.DescriptionRequest;
import com.conpresp.conprespapi.entity.property.GraphicDocumentation;
import com.conpresp.conprespapi.entity.property.PhotographicDocumentation;
import com.conpresp.conprespapi.entity.property.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class PropertyDetailsResponse {

    private String id;

    private List<PropertyResolutionResponse> propertyResolutions;

    private String designation;

    private String classification;

    private String currentUsage;

    private String originalUsage;

    private String propertyType;

    private ConstructionResponse construction;

    private AddressLotResponse addressLot;

    private DescriptionResponse description;

    private List<PhotographicDocumentation> photographicDocumentation;

    private List<GraphicDocumentation> graphic;

    private String createdBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private String updatedBy;

    public static PropertyDetailsResponse fromProperty(Property property) {
        return new PropertyDetailsResponse(
                property.getId(),
                property.getPropertyResolutions().stream().map(PropertyResolutionResponse::fromPropertyResolution).collect(Collectors.toList()),
                property.getDesignation(),
                property.getClassification(),
                property.getCurrentUsage(),
                property.getOriginalUsage(),
                property.getPropertyType(),
                ConstructionResponse.fromConstruction(property.getConstruction()),
                AddressLotResponse.fromAddressLot(property.getAddressLot()),
                DescriptionResponse.fromDescription(property.getDescription()),
                property.getPhotographicDocumentation(),
                property.getGraphicDocumentation(),
                property.getCreatedBy(),
                property.getCreatedAt(),
                property.getUpdatedAt(),
                property.getUpdatedBy()
        );
    }
}
