package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.Construction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Year;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionRequest {

    @NotBlank
    private String constructionYear;

    @NotBlank
    private String architecturalStyle;

    @NotBlank
    private String constructiveTechnique;

    @NotNull
    private Integer floorQuantity;

    @NotNull
    private Double constructedArea;

    @NotBlank
    private String heritageLevel;

    @NotBlank
    private String modificationLevel;

    private String modificationLevelComment;

    @NotBlank
    private String conservationLevel;

    private String conservationLevelComment;

    private String floorObservation;

    public Construction toConstruction() {
        return new Construction(
                Year.parse(this.getConstructionYear()),
                this.getArchitecturalStyle(),
                this.getConstructiveTechnique(),
                this.getFloorQuantity(),
                this.getConstructedArea(),
                this.getHeritageLevel(),
                this.getModificationLevel(),
                this.getModificationLevelComment(),
                this.getConservationLevel(),
                this.getConservationLevelComment(),
                this.getFloorObservation()
        );
    }
}
