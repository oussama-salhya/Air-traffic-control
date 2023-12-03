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

    @EmbeddedId
    private DistanceAeroportId distanceAeroportId;

    private double distance;

    /**/

    public DistanceAeroport(DistanceAeroportId distanceAeroportId ,double distance) {
        this.distanceAeroportId=distanceAeroportId;
        this.distance = distance;
    }
}
