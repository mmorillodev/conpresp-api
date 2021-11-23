package com.conpresp.conprespapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Year;

@Table(name = "construction")
@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Construction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @JsonFormat(pattern = "yyyy")
    private Year constructionYear;

    @NonNull
    private String architecturalStyle;

    @NonNull
    private String constructiveTechnique;

    @NonNull
    private Integer floorQuantity;

    @NonNull
    private Double constructedArea;

    @NonNull
    private String heritageLevel;

    @NonNull
    private String modificationLevel;

    @NonNull
    @Column(columnDefinition = "TEXT")
    private String modificationLevelComment;

    @NonNull
    private String conservationLevel;

    @NonNull
    @Column(columnDefinition = "TEXT")
    private String conservationLevelComment;

    @NonNull
    @Column(columnDefinition = "TEXT")
    private String floorObservation;
}