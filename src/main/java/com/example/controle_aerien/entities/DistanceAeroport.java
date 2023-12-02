package com.example.controle_aerien.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "distance_aeroports")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DistanceAeroport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double distance;

    @ManyToOne
    @JoinColumn(name = "aeroport_1_id")
    private Aeroport aeroport1;

    @ManyToOne
    @JoinColumn(name = "aeroport_2_id")
    private Aeroport aeroport2;

    public DistanceAeroport(double distance, Aeroport aeroport1, Aeroport aeroport2) {
        this.distance = distance;
        this.aeroport1 = aeroport1;
        this.aeroport2 = aeroport2;
    }
}
