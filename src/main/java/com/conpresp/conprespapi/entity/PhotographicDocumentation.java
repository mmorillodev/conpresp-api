package com.conpresp.conprespapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Base64;

@Table(name = "photographic_documentation")
@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class PhotographicDocumentation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Lob
    private String image;
}