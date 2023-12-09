package com.example.controle_aerien.Djikstra.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    public Long AeroportDepartId;
    public Long AeroportArriverId;
    public double weight;
}
