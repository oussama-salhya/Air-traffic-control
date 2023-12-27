package com.example.controle_aerien;

import com.example.controle_aerien.Djikstra.classes.Edge;
import com.example.controle_aerien.Djikstra.classes.Graph;
import com.example.controle_aerien.Djikstra.classes.Pair;
import com.example.controle_aerien.Djikstra.services.DjikstraImpl;
import com.example.controle_aerien.entities.*;
import com.example.controle_aerien.exceptions.AeroportNotfound;
import com.example.controle_aerien.services.AeroportService;
import com.example.controle_aerien.services.AvionService;
import com.example.controle_aerien.services.DistanceAeroportService;
import com.example.controle_aerien.services.VolService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@SpringBootApplication
@EnableScheduling
public class ControleAerienApplication {

    @Autowired
    private AeroportService aeroportService;

    @Autowired
    private AvionService avionService;

    @Autowired
    private DistanceAeroportService distanceAeroportService;

    @Autowired
    private VolService volService;
    @Autowired
    private DjikstraImpl djik;


    public static void main(String[] args) {
        SpringApplication.run(ControleAerienApplication.class, args);
    }

    @PostConstruct
    public void init() throws AeroportNotfound, IOException {
//        Aeroport aeroport0 = new Aeroport(1, new Point(579, 352));
        Aeroport aeroport0 = new Aeroport(1, new Point(31.25, -8));
        Aeroport aeroport1 = new Aeroport(2, new Point(579, 382));
        Aeroport aeroport2 = new Aeroport(3, new Point(390, 466));
        Aeroport aeroport3 = new Aeroport(4, new Point(351, 370));
        Aeroport aeroport4 = new Aeroport(5, new Point(728, 384));
        Aeroport aeroport5 = new Aeroport(6, new Point(960, 517));
        Aeroport aeroport6 = new Aeroport(7, new Point(998, 357));
        Aeroport aeroport7 = new Aeroport(8, new Point(329, 270));
//        Aeroport aeroport8 = new Aeroport(9, new Point(677, 559));
        Aeroport aeroport8 = new Aeroport(9, new Point(38.73, -102.38));

        aeroportService.saveAeroport(aeroport0);
        aeroportService.saveAeroport(aeroport1);
        aeroportService.saveAeroport(aeroport2);
        aeroportService.saveAeroport(aeroport3);
        aeroportService.saveAeroport(aeroport4);
        aeroportService.saveAeroport(aeroport5);
        aeroportService.saveAeroport(aeroport6);
        aeroportService.saveAeroport(aeroport7);
        aeroportService.saveAeroport(aeroport8);

        Avion avion1 = new Avion("avion1");
        Avion avion2 = new Avion("avion2");
        Avion avion3 = new Avion("avion3");
        System.out.println("D3 : " + avion1.isDisponibilite());

        avion1 =avionService.saveAvion(avion1);
        avion2 =avionService.saveAvion(avion2);
        avion3 =avionService.saveAvion(avion3);


        for(Avion avion : avionService.getALLAvions())
        {
            System.out.println("avion d: " + avion.isDisponibilite() );
        }
        System.out.println("D2 : " + avion1.isDisponibilite());

        //Avion avion = avionService.getALLAvions().get(0);

        aeroportService.AddAvionToAeroport(aeroport0.getId(),avion1.getId());
//        aeroportService.AddAvionToAeroport(aeroport0.getId(),avion2.getId());
//        aeroportService.AddAvionToAeroport(aeroport0.getId(),avion3.getId());


        for(Avion avion : avionService.getALLAvions())
        {
            System.out.println("avion d: " + avion.isDisponibilite() );
        }
        System.out.println("D1 : " + avion1.isDisponibilite());

        Vol vol = volService.addVol(aeroport0.getId(),aeroport8.getId(),null);
        Vol vol1 = volService.addVol(aeroport8.getId(),aeroport0.getId(),null);
//        Vol vol2 = volService.addVol(aeroport0.getId(),aeroport4.getId(),null);
//        Vol vol3 = volService.addVol(aeroport0.getId(),aeroport1.getId(),null);


        for(Avion avion : avionService.getALLAvions())
        {
            System.out.println("avion d: " + avion.isDisponibilite() );
        }
        System.out.println("D4 : " + avion1.isDisponibilite());

        for(Avion avion : avionService.getALLAvions())
        {
            System.out.println("avion d: " + avion.isDisponibilite() );
        }
        volService.StartVolGlobal(vol);
        volService.StartVolGlobal(vol1);
        /*volService.StartVolGlobal(vol2);
        volService.StartVolGlobal(vol3);*/

        /*HashMap<String ,Integer> dji = djik.djisktraalgo(vol.getAeroportDepart().getId(),vol.getAeroportArrivee().getId());

        for (Map.Entry<String, Integer> entry : dji.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }*/

        /*volService.StartVol(vol.getId());

        Vol vol2 = volService.addVol(aeroport4.getId(),aeroport0.getId());

        volService.StartVol(vol2.getId());*/
        /*
        List<DistanceAeroport> distanceAeroportList = distanceAeroportService.getAllDistanceAeroport();

        for(DistanceAeroport ds : distanceAeroportList)
        {
            System.out.println(ds.getDistance());
        }
*/
    }
}

