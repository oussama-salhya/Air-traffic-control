package com.example.controle_aerien.entities;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name="aeroports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Aeroport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int idtest;

    private String nom;
    private int nbPiste;
    private int nbPlaceSol;
    private double tmpAccPist;
    private double delaiAntiColis;
    private double delaiAttente;
    private double tmpDecolage;
    private double durreboucleatt;

    @OneToOne(cascade = CascadeType.ALL)
    private Point position;

    @OneToMany(mappedBy = "distanceAeroportId.aeroport1")
    private List<DistanceAeroport> distancesFromHere;

    @OneToMany(mappedBy = "distanceAeroportId.aeroport2")
    private List<DistanceAeroport> distancesToHere;

    private double secteur;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Avion> avionsSol;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Avion> avionsVol;

    private boolean disponibilite;
    public Aeroport (int idtest, Point position)
    {
        super();
        this.idtest=idtest;
        this.position=position;
        this.disponibilite=true;
        this.nbPlaceSol=5;
    }
    public Aeroport(String nom, Point position){
        this.nom = nom;
        this.position = position;
        this.disponibilite=true;
        this.nbPlaceSol=5;
//        this.idtest=idtest;
//        this.idtest=(int)this.getId();
    }


}
