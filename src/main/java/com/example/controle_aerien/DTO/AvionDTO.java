package com.example.controle_aerien.DTO;

import com.example.controle_aerien.entities.Avion;
import com.example.controle_aerien.entities.Point;
import com.example.controle_aerien.entities.TypeAvion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
    private Point Position;

    // Constructors
    public AvionDTO(Avion avion) {
        this.id = avion.getId();
        this.name = avion.getNom();
        this.type = avion.getType();
        this.consommation = avion.getConsommation();
        this.capacite = avion.getCapacite();
        this.disponibilite = avion.isDisponibilite();
        this.speed = avion.getSpeed();
        this.Position = avion.getPosition();
        this.IdAeroport=avion.getAeroport().getId();
    }
    public static List<AvionDTO> convertToDTOList(List<Avion> avions) {
        return avions.stream()
                .map(AvionDTO::new)
                .collect(Collectors.toList());
    }
}
