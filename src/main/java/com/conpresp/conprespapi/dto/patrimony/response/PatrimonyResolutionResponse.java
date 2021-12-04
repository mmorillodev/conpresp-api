package com.conpresp.conprespapi.dto.patrimony.response;

import com.conpresp.conprespapi.entity.patrimony.HeritageResolution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
public class PatrimonyResolutionResponse {

    private String resolution;

    private String institution;

    private Year year;

    public static PatrimonyResolutionResponse fromHeritageResolution(HeritageResolution heritageResolution) {
        return new PatrimonyResolutionResponse(
                heritageResolution.getResolution(),
                heritageResolution.getInstitution(),
                heritageResolution.getYear()
        );
    }
}
