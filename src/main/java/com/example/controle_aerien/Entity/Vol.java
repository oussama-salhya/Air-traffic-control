package com.example.controle_aerien.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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
    @JoinColumn(name = "aeroport_depart_id")
    private Aeroport aeroportDepart;

    @ManyToOne
    @JoinColumn(name = "aeroport_arrivee_id")
    private Aeroport aeroportArrivee;

    private Date heureDepart;
    private Date heureArrivee;

    @OneToOne
    @JoinColumn(name = "avion_id")
    private Avion avion;

    public Vol(Aeroport aeroportDepart, Aeroport aeroportArrivee, Date heureDepart, Date heureArrivee, Avion avion) {
        this.aeroportDepart = aeroportDepart;
        this.aeroportArrivee = aeroportArrivee;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
        this.avion = avion;
        avion.setDisponibilite(false); // false dans l'affectation
    }
}
