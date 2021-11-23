package com.conpresp.conprespapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "heritage_resolution")
@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class HeritageResolution {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_resolution_ID")
    @NonNull
    private PropertyResolution propertyResolution;

    @NonNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_ID")
    @NonNull
    private Institution institution;
}