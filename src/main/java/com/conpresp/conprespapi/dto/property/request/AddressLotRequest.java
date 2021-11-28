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
