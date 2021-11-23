package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertyResponse {

    private HeritageResolution heritageResolution;

    private String designation;

    private String classification;

    private String currentUsage;

    private String type;

    private Construction construction;

    private Address address;

    private String author;

    public static PropertyResponse fromProperty(Property property) {
        return new PropertyResponse(
                new HeritageResolution(
                        new PropertyResolution(property.getResolutionItem().getPropertyResolution().getProperty(),
                        property.getResolutionItem().getPropertyResolution().getResolution()),
                        property.getResolutionItem().getYear(),
                        new Institution(property.getResolutionItem().getInstitution().getName(),
                        property.getResolutionItem().getInstitution().getScope())),
                property.getDesignation(),
                property.getClassification(),
                property.getCurrentUsage(),
                property.getType(),
                new Construction(
                        property.getConstruction().getConstructionYear(),
                        property.getConstruction().getArchitecturalStyle(),
                        property.getConstruction().getConstructiveTechnique(),
                        property.getConstruction().getFloorQuantity(),
                        property.getConstruction().getConstructedArea(),
                        property.getConstruction().getHeritageLevel(),
                        property.getConstruction().getModificationLevel(),
                        property.getConstruction().getModificationLevelComment(),
                        property.getConstruction().getConservationLevel(),
                        property.getConstruction().getConservationLevelComment(),
                        property.getConstruction().getFloorObservation()),
                new Address(
                        property.getAddressLot().getAddressType(),
                        property.getAddressLot().getTitle(),
                        property.getAddressLot().getAddressStreet(),
                        property.getAddressLot().getAddressNumber(),
                        property.getAddressLot().getAddressDistrict(),
                        property.getAddressLot().getRegionalHall(),
                        property.getAddressLot().getSector(),
                        property.getAddressLot().getBlock(),
                        property.getAddressLot().getLot()),
                property.getAuthor()
        );
    }
}
