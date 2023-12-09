package com.example.controle_aerien.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "avions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Avion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Avion (String nom)
    {
        super();
        this.nom=nom;
    }

    private String nom;

    @Enumerated(EnumType.STRING)
    private TypeAvion type;

    private double consommation;
    private double capacite;
    private boolean disponibilite;

    @ManyToOne
    @JoinColumn(name ="aerport_id",referencedColumnName = "id")
    private Aeroport aeroport;


    public Avion(String nom, TypeAvion type, double consommation, double capacite, boolean disponibilite) {
        this.nom = nom;
        this.type = type;
        this.consommation = consommation;
        this.capacite = capacite;
        updateDisponibilite();
    }


    //DONT FORGET TO WORK ON DISPONIBILITE ON THE CONDITIONS AND STUFF
    private void updateDisponibilite() {
        this.disponibilite = this.capacite > 100;
    }
}
