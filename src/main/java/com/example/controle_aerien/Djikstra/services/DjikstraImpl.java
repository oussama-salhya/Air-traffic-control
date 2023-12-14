package com.example.controle_aerien.Djikstra.services;

import com.example.controle_aerien.Djikstra.classes.Pair;
import com.example.controle_aerien.entities.DistanceAeroport;
import com.example.controle_aerien.services.AeroportService;
import com.example.controle_aerien.services.DistanceAeroportService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.controle_aerien.Djikstra.classes.Edge;
import com.example.controle_aerien.Djikstra.classes.Graph;
import com.example.controle_aerien.Djikstra.classes.Node;
import com.example.controle_aerien.entities.Aeroport;

import java.util.*;

@Service
public class DjikstraImpl {
    @Autowired
    private  AeroportService aeroportService;

    @Autowired
    private  DistanceAeroportService distanceAeroportService;

    //IF RETURN NULL MEANS NO PATH IS FROM aeroport_depart TO aeroport_arriv
    public HashMap<String ,Integer> djisktraalgo(long aeroport_depart, long aeroport_arriv) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        List<DistanceAeroport> distanceAeroportList = distanceAeroportService.getAllDistanceAeroport();
        System.out.println(distanceAeroportList.size());
        for(DistanceAeroport ds : distanceAeroportList)
        {
            System.out.println(ds.getDistance());
        }

        String[] tab = new String[0];

        int n = aeroportService.getAllAeroport().size();

        int weight = 0;

        int vtces = n;
        ArrayList<Edge>[] graph = new ArrayList[vtces + 1];

        for (int i = 0; i < vtces + 1; i++) {
            graph[i] = new ArrayList<>();
        }

        // Read the number of edges edges
        int edges = distanceAeroportService.getAllDistanceAeroport().size();

        // Read the details of each of the edge.
        for (DistanceAeroport distanceAeroport : distanceAeroportList) {
            int v1 = (int) distanceAeroport.getDistanceAeroportId().getAeroport1().getId();
            int v2 = (int) distanceAeroport.getDistanceAeroportId().getAeroport2().getId();
            int wt = distanceAeroport.getDistance();
            graph[v1].add(new Edge(v1, v2, wt));
            graph[v2].add(new Edge(v2, v1, wt));
        }

        // Input the source vertex
        int src = (int) aeroport_depart;
        boolean[] visited = new boolean[n + 1];

        // Priority Queue to hold the pairs.
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(src, src + "", 0));


        while (pq.size() > 0) {

            Pair rem = pq.remove();
            if (visited[rem.vtx] == true) {
                continue;
            }


            visited[rem.vtx] = true;
            
            if (rem.vtx == (int) aeroport_arriv) {
                System.out.println(rem.vtx + " via " + rem.psf + " @ " + rem.wsf);
                tab = rem.psf.split("->");
                weight = rem.wsf;
            }


            for (Edge e : graph[rem.vtx]) {
                if (visited[e.getNbr()] == false) {
                    pq.add(new Pair(e.getNbr(), rem.psf + "->" + e.getNbr(), rem.wsf + e.getWt()));
                }
            }

        }
        if(tab != null && tab.length > 0)
        {
                HashMap<String, Integer> djikstraresultat= new HashMap<String,Integer>();
                Integer[] tabint = new Integer[tab.length];
                for(int i=0 ; i < tab.length ; i++)
                {
                    tabint[i] = Integer.parseInt(tab[i]);
                    djikstraresultat.put("T"+i,tabint[i]);
                }
                djikstraresultat.put("D",weight);
            //return tabint;
            return djikstraresultat;
        }
        return null;
    }

}

