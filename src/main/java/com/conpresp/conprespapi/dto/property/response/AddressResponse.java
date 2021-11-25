package com.conpresp.conprespapi.dto.property.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AddressResponse {

    private String Type;

    private String Title;

    private String Street;

    private String Number;

    private String District;

    private String RegionalHall;

    private String Sector;

    private String Block;

    private String Lot;
}
