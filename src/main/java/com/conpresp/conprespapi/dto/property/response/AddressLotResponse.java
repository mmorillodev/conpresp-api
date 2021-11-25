package com.conpresp.conprespapi.dto.property.response;

import com.conpresp.conprespapi.entity.AddressLot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AddressLotResponse {

    private String Type;

    private String Title;

    private String Street;

    private String Number;

    private String District;

    private String RegionalHall;

    private String Sector;

    private String Block;

    private String Lot;

    public static AddressLotResponse fromAddressLot(AddressLot address) {
        return new AddressLotResponse(
                address.getAddressType(),
                address.getTitle(),
                address.getAddressStreet(),
                address.getAddressNumber(),
                address.getAddressDistrict(),
                address.getRegionalHall(),
                address.getSector(),
                address.getBlock(),
                address.getLot()
        );
    }
}
