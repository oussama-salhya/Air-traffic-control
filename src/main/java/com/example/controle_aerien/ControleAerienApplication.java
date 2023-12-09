package com.example.controle_aerien;

import com.example.controle_aerien.Djikstra.classes.Edge;
import com.example.controle_aerien.Djikstra.classes.Graph;
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

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ControleAerienApplication {

    @Autowired
    private AeroportService aeroportService;

    @Autowired
    private DistanceAeroportService distanceAeroportService;

    public static void main(String[] args) {
        SpringApplication.run(ControleAerienApplication.class, args);
    }

    @PostConstruct
    public void init() throws AeroportNotfound {
        Aeroport aeroport0 = new Aeroport(1, new Point(0, 0));
        Aeroport aeroport1 = new Aeroport(2, new Point(0, 1));
        Aeroport aeroport2 = new Aeroport(3, new Point(1, 0));
        Aeroport aeroport3 = new Aeroport(4, new Point(1, 1));
        Aeroport aeroport4 = new Aeroport(5, new Point(2, 2));

        aeroportService.saveAeroport(aeroport0);
        aeroportService.saveAeroport(aeroport1);
        aeroportService.saveAeroport(aeroport2);
        aeroportService.saveAeroport(aeroport3);
        aeroportService.saveAeroport(aeroport4);

        List<DistanceAeroport> distanceAeroportList = distanceAeroportService.getAllDistanceAeroport();
        List<Edge> edges = new ArrayList<>();
        for (DistanceAeroport distanceAeroport : distanceAeroportList) {
            Edge edge = new Edge(
                    distanceAeroport.getDistanceAeroportId().getAeroport1().getId(),
                    distanceAeroport.getDistanceAeroportId().getAeroport2().getId(),
                    distanceAeroport.getDistance()
            );
            edges.add(edge);
        }

        // total number of nodes in the graph
        int n = aeroportService.getAllAeroport().size();

        // construct graph
        Graph graph = new Graph(edges);

        // run the Dijkstra’s algorithm from every node
        System.out.println(DjikstraImpl.findShortestPaths(graph, aeroport0.getId(),aeroport4.getId()));
    }
}
