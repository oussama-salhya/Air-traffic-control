package com.example.controle_aerien.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "points")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double x;
    private double y;

    @OneToOne
    @JoinColumn(name = "aeroport_id")
    private Aeroport aeroport;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
