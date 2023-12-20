package com.example.controle_aerien.DTO;

import com.example.controle_aerien.entities.TypeAvion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvionDTO {
    private Long id;
    private String name;
    private TypeAvion type;

    private double consommation;
    private double capacite;
    private boolean disponibilite;
    private int speed;
    private Long IdAeroport;
    private Long InPoint;

}
