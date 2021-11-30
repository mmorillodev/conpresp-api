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
public class PropertyUpdateRequest {

    @NotNull
    private List<PropertyResolutionRequest> propertyResolutions;

    @NotBlank
    private String designation;

    @NotBlank
    private String classification;

    @NotBlank
    private String currentUsage;

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

    public Property toProperty(Property property) {
        var propertyResolutions = getPropertyResolutions().stream().map(PropertyResolutionRequest::toPropertyResolution).collect(Collectors.toList());
        var graphicDocumentations = getGraphic().stream().map(GraphicRequest::toGraphicDocumentation).collect(Collectors.toList());
        var photographicDocumentations = getPhotographicDocumentation().stream().map(PhotographicRequest::toPhotographicDocumentation).collect(Collectors.toList());

        property.setPropertyResolutions(propertyResolutions);
        property.setGraphicDocumentation(graphicDocumentations);
        property.setPhotographicDocumentation(photographicDocumentations);
        property.setDesignation(property.getDesignation());
        property.setClassification(property.getClassification());
        property.setCurrentUsage(property.getCurrentUsage());
        property.setType(property.getType());
        property.setConstruction(this.getConstruction().toConstruction());
        property.setAddressLot(this.getAddressLot().toAddressLot());
        property.setAuthor(property.getAuthor());
        property.setBibliographicSource(property.getBibliographicSource());
        property.setOtherInfo(property.getOtherInfo());
        property.setObservation(property.getObservation());

        return property;
    }
}
