package com.example.controle_aerien.controllers;

import com.example.controle_aerien.DTO.VolDTO;
import com.example.controle_aerien.entities.Vol;
import com.example.controle_aerien.services.VolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class VolController {

    @Autowired
    private VolService volService;


    @GetMapping("/vols")
    public List<Vol> getAllVoll()
    {
        return volService.getAllVol();
    }

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
}
