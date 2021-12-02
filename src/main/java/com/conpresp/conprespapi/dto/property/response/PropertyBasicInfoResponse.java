package com.conpresp.conprespapi.dto.property.response;

import com.conpresp.conprespapi.entity.property.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertyBasicInfoResponse {

    private String id;

    private String designation;

    private String addressType;

    private String addressStreet;

    private String modificationLevel;

    private String modificationLevelComment;

    public static PropertyBasicInfoResponse fromProperty(Property property) {
        return new PropertyBasicInfoResponse(
                property.getId(),
                property.getDesignation(),
                property.getAddressLot().getAddressType(),
                property.getAddressLot().getAddressStreet(),
                property.getConstruction().getModificationLevel(),
                property.getConstruction().getModificationLevelComment()
        );
    }
}
