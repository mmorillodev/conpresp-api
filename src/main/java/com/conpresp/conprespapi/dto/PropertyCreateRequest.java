package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.Institution;
import com.conpresp.conprespapi.entity.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyCreateRequest {

    @NotNull
    private HeritageResolutionRequest heritageResolutionRequest;

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

    private PhotographicRequest photographicDocumentationRequest;

    private GraphicRequest graphicRequest;

    private String bibliographicSource;

    private String otherInfo;

    private String observation;

    public Property toProperty(Institution institution) {
        return new Property(
                this.getHeritageResolutionRequest().toHeritageResolution(institution),
                this.getDesignation(),
                this.getClassification(),
                this.getCurrentUsage(),
                this.getType(),
                this.getConstructionRequest().toConstruction(),
                this.getAddressLotRequest().toAddressLot(),
                this.getAuthor(),
                this.getPhotographicDocumentationRequest().toPhotographicDocumentation(),
                this.getGraphicRequest().toGraphicDocumentation(),
                this.getBibliographicSource(),
                this.getOtherInfo(),
                this.getObservation()
        );
    }
}
