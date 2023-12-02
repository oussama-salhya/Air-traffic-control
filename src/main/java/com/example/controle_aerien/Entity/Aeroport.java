package com.example.controle_aerien.Entity;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="aeroports")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Aeroport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    private double secteur;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Avion> avionsSol;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Avion> avionsVol;

    private boolean disponibilite;
}
