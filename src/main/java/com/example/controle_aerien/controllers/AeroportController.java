package com.example.controle_aerien.controllers;

import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.services.AeroportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//@RequestMapping("/aeroports")
public class AeroportController {

    @Autowired
    private AeroportService aeroportService;


    @GetMapping("/aeroports")
    public List<Aeroport> getAllAeroport()
    {
        return aeroportService.getAllAeroport();
    }

    @GetMapping("/aeroports/{id}")
    public ResponseEntity<Aeroport> getAeroportById(@PathVariable Long id)
    {
        Aeroport aeroport = aeroportService.getAeroportById(id);
        if(aeroport!=null)
        return ResponseEntity.ok(aeroport);//200 OK
        else
            return ResponseEntity.notFound().build();//404 NOT FOUND
    }
    @PostMapping("/create_aeroport")
    public void saveAeroport(@RequestBody Aeroport aeroport)
    {
         aeroportService.saveAeroport(aeroport);
    }

    @PutMapping("/update_aeroport/{id}")
    public ResponseEntity<Aeroport> updateAeroport(@PathVariable Long id , @RequestBody Aeroport newaeroport)
    {
        Aeroport oldaeroport = aeroportService.getAeroportById(id);

        if(oldaeroport == null)
        {
            return ResponseEntity.notFound().build();
        }
        oldaeroport.setNom(newaeroport.getNom());
        aeroportService.saveAeroport(oldaeroport);
        return ResponseEntity.ok(oldaeroport);
    }
    @DeleteMapping ("/delete_aeroport/{id}")
    public void deleteAeroportById(@PathVariable Long id)
    {
        aeroportService.deleteAeroportById(id);
    }
}
