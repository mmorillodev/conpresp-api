package com.conpresp.conprespapi.dto.patrimony.response;

import com.conpresp.conprespapi.entity.patrimony.AddressLot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AddressLotResponse {


    private String type;

    private String title;

    private String street;

    private String number;

    private String district;

    private String regionalHall;

    private String sector;

    private String block;

    private String lot;

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
