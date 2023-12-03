package com.example.controle_aerien.services;

import com.example.controle_aerien.dao.AeroportRepository;
import com.example.controle_aerien.entities.Aeroport;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class AeroportService {
    @Autowired
    private AeroportRepository aeroportRepo;

    public void addAeroport(Aeroport aeroport)
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

}
