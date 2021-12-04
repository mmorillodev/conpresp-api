package com.conpresp.conprespapi.dto.patrimony.request;

import com.conpresp.conprespapi.entity.patrimony.Description;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionRequest {

    @NotBlank
    private String  historicalData;

    @NotBlank
    private String architecturalData;

    @NotBlank
    private String ambienceData;

    @NotBlank
    private String bibliographicSource;

    @NotBlank
    private String otherInfo;

    @NotBlank
    private String observation;

    public Description toDescription() {
        return new Description(
                this.getHistoricalData(),
                this.getArchitecturalData(),
                this.getAmbienceData(),
                this.getBibliographicSource(),
                this.getOtherInfo(),
                this.getObservation()
        );
    }
}
