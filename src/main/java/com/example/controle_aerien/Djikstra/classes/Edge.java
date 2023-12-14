package com.example.controle_aerien.Djikstra.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {

    int src; // One of the nodes
    int nbr; // The second node forming the edge
    int wt; // Weight of the edge

}
