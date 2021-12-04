package com.conpresp.conprespapi.dto.patrimony.response;

import com.conpresp.conprespapi.entity.patrimony.Patrimony;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class PatrimonyBasicInfoResponse {

    private String id;

    private String denomination;

    private List<PatrimonyResolutionResponse> resolution;

    private String addressType;

    private String addressStreet;

    private String modificationLevel;

    private String modificationLevelComment;

    public static PatrimonyBasicInfoResponse fromPatrimony(Patrimony patrimony) {
        return new PatrimonyBasicInfoResponse(
                patrimony.getId(),
                patrimony.getDenomination(),
                patrimony.getHeritageResolutions().stream().map(PatrimonyResolutionResponse::fromHeritageResolution).collect(Collectors.toList()),
                patrimony.getAddressLot().getAddressType(),
                patrimony.getAddressLot().getAddressStreet(),
                patrimony.getConstruction().getModificationLevel(),
                patrimony.getConstruction().getModificationLevelComment()
        );
    }
}
