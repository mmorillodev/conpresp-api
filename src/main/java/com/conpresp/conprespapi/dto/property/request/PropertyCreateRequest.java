package com.conpresp.conprespapi.dto.property.request;

import com.conpresp.conprespapi.entity.property.Property;
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
public class PropertyCreateRequest {

    @NotNull
    private List<PropertyResolutionRequest> propertyResolutions;

    @NotBlank
    private String designation;

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

    @NotBlank
    private String author;

    private String bibliographicSource;

    private String otherInfo;

    private String observation;

    private List<PhotographicRequest> photographicDocumentation;

    private List<GraphicRequest> graphic;

    public Property toProperty() {
        var propertyResolutions = getPropertyResolutions().stream().map(PropertyResolutionRequest::toPropertyResolution).collect(Collectors.toList());
        var graphicDocumentations = getGraphic().stream().map(GraphicRequest::toGraphicDocumentation).collect(Collectors.toList());
        var photographicDocumentations = getPhotographicDocumentation().stream().map(PhotographicRequest::toPhotographicDocumentation).collect(Collectors.toList());

        return new Property(
                this.getDesignation(),
                this.getClassification(),
                this.getCurrentUsage(),
                this.getOriginalUsage(),
                this.getType(),
                propertyResolutions,
                this.getConstruction().toConstruction(),
                this.getAddressLot().toAddressLot(),
                this.getAuthor(),
                photographicDocumentations,
                graphicDocumentations,
                this.getBibliographicSource(),
                this.getOtherInfo(),
                this.getObservation()
        );
    }
}
