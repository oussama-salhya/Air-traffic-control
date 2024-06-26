package com.example.controle_aerien.services;

import com.example.controle_aerien.dao.DistanceAeroportRepository;
import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.DistanceAeroport;
import com.example.controle_aerien.entities.DistanceAeroportId;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class DistanceAeroportService {
    @Autowired
    private DistanceAeroportRepository distanceAeroportRepository;

    public void saveDistanceAeroport(DistanceAeroport distanceAeroport)
    {
        distanceAeroportRepository.save(distanceAeroport);
    }
    public DistanceAeroport getDistanceAeroportById(DistanceAeroportId id)
    {
        return distanceAeroportRepository.findById(id).get();
    }

    public List<DistanceAeroport> getAllDistanceAeroport()
    {
        return distanceAeroportRepository.findAll();
    }
    public double getDistanceBetweenTwoAeroports(Aeroport aeroport1, Aeroport aeroport2)
    {
        DistanceAeroportId id = new DistanceAeroportId(aeroport1, aeroport2);
        DistanceAeroport distanceAeroport = distanceAeroportRepository.findDistanceAeroportByDistanceAeroportId(id);
        return distanceAeroport.getDistance();
    }
}



