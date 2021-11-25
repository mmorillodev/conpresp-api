package com.conpresp.conprespapi.dto.property.request;

import com.conpresp.conprespapi.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyCreateRequest {

    @NotNull
    private List<HeritageResolutionRequest> heritageResolutionRequest;

    @NotBlank
    private String designation;

    @NotBlank
    private String classification;

    @NotBlank
    private String currentUsage;

    @NotBlank
    private String type;

    @NotNull
    private ConstructionRequest constructionRequest;

    @NotNull
    private AddressLotRequest addressLotRequest;

    @NotBlank
    private String author;

    private List<PhotographicRequest> photographicDocumentationRequest;

    private List<GraphicRequest> graphicRequest;

    private String bibliographicSource;

    private String otherInfo;

    private String observation;

    public Property toProperty() {
        return new Property(
                this.getHeritageResolutionRequest().stream().map(HeritageResolutionRequest::toHeritageResolution).collect(Collectors.toList()),
                this.getDesignation(),
                this.getClassification(),
                this.getCurrentUsage(),
                this.getType(),
                this.getConstructionRequest().toConstruction(),
                this.getAddressLotRequest().toAddressLot(),
                this.getAuthor(),
                this.getPhotographicDocumentationRequest().stream().map(PhotographicRequest::toPhotographicDocumentation).collect(Collectors.toList()),
                this.getGraphicRequest().stream().map(GraphicRequest::toGraphicDocumentation).collect(Collectors.toList()),
                this.getBibliographicSource(),
                this.getOtherInfo(),
                this.getObservation()
        );
    }
}
