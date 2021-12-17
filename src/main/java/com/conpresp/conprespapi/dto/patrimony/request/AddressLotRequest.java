package com.conpresp.conprespapi.dto.patrimony.request;

import com.conpresp.conprespapi.entity.patrimony.AddressLot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressLotRequest {

    private String type;

    private String title;

    private String street;

    private String address;

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
                (this.getType() + " " + this.getTitle() + " " + this.getStreet()).trim().replaceAll("\\s{2,}", " "),
                this.getNumber(),
                this.getDistrict(),
                this.getRegionalHall(),
                this.getSector(),
                this.getBlock(),
                this.getLot()
        );
    }
}
