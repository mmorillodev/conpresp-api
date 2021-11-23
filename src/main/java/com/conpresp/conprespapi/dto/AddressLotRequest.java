package com.conpresp.conprespapi.dto;

import com.conpresp.conprespapi.entity.AddressLot;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressLotRequest {

    @NotBlank
    private String addressType;

    @NotBlank
    private String title;

    @NotBlank
    private String addressStreet;

    @NotBlank
    private String addressNumber;

    @NotBlank
    private String addressDistrict;

    @NotBlank
    private String regionalHall;

    @NotBlank
    private String sector;

    @NotBlank
    private String block;

    @NotBlank
    private String lot;

    public AddressLot toAddressLot() {
        return new AddressLot(
                this.getAddressType(),
                this.getTitle(),
                this.getAddressStreet(),
                this.getAddressNumber(),
                this.getAddressDistrict(),
                this.getRegionalHall(),
                this.getSector(),
                this.getBlock(),
                this.getLot()
        );
    }
}
