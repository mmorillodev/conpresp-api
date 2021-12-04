package com.conpresp.conprespapi.entity.patrimony;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.Year;

@Table(name = "heritage_resolution")
@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class HeritageResolution {

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