package com.conpresp.conprespapi.dto.patrimony.request;

import com.conpresp.conprespapi.entity.patrimony.PhotographicDocumentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Lob;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotographicRequest {

    @Type(type="org.hibernate.type.StringType")
    private String image;

    private String imageName;

    public PhotographicDocumentation toPhotographicDocumentation() {
        return new PhotographicDocumentation(
                this.getImageName(),
                this.getImage()
        );
    }
}
