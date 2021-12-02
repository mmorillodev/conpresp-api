package com.conpresp.conprespapi.dto.property.response;

import com.conpresp.conprespapi.entity.property.Construction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter @Setter
@AllArgsConstructor
public class ConstructionResponse {

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

    public static ConstructionResponse fromConstruction(Construction construction) {
        return new ConstructionResponse(
                construction.getConstructionYear(),
                construction.getArchitecturalStyle(),
                construction.getConstructiveTechnique(),
                construction.getFloorQuantity(),
                construction.getConstructedArea(),
                construction.getHeritageLevel(),
                construction.getModificationLevel(),
                construction.getModificationLevelComment(),
                construction.getConservationLevel(),
                construction.getConservationLevelComment(),
                construction.getFloorObservation()
        );
    }
}
