package com.conpresp.conprespapi.dto.property.response;

import com.conpresp.conprespapi.entity.property.Description;
import lombok.*;

import javax.persistence.Column;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionResponse {


    private String historicalData;


    private String architecturalData;


    private String ambienceData;


    private String bibliographicSource;


    private String otherInfo;


    private String observation;

    public static DescriptionResponse fromDescription(Description description) {
        return new DescriptionResponse(
                description.getHistoricalData(),
                description.getArchitecturalData(),
                description.getAmbienceData(),
                description.getBibliographicSource(),
                description.getOtherInfo(),
                description.getObservation()
        );
    }
}
