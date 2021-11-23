package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.PhotographicDocumentation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import java.util.Base64;

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
