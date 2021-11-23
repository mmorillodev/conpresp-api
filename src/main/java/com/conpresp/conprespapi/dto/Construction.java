package com.conpresp.conprespapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter @Setter
@AllArgsConstructor
public class Construction {

    private Year constructionYear;

    private String architecturalStyle;

    private String constructiveTechnique;

    private Integer floorQuantity;

    private Double constructedArea;

    private String heritageLevel;

    private String modificationLevel;

    private String modificationLevelComment;

    private String conservationLevel;

    private String conservationLevelComment;

    private String floorObservation;
}
