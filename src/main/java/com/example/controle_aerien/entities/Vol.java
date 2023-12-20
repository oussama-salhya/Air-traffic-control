package com.example.controle_aerien.entities;

import com.example.controle_aerien.DTO.VolDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vols")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Vol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aeroport_depart_id",referencedColumnName = "id")
    private Aeroport aeroportDepart;

    @ManyToOne
    @JoinColumn(name = "aeroport_arrivee_id",referencedColumnName = "id")
    private Aeroport aeroportArrivee;

    private Date heureDepart;
    private Date heureArrivee;

    @ManyToOne
    @JoinColumn(name = "avion_id" , referencedColumnName = "id")
    private Avion avion;


    @ManyToOne
    @JoinColumn(name = "parent_vol_id")
    private Vol parentVol;

    @OneToMany(mappedBy = "parentVol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vol> subVols = new ArrayList<>();

    public Vol(Aeroport aeroportDepart, Aeroport aeroportArrivee, Date heureDepart, Date heureArrivee, Avion avion) {
        this.aeroportDepart = aeroportDepart;
        this.aeroportArrivee = aeroportArrivee;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
        this.avion = avion;
        avion.setDisponibilite(true);
    }


    public VolDTO toDTO() {
        return new VolDTO(this);
    }



}
