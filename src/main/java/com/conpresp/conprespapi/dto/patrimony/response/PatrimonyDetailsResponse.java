package com.conpresp.conprespapi.dto.patrimony.response;

import com.conpresp.conprespapi.entity.patrimony.GraphicDocumentation;
import com.conpresp.conprespapi.entity.patrimony.Patrimony;
import com.conpresp.conprespapi.entity.patrimony.PhotographicDocumentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class PatrimonyDetailsResponse {

    private String id;

    private List<PatrimonyResolutionResponse> heritageResolutions;

    private String resolutionItem;

    private String denomination;

    private String classification;

    private String currentUsage;

    private String originalUsage;

    private String type;

    private ConstructionResponse construction;

    private AddressLotResponse addressLot;

    private DescriptionResponse description;

    private List<PhotographicDocumentation> photographicDocumentation;

    private List<GraphicDocumentation> graphic;

    private String createdBy;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private String updatedBy;

    public static PatrimonyDetailsResponse fromPatrimony(Patrimony patrimony) {
        return new PatrimonyDetailsResponse(
                patrimony.getId(),
                patrimony.getHeritageResolutions().stream().map(PatrimonyResolutionResponse::fromHeritageResolution).collect(Collectors.toList()),
                patrimony.getResolutionItem(),
                patrimony.getDenomination(),
                patrimony.getClassification(),
                patrimony.getCurrentUsage(),
                patrimony.getOriginalUsage(),
                patrimony.getType(),
                ConstructionResponse.fromConstruction(patrimony.getConstruction()),
                AddressLotResponse.fromAddressLot(patrimony.getAddressLot()),
                DescriptionResponse.fromDescription(patrimony.getDescription()),
                patrimony.getPhotographicDocumentation(),
                patrimony.getGraphicDocumentation(),
                patrimony.getCreatedBy(),
                patrimony.getCreatedAt(),
                patrimony.getUpdatedAt(),
                patrimony.getUpdatedBy()
        );
    }
}
