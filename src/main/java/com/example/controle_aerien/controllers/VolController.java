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
    private DistanceAeroportService distanceAeroportService;


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
            volService.StartVolGlobal(vol);
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
        try {

            Avion avion = new Avion("avion" + vol.getId());
            avion = avionService.saveAvion(avion);
            avion.setNom("avion" + avion.getId());
            aeroportService.AddAvionToAeroport(vol.getAeroportDepart().getId(), avion.getId());
            volService.addVol(vol.getAeroportDepart().getId(), vol.getAeroportArrivee().getId(), null);
            return ResponseEntity.ok("Vol ajouté avec succès!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: Vol non ajouté!");
        }
    }
//    @PostMapping("/addVol")
//    public ResponseEntity<String> addVol(@RequestBody Vol vol) {
//        try {
//
//            Avion avion = new Avion();
//            System.out.println("ssss");
//            avion = avionService.saveAvion(avion);
//            System.out.println("ssss");
//            System.out.println("vol = " + vol);
//            Aeroport aeroportDepart =  aeroportService.getAeroportById(vol.getAeroportDepart().getId());
//            System.out.println("aeroportDepart = " + aeroportDepart);
//            Aeroport aeroportArrive =  aeroportService.getAeroportById(vol.getAeroportArrivee().getId());
//            System.out.println("aeroportArrive = " + aeroportArrive);
//             double distance = distanceAeroportService.getDistanceBetweenTwoAeroports(aeroportDepart, aeroportArrive);
//             System.out.println("distance = " + distance);
//            if (distance < 230) {
//                avion.setType(TypeAvion.COURT);
//
//            } else if (distance<294) {
//                avion.setType(TypeAvion.MOYEN);
//
//            } else {
//                avion.setType(TypeAvion.LONG);
//            }
//            avion = avionService.doConsommation(avion);

//            avion.setNom("avion" + avion.getId());
//            System.out.println("ssss");
//            avion = avionService.saveAvion(avion);
//            System.out.println("ssss");
//            aeroportService.AddAvionToAeroport(vol.getAeroportDepart().getId(), avion.getId());
//            System.out.println("ssss");
//            volService.addVol(vol.getAeroportDepart().getId(), vol.getAeroportArrivee().getId(), null);
//            return ResponseEntity.ok("Vol ajouté avec succès!");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return ResponseEntity.badRequest().body("Erreur: Vol non ajouté!");
//        }
//    }

}
