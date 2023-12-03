package com.example.controle_aerien.services;

import com.example.controle_aerien.dao.AeroportRepository;
import com.example.controle_aerien.dao.VolRepository;
import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.Vol;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class VolService {
    @Autowired
    private VolRepository volRepository;
    public void addVol(Vol vol)
    {
        volRepository.save(vol);
    }
    public Vol getVolById(Long id)
    {
        return volRepository.findById(id).get();
    }
    public List<Vol> getAllVol()
    {
        return volRepository.findAll();
    }
    public void deleteVolById(Long Id)
    {
        volRepository.deleteById(Id);
    }
}
