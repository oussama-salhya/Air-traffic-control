package com.example.controle_aerien;

import com.example.controle_aerien.Djikstra.classes.Edge;
import com.example.controle_aerien.Djikstra.classes.Graph;
import com.example.controle_aerien.Djikstra.classes.Pair;
import com.example.controle_aerien.Djikstra.services.DjikstraImpl;
import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.DistanceAeroport;
import com.example.controle_aerien.entities.Point;
import com.example.controle_aerien.exceptions.AeroportNotfound;
import com.example.controle_aerien.services.AeroportService;
import com.example.controle_aerien.services.DistanceAeroportService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@SpringBootApplication
public class ControleAerienApplication {

    @Autowired
    private AeroportService aeroportService;

    @Autowired
    private DistanceAeroportService distanceAeroportService;

    @Autowired
    DjikstraImpl djik ;

    public static void main(String[] args) {
        SpringApplication.run(ControleAerienApplication.class, args);
    }

    @PostConstruct
    public void init() throws AeroportNotfound, IOException {
        Aeroport aeroport0 = new Aeroport(1, new Point(579, 352));
        Aeroport aeroport1 = new Aeroport(2, new Point(579, 382));
        Aeroport aeroport2 = new Aeroport(3, new Point(390, 466));
        Aeroport aeroport3 = new Aeroport(4, new Point(351, 370));
        Aeroport aeroport4 = new Aeroport(5, new Point(728, 384));
        Aeroport aeroport5 = new Aeroport(6, new Point(960, 517));
        Aeroport aeroport6 = new Aeroport(7, new Point(998, 357));
        Aeroport aeroport7 = new Aeroport(8, new Point(329, 270));
        Aeroport aeroport8 = new Aeroport(9, new Point(677, 559));

        aeroportService.saveAeroport(aeroport0);
        aeroportService.saveAeroport(aeroport1);
        aeroportService.saveAeroport(aeroport2);
        aeroportService.saveAeroport(aeroport3);
        aeroportService.saveAeroport(aeroport4);
        aeroportService.saveAeroport(aeroport5);
        aeroportService.saveAeroport(aeroport6);
        aeroportService.saveAeroport(aeroport7);
        aeroportService.saveAeroport(aeroport8);

    }
}

