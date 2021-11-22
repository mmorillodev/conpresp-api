package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.HeritageResolution;
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
public class HeritageResolutionRequest {

    @NotNull
    private PropertyResolutionRequest propertyResolutionRequest;

    @NotBlank
    private String year;

    @NotNull
    private InstitutionRequest institutionRequest;

    public HeritageResolution toHeritageResolution() {
        return new HeritageResolution(
                this.getPropertyResolutionRequest().toPropertyResolution(),
                Year.parse(this.getYear()),
                this.getInstitutionRequest().toInstitution()
        );
    }
}
