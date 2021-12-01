package com.conpresp.conprespapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Property extends Auditable {

    @Id
    private String id = UUID.randomUUID().toString();

    @NonNull
    private String designation;

    @NonNull
    private String classification;

    @NonNull
    private String currentUsage;

    @NonNull
    private String originalUsage;

    @NonNull
    private String type;

    @OneToMany(cascade = CascadeType.ALL)
    @NonNull
    private List<PropertyResolution> propertyResolutions;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Construction construction;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private AddressLot addressLot;

    @NonNull
    private String author;

    @OneToMany(cascade = CascadeType.ALL)
    @NonNull
    private List<PhotographicDocumentation> photographicDocumentation;

    @OneToMany(cascade = CascadeType.ALL)
    @NonNull
    private List<GraphicDocumentation> graphicDocumentation;

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
