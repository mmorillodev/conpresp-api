package com.conpresp.conprespapi.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "property_resolution")
@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class PropertyResolution {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String property;

    @NonNull
    private String resolution;
}