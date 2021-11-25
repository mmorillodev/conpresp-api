package com.conpresp.conprespapi.dto.property.response;

import com.conpresp.conprespapi.entity.HeritageResolution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
public class HeritageResolutionResponse {

    private PropertyResolutionResponse propertyResolution;

    private Year year;

    private InstitutionResponse institution;

    public static HeritageResolutionResponse fromHeritageResolution(HeritageResolution heritageResolution) {
        return new HeritageResolutionResponse(
                PropertyResolutionResponse.fromPropertyResolution(heritageResolution.getPropertyResolution()),
                heritageResolution.getYear(),
                InstitutionResponse.fromInstitution(heritageResolution.getInstitution())
        );
    }
}
