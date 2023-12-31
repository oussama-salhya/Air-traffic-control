package com.example.controle_aerien.services;

import com.example.controle_aerien.dao.AeroportRepository;
import com.example.controle_aerien.dao.AvionRepository;
import com.example.controle_aerien.dao.DistanceAeroportRepository;
import com.example.controle_aerien.entities.*;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class AeroportService {

    @Autowired
    private AeroportRepository aeroportRepo;
    @Autowired
    private DistanceAeroportService distanceAeroportService;
    @Autowired
    private AvionService avionService;

    public void saveAeroport(Aeroport aeroport)
    {
        aeroportRepo.save(aeroport);
        List<DistanceAeroport> distancesAeroports;
        distancesAeroports = calculerDistancesAeroports(aeroport);
        for (DistanceAeroport distanceAeroport : distancesAeroports) {
            distanceAeroportService.saveDistanceAeroport(distanceAeroport);
        }
    }
    void removeAvionFromAvionsVol(long aeroportId, Avion avion) {
        synchronized (this) {
            aeroportRepo.removeAvionFromAvionsVol(aeroportId, avion);
        }
    }
    void removeAvionFromAvionsSol(long aeroportId, Avion avion) {
        synchronized (this) {
            aeroportRepo.removeAvionFromAvionsSol(aeroportId, avion);
        }
    }
    public Aeroport getAeroportById(Long id)
    {
        return aeroportRepo.findById(id).get();
    }
    public List<Aeroport> getAllAeroport()
    {
        return aeroportRepo.findAll();
    }
    public void deleteAeroportById(Long id)
    {
        aeroportRepo.deleteById(id);
    }
    public List<Avion> getAllAvionenSolByAeroport(Aeroport aeroport)
    {
        return aeroport.getAvionsSol();
    }

    public List<DistanceAeroport> calculerDistancesAeroports(Aeroport newaeroport)
    {
        List<Aeroport> aerportList = new ArrayList<Aeroport>();
        aerportList = aeroportRepo.findAll();
        List<DistanceAeroport>distancesAeroports = new ArrayList<>();
        if(aerportList.size()>1)
        {
            for(Aeroport aeroport : aerportList)
            {
                if(aeroport.getId() != newaeroport.getId()) {
                    int distance = (int) Math.round(Math.sqrt(Math.pow(newaeroport.getPosition().getX() - aeroport.getPosition().getX(), 2) + Math.pow(newaeroport.getPosition().getY() - aeroport.getPosition().getY(), 2)));
                    if(distance <=1000) {
                        DistanceAeroportId distanceAeroportId = new DistanceAeroportId(newaeroport, aeroport);

                        DistanceAeroport distanceAeroport = new DistanceAeroport(distanceAeroportId, distance);

                        distancesAeroports.add(distanceAeroport);
                    }
                }
            }
        }
        return distancesAeroports;
    }
    public Aeroport AddAvionToAeroport(Long idAero , Long idAvion)
    {
        Aeroport aeroport = aeroportRepo.findById(idAero).get();
        Avion avion = avionService.getAvionById(idAvion);
        if(aeroport.getAvionsSol().size() < aeroport.getNbPlaceSol() && aeroport.isDisponibilite() && avion.isDisponibilite()) {
            avion.setAeroport(aeroport);
            avion.setPosition(new Point(aeroport.getPosition().getX(),aeroport.getPosition().getY()));
            avionService.saveAvion(avion);
            aeroport.getAvionsSol().add(avion);
        }
        return aeroportRepo.save(aeroport);
    }

    public Aeroport removeAvionFromAeroport(Long idAero, Long idAvion) {
        Aeroport aeroport = aeroportRepo.findById(idAero).orElse(null);
        Avion avion = avionService.getAvionById(idAvion);

        if (aeroport != null && avion != null && aeroport.getAvionsSol().contains(avion)) {
            avion.setAeroport(null);
            System.out.println("CONTAINSS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            avionService.saveAvion(avion);

            aeroport.getAvionsSol().remove(avion);
            aeroport.getAvionsVol().remove(avion);
            aeroport = aeroportRepo.save(aeroport);
        }

        return aeroport;
    }

}
