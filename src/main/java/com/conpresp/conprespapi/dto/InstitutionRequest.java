package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.Institution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String scope;

    public Institution toInstitution() {
        return new Institution(
          this.getName(),
          this.getScope()
        );
    }
}
