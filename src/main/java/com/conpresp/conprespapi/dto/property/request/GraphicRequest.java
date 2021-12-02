package com.conpresp.conprespapi.dto.property.request;

import com.conpresp.conprespapi.entity.property.GraphicDocumentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraphicRequest {

    @Lob
    private String image;

    public GraphicDocumentation toGraphicDocumentation() {
        return new GraphicDocumentation(
                this.getImage()
        );
    }
}
