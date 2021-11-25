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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "resolution_item_ID")
    @NonNull
    private List<HeritageResolution> heritageResolution;

    @NonNull
    private String designation;

    @NonNull
    private String classification;

    @NonNull
    private String currentUsage;

    @NonNull
    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "construction_ID")
    @NonNull
    private Construction construction;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_lot_ID")
    @NonNull
    private AddressLot addressLot;

    @NonNull
    private String author;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "photographic_documentation_ID")
    @NonNull
    private List<PhotographicDocumentation> photographicDocumentation;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "graphic_documentation_ID")
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
