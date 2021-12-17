package com.conpresp.conprespapi.dto.patrimony.request;

import com.conpresp.conprespapi.entity.patrimony.Construction;
import lombok.*;

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
    private Boolean approximateDate;

    @NotBlank
    private String author;

    @NotBlank
    private String constructor;

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

    @NotBlank
    private String conservationLevelComment;

    @NotBlank
    private String floorObservation;

    public Construction toConstruction() {
        return new Construction(
                Year.parse(this.getConstructionYear()),
                this.getApproximateDate(),
                this.getAuthor(),
                this.getConstructor(),
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
