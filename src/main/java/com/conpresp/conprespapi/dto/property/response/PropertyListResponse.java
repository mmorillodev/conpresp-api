package com.conpresp.conprespapi.dto.property.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyListResponse {

    private List<PropertyResponse> property;

    private int total;
}
