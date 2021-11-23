package com.conpresp.conprespapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "construction")
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Construction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate constructionYear;

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
    @Column
    private String floorObservation;
}