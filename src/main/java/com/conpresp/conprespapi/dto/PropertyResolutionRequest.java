package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.PropertyResolution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
