package com.conpresp.conprespapi.dto.property.response;

import com.conpresp.conprespapi.entity.Institution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InstitutionResponse {

    private String name;

    private String Scope;

    public static InstitutionResponse fromInstitution(Institution institution) {
        return new InstitutionResponse(
                institution.getName(),
                institution.getScope()
        );
    }
}
