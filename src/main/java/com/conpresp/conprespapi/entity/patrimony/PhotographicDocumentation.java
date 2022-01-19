package com.conpresp.conprespapi.entity.patrimony;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Table(name = "photographic_documentation")
@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class PhotographicDocumentation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String imageName;

    @NonNull
    @Lob
    private String image;
}