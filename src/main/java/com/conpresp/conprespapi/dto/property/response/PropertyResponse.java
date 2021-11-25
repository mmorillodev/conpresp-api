package com.conpresp.conprespapi.dto.property.response;

import com.conpresp.conprespapi.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertyResponse {

    private HeritageResolutionResponse heritageResolution;

    private String designation;

    private String classification;

    private String currentUsage;

    private String type;

    private ConstructionResponse construction;

    private AddressLotResponse address;

    private String author;

    public static PropertyResponse fromProperty(Property property) {
        return new PropertyResponse(
                HeritageResolutionResponse.fromHeritageResolution(property.getResolutionItem()),
                property.getDesignation(),
                property.getClassification(),
                property.getCurrentUsage(),
                property.getType(),
                ConstructionResponse.fromConstruction(property.getConstruction()),
                AddressLotResponse.fromAddressLot(property.getAddressLot()),
                property.getAuthor()
        );
    }
}
