package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
public class PropertyResponse {

    private String resolution;

    private Year heritageYear;

    private String institutionName;

    private String institutionScope;

    private String designation;

    private String classification;

    private String currentUsage;

    private String type;

    private Year constructionYear;

    private String constructionArchitecturalStyle;

    private String constructionConstructiveTechnique;

    private Integer constructionFloorQuantity;

    private Double constructionConstructedArea;

    private String constructionHeritageLevel;

    private String constructionModificationLevel;

    private String constructionModificationLevelComment;

    private String constructionConservationLevel;

    private String constructionConservationLevelComment;

    private String constructionFloorObservation;

    private String addressType;

    private String addressTitle;

    private String addressStreet;

    private String addressNumber;

    private String addressDistrict;

    private String addressRegionalHall;

    private String addressSector;

    private String addressBlock;

    private String addressLot;

    private String author;

    public static PropertyResponse fromProperty(Property property) {
        return new PropertyResponse(
                property.getResolutionItem().getPropertyResolution().getResolution(),
                property.getResolutionItem().getYear(),
                property.getResolutionItem().getInstitution().getName(),
                property.getResolutionItem().getInstitution().getScope(),
                property.getDesignation(),
                property.getClassification(),
                property.getCurrentUsage(),
                property.getType(),
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
                property.getConstruction().getFloorObservation(),
                property.getAddressLot().getAddressType(),
                property.getAddressLot().getTitle(),
                property.getAddressLot().getAddressStreet(),
                property.getAddressLot().getAddressNumber(),
                property.getAddressLot().getAddressDistrict(),
                property.getAddressLot().getRegionalHall(),
                property.getAddressLot().getSector(),
                property.getAddressLot().getBlock(),
                property.getAddressLot().getLot(),
                property.getAuthor()
        );
    }
}
