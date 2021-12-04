package com.conpresp.conprespapi.entity.patrimony;

import lombok.*;

import javax.persistence.*;

@Table(name = "graphic_documentation")
@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class GraphicDocumentation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Lob
    private String image;
}