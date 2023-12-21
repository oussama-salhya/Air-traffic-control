package com.example.controle_aerien.DTO;

import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AeroportDTO {
    private long id;
    private int idtest;
    private String nom;
    private int nbPiste;
    private int nbPlaceSol;
    private double tmpAccPist;
    private double delaiAntiColis;
    private double delaiAttente;
    private double tmpDecolage;
    private double durreboucleatt;
    private double secteur;
    private Point position;
    private boolean disponibilite;
    private List<AvionDTO> avionsSol;
    private List<AvionDTO> avionsVol;

    // Constructors
    public AeroportDTO(Aeroport aeroport) {
        this.id = aeroport.getId();
        this.idtest = aeroport.getIdtest();
        this.nom = aeroport.getNom();
        this.nbPiste = aeroport.getNbPiste();
        this.nbPlaceSol = aeroport.getNbPlaceSol();
        this.tmpAccPist = aeroport.getTmpAccPist();
        this.delaiAntiColis = aeroport.getDelaiAntiColis();
        this.delaiAttente = aeroport.getDelaiAttente();
        this.tmpDecolage = aeroport.getTmpDecolage();
        this.durreboucleatt = aeroport.getDurreboucleatt();
        this.secteur = aeroport.getSecteur();
        this.position = aeroport.getPosition();
        this.disponibilite = aeroport.isDisponibilite();
        this.avionsSol = AvionDTO.convertToDTOList(aeroport.getAvionsSol());
        this.avionsVol = AvionDTO.convertToDTOList(aeroport.getAvionsVol());
    }

    // Static method to convert a list of Aeroport entities to a list of AeroportDTO
    public static List<AeroportDTO> convertToDTOList(List<Aeroport> aeroports) {
        return aeroports.stream().map(AeroportDTO::new).collect(Collectors.toList());
    }
}
