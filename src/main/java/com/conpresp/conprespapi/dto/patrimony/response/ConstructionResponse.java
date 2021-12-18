package com.conpresp.conprespapi.dto.patrimony.response;

import com.conpresp.conprespapi.entity.patrimony.Construction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter @Setter
@AllArgsConstructor
public class ConstructionResponse {

    private Year constructionYear;

    private Boolean approximateDate;

    private String author;

    private String constructor;

    private String architecturalStyle;

    private String constructiveTechnique;

    private Integer floorQuantity;

    private Double constructedArea;

    private Double areaLot;

    private String heritageLevel;

    private String modificationLevel;

    private String modificationLevelComment;

    private String conservationLevel;

    private String conservationLevelComment;

    private String floorObservation;

    public static ConstructionResponse fromConstruction(Construction construction) {
        return new ConstructionResponse(
                construction.getConstructionYear(),
                construction.getApproximateDate(),
                construction.getAuthor(),
                construction.getConstructor(),
                construction.getArchitecturalStyle(),
                construction.getConstructiveTechnique(),
                construction.getFloorQuantity(),
                construction.getConstructedArea(),
                construction.getAreaLot(),
                construction.getHeritageLevel(),
                construction.getModificationLevel(),
                construction.getModificationLevelComment(),
                construction.getConservationLevel(),
                construction.getConservationLevelComment(),
                construction.getFloorObservation()
        );
    }
}
