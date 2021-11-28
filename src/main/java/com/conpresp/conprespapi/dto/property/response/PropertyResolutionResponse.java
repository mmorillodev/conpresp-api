package com.conpresp.conprespapi.dto.property.response;

import com.conpresp.conprespapi.entity.PropertyResolution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
public class PropertyResolutionResponse {

    private String resolution;

    private String institution;

    private Year year;

    public static PropertyResolutionResponse fromPropertyResolution(PropertyResolution propertyResolution) {
        return new PropertyResolutionResponse(
                propertyResolution.getResolution(),
                propertyResolution.getInstitution(),
                propertyResolution.getYear()
        );
    }
}
