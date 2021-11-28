package com.conpresp.conprespapi.dto.property.response;

import com.conpresp.conprespapi.entity.PropertyResolution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertyResolutionResponse {

    private String resolution;

    public static PropertyResolutionResponse fromPropertyResolution(PropertyResolution propertyResolution) {
        return new PropertyResolutionResponse(
                propertyResolution.getResolution()
        );
    }
}
