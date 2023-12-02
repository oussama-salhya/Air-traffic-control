package com.example.controle_aerien.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "avions")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Avion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nom;

    @Enumerated(EnumType.STRING)
    private TypeAvion type;

    private double consommation;
    private double capacite;
    private boolean disponibilite;

    // Many to one
    @ManyToOne
    @JoinColumn(name = "aeroport_id")
    private Aeroport aeroport;

    // One to one
    @OneToOne(mappedBy = "avion", cascade = CascadeType.ALL)
    private Vol vol;

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
