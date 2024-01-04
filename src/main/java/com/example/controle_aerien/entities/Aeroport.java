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
        this.tmpAccPist=120;
        this.delaiAntiColis=120;
        this.delaiAttente= 60;
        this.tmpDecolage= 90;
        this.durreboucleatt=200;
    }
    public Aeroport(String nom, Point position){
        this.nom = nom;
        this.position = position;
        this.disponibilite=true;
        this.nbPlaceSol = generateRandomNumber(5, 10);  // Example range: 2 to 10
        this.tmpAccPist = generateRandomNumber(2, 15);  // Example range: 60 to 180
        this.delaiAntiColis = generateRandomNumber(3, 17);  // Example range: 60 to 180
        this.delaiAttente = generateRandomNumber(4, 13);  // Example range: 30 to 90
        this.tmpDecolage = generateRandomNumber(1, 12);  // Example range: 60 to 120
        this.durreboucleatt = generateRandomNumber(2, 18);  // Example range: 150 to 300
        this.nbPiste = generateRandomNumber(2, 5);  // Example range: 150 to 300
    }
    private int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }


}
