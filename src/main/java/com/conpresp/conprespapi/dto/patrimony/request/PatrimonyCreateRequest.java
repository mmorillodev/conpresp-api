package com.conpresp.conprespapi.dto.patrimony.request;

import com.conpresp.conprespapi.entity.patrimony.Patrimony;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatrimonyCreateRequest {

    @NotNull
    private List<HeritageResolutionRequest> heritageResolutions;

    @NotBlank
    private String denomination;

    @NotBlank
    private String classification;

    @NotBlank
    private String currentUsage;

    @NotBlank
    private String originalUsage;

    @NotBlank
    private String type;

    @NotNull
    private ConstructionRequest construction;

    @NotNull
    private AddressLotRequest addressLot;

    @NotNull
    private DescriptionRequest description;

    private List<PhotographicRequest> photographicDocumentation;

    private List<GraphicRequest> graphic;

    public Patrimony toPatrimony() {
        var heritageResolutions = getHeritageResolutions().stream().map(HeritageResolutionRequest::toHeritageResolution).collect(Collectors.toList());
        var graphicDocumentations = getGraphic().stream().map(GraphicRequest::toGraphicDocumentation).collect(Collectors.toList());
        var photographicDocumentations = getPhotographicDocumentation().stream().map(PhotographicRequest::toPhotographicDocumentation).collect(Collectors.toList());

        return new Patrimony(
                this.getDenomination(),
                this.getClassification(),
                this.getCurrentUsage(),
                this.getOriginalUsage(),
                this.getType(),
                heritageResolutions,
                this.getConstruction().toConstruction(),
                this.getAddressLot().toAddressLot(),
                this.getDescription().toDescription(),
                photographicDocumentations,
                graphicDocumentations
        );
    }
}
