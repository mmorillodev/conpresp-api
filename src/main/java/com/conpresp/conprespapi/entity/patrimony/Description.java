package com.conpresp.conprespapi.entity.patrimony;

import lombok.*;

import javax.persistence.*;

@Table(name = "description")
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String historicalData;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String architecturalData;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String ambienceData;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String bibliographicSource;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String otherInfo;

    @Column(columnDefinition = "TEXT")
    @NonNull
    private String observation;
}
