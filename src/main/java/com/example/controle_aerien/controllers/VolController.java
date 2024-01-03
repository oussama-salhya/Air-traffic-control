package com.example.controle_aerien.controllers;

import com.example.controle_aerien.DTO.VolDTO;
import com.example.controle_aerien.dao.AeroportRepository;
import com.example.controle_aerien.dao.DistanceAeroportRepository;
import com.example.controle_aerien.entities.*;
import com.example.controle_aerien.services.AeroportService;
import com.example.controle_aerien.services.AvionService;
import com.example.controle_aerien.services.DistanceAeroportService;
import com.example.controle_aerien.services.VolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class VolController {

    @Autowired
    private VolService volService;
    @Autowired
    private AvionService avionService;
    @Autowired
    private AeroportService aeroportService;
    @Autowired
    private DistanceAeroportRepository distanceAeroportRepository;


    @GetMapping("/vols")
    public List<Vol> getAllVoll()
    {
        return volService.getAllVol();
    }
    @GetMapping("/volsEscale/{id}")
    public List<Vol> getAllVollEscale(@PathVariable Long id)
    {
        List<Vol> vols = volService.getAllVol();
        List <Vol> volsEscale = new ArrayList<>();
        for (Vol vol : vols) {
            if (vol.getParentVol() != null){
            if(vol.getParentVol().getId()==id)
            {
                volsEscale.add(vol);
            }

            }
        }
        return volsEscale;
    }


    @GetMapping("/startSimulation")
    public List<Vol> startSimulation()
    {
        List<Vol> vols = volService.getAllVol();
        for(Vol vol : vols)
        {
            if(vol.isArrived()==false)
            {
                volService.StartVolGlobal(vol);
            }
        }
        return vols;
    }
//    @GetMapping("/startVolGlobalGloabal")
//    public void startVolGlobalGlobal()
//    {   Vol vol = volService.getVolById();
//        volService.StartVolGlobal(vol);
//    }

    @GetMapping("/vols/{id}")
    public ResponseEntity<VolDTO> getVolById(@PathVariable Long id)
    {
        Vol vol = volService.getVolById(id);
        if(vol!=null) {
            VolDTO volDTO = vol.toDTO();
            return ResponseEntity.ok(volDTO);//200 OK
        }
        else
            return ResponseEntity.notFound().build();//404 NOT FOUND
    }
    @PostMapping("/create_vol")
    public void saveVol(@RequestBody Vol vol)
    {
        volService.saveVol(vol);
    }

    @PutMapping("/update_vol/{id}")
    public ResponseEntity<Vol> updateVol(@PathVariable Long id , @RequestBody Vol newVol)
    {
        Vol oldVol = volService.getVolById(id);

        if(oldVol == null)
        {
            return ResponseEntity.notFound().build();
        }
        oldVol.setAvion(newVol.getAvion());
        oldVol.setHeureDepart(newVol.getHeureDepart());
        oldVol.setHeureArrivee(newVol.getHeureArrivee());
        oldVol.setAeroportDepart(newVol.getAeroportDepart());
        oldVol.setAeroportArrivee(newVol.getAeroportArrivee());
        volService.saveVol(oldVol);
        return ResponseEntity.ok(oldVol);
    }
    @DeleteMapping ("/delete_vol/{id}")
    public void deleteVolById(@PathVariable Long id)
    {
        volService.deleteVolById(id);
    }

    @PostMapping("/addVol")
    public ResponseEntity<String> addVol(@RequestBody Vol vol) {

        Avion avion = new Avion("avion" + vol.getId());
        avion = avionService.saveAvion(avion);
        avion.setNom("avion" + avion.getId());
        DistanceAeroport distanceAeroport = distanceAeroportRepository.findByDistanceAeroportId(vol.getAeroportDepart(),vol.getAeroportArrivee());
        if(distanceAeroport == null)
        {
             distanceAeroport = distanceAeroportRepository.findByDistanceAeroportId(vol.getAeroportArrivee(),vol.getAeroportDepart());
        }
        if(distanceAeroport == null)
        {
            avion.setType(TypeAvion.LONG);
            avion.setSpeed(500);
            avion.setConsommation(60);
            avion.setCapacite(6000);
        }
        else// non nul
        {
            int distance = distanceAeroport.getDistance();

            if (distance < 230) {
                avion.setType(TypeAvion.COURT);
                //4L/100Km
                avion.setConsommation(40);
                avion.setSpeed(300);
                avion.setCapacite(2000);
            } else if (distance<294) {
                avion.setType(TypeAvion.MOYEN);
                avion.setConsommation(50);
                avion.setSpeed(400);
                avion.setCapacite(4000);
            } else {
                avion.setType(TypeAvion.LONG);
                avion.setSpeed(500);
                avion.setConsommation(60);
                avion.setCapacite(6000);
            }
        }
        avion = avionService.saveAvion(avion);
        aeroportService.AddAvionToAeroport(vol.getAeroportDepart().getId(), avion.getId());
        volService.addVol(vol.getAeroportDepart().getId(), vol.getAeroportArrivee().getId(), null);
        return ResponseEntity.ok("Vol ajouté avec succès!");
    }

}
