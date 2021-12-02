package com.conpresp.conprespapi.dto.property.request;

import com.conpresp.conprespapi.entity.property.PhotographicDocumentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotographicRequest {

    @Lob
    private String image;

    public PhotographicDocumentation toPhotographicDocumentation() {
        return new PhotographicDocumentation(
                this.getImage()
        );
    }
}
