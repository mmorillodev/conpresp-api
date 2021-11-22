package com.conpresp.conprespapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Property extends Auditable {

    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "resolution_item_ID")
    @NonNull
    private HeritageResolution resolutionItem;

    @NonNull
    private String designation;

    @NonNull
    private String classification;

    @NonNull
    private String currentUsage;

    @NonNull
    private String type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "construction_ID")
    @NonNull
    private Construction construction;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_lot_ID")
    @NonNull
    private AddressLot addressLot;

    @NonNull
    private String author;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "photographic_documentation_ID")
    @NonNull
    private PhotographicDocumentation photographicDocumentation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "graphic_documentation_ID")
    @NonNull
    private GraphicDocumentation graphicDocumentation;

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
