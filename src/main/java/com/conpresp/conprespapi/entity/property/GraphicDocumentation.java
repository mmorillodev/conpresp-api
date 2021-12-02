package com.conpresp.conprespapi.entity.property;

import lombok.*;

import javax.persistence.*;
import java.util.Base64;

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