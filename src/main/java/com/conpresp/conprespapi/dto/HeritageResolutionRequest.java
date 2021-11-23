package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.HeritageResolution;
import com.conpresp.conprespapi.entity.Institution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Year;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class HeritageResolutionRequest {

    @NotNull
    private PropertyResolutionRequest propertyResolutionRequest;

    @NotBlank
    private String year;

    @NotBlank @Pattern(regexp = "CONDEPHAAT|CONPRESP|IPHAN", message = "Invalid institution name! Options: CONDEPHAAT, CONPRESP, IPHAN")
    private String institution;

    public HeritageResolution toHeritageResolution(Institution institution) {
        return new HeritageResolution(
                this.getPropertyResolutionRequest().toPropertyResolution(),
                Year.parse(this.getYear()),
                institution
        );
    }
}
