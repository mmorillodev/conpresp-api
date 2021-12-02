package com.conpresp.conprespapi.Specifications.Property;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter
@Builder
public class PropertySearchCriteria {
    private Optional<String> designation;

    private Optional<String> resolution;

    private Optional<String> originalUsage;

    private Optional<String> addressType;

    private Optional<String> addressTitle;

    private Optional<String> street;

    private Optional<String> addressNumber;

    private Optional<String> district;

    private Optional<String> regionalHall;

    private Optional<String> author;

    private Optional<String> constructionYear;

    private Optional<String> architecturalStyle;
}
