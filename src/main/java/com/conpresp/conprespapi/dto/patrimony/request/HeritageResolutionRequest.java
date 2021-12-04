package com.conpresp.conprespapi.dto.patrimony.request;

import com.conpresp.conprespapi.entity.patrimony.HeritageResolution;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeritageResolutionRequest {

    @NotBlank
    private String resolution;

    @NotBlank
    private String institution;

    @NotBlank
    @JsonFormat(pattern = "yyyy")
    private Year year;

    public HeritageResolution toHeritageResolution() {
        return new HeritageResolution(
                this.getResolution(),
                getInstitution(),
                getYear()
        );
    }
}
