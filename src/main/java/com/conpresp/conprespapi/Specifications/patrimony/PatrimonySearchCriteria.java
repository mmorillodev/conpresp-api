package com.conpresp.conprespapi.Specifications.patrimony;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PatrimonySearchCriteria {
    private String denomination;

    private String resolution;

    private String originalUsage;

    private String addressType;

    private String addressTitle;

    private String street;

    private String address;

    private String addressNumber;

    private String district;

    private String regionalHall;

    private String author;

    private String constructionYear;

    private String architecturalStyle;

    private String conservationLevel;

    private String createdBy;
}
