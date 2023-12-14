package com.example.controle_aerien.Djikstra.classes;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node {
    private Long id;
    private double distance;
}