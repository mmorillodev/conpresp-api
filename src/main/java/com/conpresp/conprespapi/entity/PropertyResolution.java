package com.conpresp.conprespapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.Year;

@Table(name = "property_resolution")
@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class PropertyResolution {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String resolution;

    @NonNull
    private String institution;

    @NonNull
    @JsonFormat(pattern = "yyyy")
    private Year year;
}