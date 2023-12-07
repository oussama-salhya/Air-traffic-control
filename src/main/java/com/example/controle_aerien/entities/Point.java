package com.example.controle_aerien.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="points",uniqueConstraints = @UniqueConstraint(columnNames = {"x","y"}))
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double x;
    private double y;

    @OneToOne(mappedBy = "position")
    private Aeroport aeroport;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
