package com.conpresp.conprespapi.dto.property.request;

import com.conpresp.conprespapi.entity.PropertyResolution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyResolutionRequest {

    @NotBlank
    private String property;

    @NotBlank
    private String resolution;

    public PropertyResolution toPropertyResolution() {
        return new PropertyResolution(
          this.getProperty(),
          this.getResolution()
        );
    }
}
