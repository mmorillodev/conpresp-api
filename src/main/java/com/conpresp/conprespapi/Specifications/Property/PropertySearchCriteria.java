package com.conpresp.conprespapi.Specifications.Property;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter
@Builder
public class PropertySearchCriteria {
    private String designation;

    private String resolution;

    private String originalUsage;

    private String addressType;

    private String addressTitle;

    private String street;

    private String addressNumber;

    private String district;

    private String regionalHall;

    private String author;

    private String constructionYear;

    private String architecturalStyle;
}
