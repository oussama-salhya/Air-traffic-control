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
    public void saveAvion(Avion avion){
        avionrepository.save(avion);
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
