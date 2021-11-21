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

    @ManyToOne(fetch = FetchType.LAZY)
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

    @ManyToOne
    @JoinColumn(name = "construction_ID")
    @NonNull
    private Construction construction;

    @ManyToOne
    @JoinColumn(name = "address_lot_ID")
    @NonNull
    private AddressLot addressLot;

    @NonNull
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographic_documentation_ID")
    private PhotographicDocumentation photographicDocumentation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "graphic_documentation_ID")
    private GraphicDocumentation graphicDocumentation;

    @Column(columnDefinition = "TEXT")
    private String bibliographicSource;

    @Column(columnDefinition = "TEXT")
    private String otherInfo;

    @Column(columnDefinition = "TEXT")
    private String observation;
}
