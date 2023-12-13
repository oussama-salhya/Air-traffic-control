package com.example.controle_aerien.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "distance_aeroports")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Component
public class DistanceAeroport {

    @EmbeddedId
    private DistanceAeroportId distanceAeroportId;

    private int distance;

    /**/
    public DistanceAeroport(DistanceAeroportId distanceAeroportId ,int distance) {
        this.distanceAeroportId=distanceAeroportId;
        this.distance = distance;
    }
}
