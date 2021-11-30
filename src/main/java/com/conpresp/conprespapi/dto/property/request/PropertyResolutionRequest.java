package com.conpresp.conprespapi.dto.property.request;

import com.conpresp.conprespapi.entity.PropertyResolution;
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
public class PropertyResolutionRequest {

    @NotBlank
    private String resolution;

    @NotBlank
    private String institution;

    @NotBlank
    @JsonFormat(pattern = "yyyy")
    private Year year;

    public PropertyResolution toPropertyResolution() {
        return new PropertyResolution(
                this.getResolution(),
                getInstitution(),
                getYear()
        );
    }
}
