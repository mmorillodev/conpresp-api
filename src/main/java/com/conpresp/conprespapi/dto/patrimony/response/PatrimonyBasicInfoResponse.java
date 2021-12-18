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

    private List<PatrimonyResolutionResponse> resolutions;

    private String type;

    private String addressStreet;

    private String conservationLevel;

    private String conservationLevelComment;

    private String modificationLevel;

    private String currentUsage;

    public static PatrimonyBasicInfoResponse fromPatrimony(Patrimony patrimony) {
        return new PatrimonyBasicInfoResponse(
                patrimony.getId(),
                patrimony.getDenomination(),
                patrimony.getHeritageResolutions().stream().map(PatrimonyResolutionResponse::fromHeritageResolution).collect(Collectors.toList()),
                patrimony.getType(),
                patrimony.getAddressLot().getAddressStreet(),
                patrimony.getConstruction().getConservationLevel(),
                patrimony.getConstruction().getConservationLevelComment(),
                patrimony.getConstruction().getModificationLevel(),
                patrimony.getCurrentUsage()
        );
    }
}
