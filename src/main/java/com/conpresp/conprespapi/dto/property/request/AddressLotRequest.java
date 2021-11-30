package com.conpresp.conprespapi.dto.property.request;

import com.conpresp.conprespapi.entity.AddressLot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressLotRequest {

    @NotBlank
    private String type;

    @NotBlank
    private String title;

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    @NotBlank
    private String district;

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
                this.getType(),
                this.getTitle(),
                this.getStreet(),
                this.getNumber(),
                this.getDistrict(),
                this.getRegionalHall(),
                this.getSector(),
                this.getBlock(),
                this.getLot()
        );
    }
}
