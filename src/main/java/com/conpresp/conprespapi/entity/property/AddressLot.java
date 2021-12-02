package com.conpresp.conprespapi.entity.property;

import lombok.*;

import javax.persistence.*;

@Table(name = "address_lot")
@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class AddressLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String addressType;

    @NonNull
    private String title;

    @NonNull
    private String addressStreet;

    @NonNull
    private String addressNumber;

    @NonNull
    private String addressDistrict;

    @NonNull
    private String regionalHall;

    @NonNull
    private String sector;

    @NonNull
    private String block;

    @NonNull
    private String lot;
}