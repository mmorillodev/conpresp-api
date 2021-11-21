package com.conpresp.conprespapi.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "institution")
@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Institution {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String scope;
}