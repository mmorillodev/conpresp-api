package com.conpresp.conprespapi.dto.property.response;

import com.conpresp.conprespapi.entity.property.GraphicDocumentation;
import com.conpresp.conprespapi.entity.property.PhotographicDocumentation;
import com.conpresp.conprespapi.entity.property.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    private String type;

    private ConstructionResponse construction;

    private AddressLotResponse addressLot;

    private String author;

    private String bibliographicSource;

    private String otherInfo;

    private String observation;

    private List<PhotographicDocumentation> photographicDocumentation;

    private List<GraphicDocumentation> graphic;

    public static PropertyDetailsResponse fromProperty(Property property) {
        return new PropertyDetailsResponse(
                property.getId(),
                property.getPropertyResolutions().stream().map(PropertyResolutionResponse::fromPropertyResolution).collect(Collectors.toList()),
                property.getDesignation(),
                property.getClassification(),
                property.getCurrentUsage(),
                property.getOriginalUsage(),
                property.getType(),
                ConstructionResponse.fromConstruction(property.getConstruction()),
                AddressLotResponse.fromAddressLot(property.getAddressLot()),
                property.getAuthor(),
                property.getBibliographicSource(),
                property.getOtherInfo(),
                property.getObservation(),
                property.getPhotographicDocumentation(),
                property.getGraphicDocumentation()
        );
    }
}
