package com.conpresp.conprespapi.entity.patrimony;

import com.conpresp.conprespapi.entity.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Patrimony extends Auditable {

    @Id
    private String id = UUID.randomUUID().toString();

    @NonNull
    private String denomination;

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
    private List<HeritageResolution> heritageResolutions;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Construction construction;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private AddressLot addressLot;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Description description;

    @OneToMany(cascade = CascadeType.ALL)
    @NonNull
    private List<PhotographicDocumentation> photographicDocumentation;

    @OneToMany(cascade = CascadeType.ALL)
    @NonNull
    private List<GraphicDocumentation> graphicDocumentation;

}
