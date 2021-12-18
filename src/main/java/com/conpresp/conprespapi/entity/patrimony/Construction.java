package com.conpresp.conprespapi.entity.patrimony;

import lombok.*;

import javax.persistence.*;
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
    private Year constructionYear;

    @NonNull
    private Boolean approximateDate;

    @NonNull
    private String author;

    @NonNull
    private String constructor;

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