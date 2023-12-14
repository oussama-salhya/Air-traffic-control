package com.example.controle_aerien.entities;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(fetch = FetchType.EAGER)//mappedBy = "aeroport"
    private List<Avion> avionsSol;

    @OneToMany()
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


}
