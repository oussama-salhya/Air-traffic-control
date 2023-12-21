package com.example.controle_aerien.controllers;

import com.example.controle_aerien.DTO.AeroportDTO;
import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.DistanceAeroport;
import com.example.controle_aerien.services.AeroportService;
import com.example.controle_aerien.services.DistanceAeroportService;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
public class AeroportController {

    @Autowired
    private AeroportService aeroportService;
    @Autowired
    private DistanceAeroportService distanceAeroportService;


    @GetMapping("/aeroports")
    public List<Aeroport> getAllAeroport() {
        return aeroportService.getAllAeroport();
    }
    @GetMapping("/aeroports/{id}")
    public ResponseEntity<AeroportDTO> getAeroportById(@PathVariable Long id) {
        Aeroport aeroport = aeroportService.getAeroportById(id);
        if (aeroport != null) {
            AeroportDTO aeroportDTO = new AeroportDTO(aeroport);
            return ResponseEntity.ok(aeroportDTO);//200 OK
        }
        else
            return ResponseEntity.notFound().build();//404 NOT FOUND
    }

    @PostMapping("/create_aeroport")
    public void saveAeroport(@RequestBody Aeroport newaeroport) {
        aeroportService.saveAeroport(newaeroport);
    }

    @PutMapping("/update_aeroport/{id}")
    public ResponseEntity<Aeroport> updateAeroport(@PathVariable Long id, @RequestBody Aeroport newaeroport) {
        Aeroport oldaeroport = aeroportService.getAeroportById(id);

        if (oldaeroport == null) {
            return ResponseEntity.notFound().build();
        }
        oldaeroport.setNom(newaeroport.getNom());
        oldaeroport.setNbPiste(newaeroport.getNbPiste());
        oldaeroport.setNbPlaceSol(newaeroport.getNbPlaceSol());
        oldaeroport.setTmpAccPist(newaeroport.getTmpAccPist());
        oldaeroport.setDelaiAntiColis(newaeroport.getDelaiAntiColis());
        oldaeroport.setDelaiAttente(newaeroport.getDelaiAttente());
        oldaeroport.setTmpDecolage(newaeroport.getTmpDecolage());
        oldaeroport.setDurreboucleatt(newaeroport.getDurreboucleatt());
        oldaeroport.setAvionsSol(newaeroport.getAvionsSol());
        oldaeroport.setPosition(newaeroport.getPosition());
        oldaeroport.setAvionsVol(newaeroport.getAvionsVol());
        oldaeroport.setDisponibilite(newaeroport.isDisponibilite());
        oldaeroport.setSecteur(newaeroport.getSecteur());
        oldaeroport.setDistancesFromHere(newaeroport.getDistancesFromHere());
        oldaeroport.setDistancesToHere(newaeroport.getDistancesToHere());
        aeroportService.saveAeroport(oldaeroport);
        return ResponseEntity.ok(oldaeroport);
    }
}
