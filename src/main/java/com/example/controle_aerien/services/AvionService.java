package com.example.controle_aerien.services;
import com.example.controle_aerien.entities.Avion;
import com.example.controle_aerien.dao.AvionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
@Service
public class AvionService {
    @Autowired
    AvionRepository avionrepository ;
    public Avion getAvionById(Long idAvion){

        return avionrepository.findById(idAvion).get();
    }
    //update and save avion
    public Avion saveAvion(Avion avion){
        if(avion.getType().equals("COURT"))
        {
            avion.setConsommation(4);
            avion.setCapacite(20000);
        }
        else if(avion.getType().equals("MOYEN"))
        {
            avion.setConsommation(5);
            avion.setCapacite(40000);
        }
        else if(avion.getType().equals("LONG"))
        {
            avion.setConsommation(6);
            avion.setCapacite(80000);
        }
        return avionrepository.save(avion);
    }
    public List<Avion> getALLAvions(){
        return avionrepository.findAll();
    }
    public void deleteAvionByID(Long id){
        if(avionrepository.count()==0)
            return;
        else avionrepository.deleteById(id);
    }

}
