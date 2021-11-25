package com.conpresp.conprespapi.dto.property.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter @Setter
@AllArgsConstructor
public class HeritageResolution {

    private PropertyResolutionResponse propertyResolution;

    private Year year;

    private InstitutionResponse institution;
}
