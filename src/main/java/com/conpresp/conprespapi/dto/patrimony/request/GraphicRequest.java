package com.conpresp.conprespapi.dto.patrimony.request;

import com.conpresp.conprespapi.entity.patrimony.GraphicDocumentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Lob;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraphicRequest {

    @Type(type="org.hibernate.type.StringType")
    private String image;

    private String imageName;

    public GraphicDocumentation toGraphicDocumentation() {
        return new GraphicDocumentation(
                this.getImageName(),
                this.getImage()
        );
    }
}
