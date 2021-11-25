package com.conpresp.conprespapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter @Setter
@AllArgsConstructor
public class HeritageResolution {

    private PropertyResolution propertyResolution;

    private Year year;

    private Institution institution;
}
