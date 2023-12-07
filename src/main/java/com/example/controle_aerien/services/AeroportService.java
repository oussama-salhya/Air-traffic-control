package com.example.controle_aerien.services;

import com.example.controle_aerien.dao.AeroportRepository;
import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.Avion;
import com.example.controle_aerien.entities.DistanceAeroport;
import com.example.controle_aerien.entities.DistanceAeroportId;
import lombok.AllArgsConstructor;
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

    public void saveAeroport(Aeroport aeroport)
    {
        aeroportRepo.save(aeroport);
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
                if(aeroport != newaeroport) {
                    double distance = Math.sqrt(Math.pow(newaeroport.getPosition().getX() - aeroport.getPosition().getX(), 2) + Math.pow(newaeroport.getPosition().getY() - aeroport.getPosition().getY(), 2));

                    DistanceAeroportId distanceAeroportId = new DistanceAeroportId(newaeroport,aeroport);

                    DistanceAeroport distanceAeroport = new DistanceAeroport(distanceAeroportId,distance);

                    distancesAeroports.add(distanceAeroport);
                }
            }
        }
        return distancesAeroports;
    }

}
