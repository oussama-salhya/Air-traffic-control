package com.example.controle_aerien.Djikstra.classes;

public class Pair implements Comparable<Pair>{
    public int vtx; // The vertex
    public String psf; // The path from source node
    public int wsf; // THe weight of the path

    public Pair(int vtx, String psf, int wsf) {
        this.vtx = vtx;
        this.psf = psf;
        this.wsf = wsf;
    }

    // To compare two paths/routes, just compare their weights
    public int compareTo(Pair o) {
        return this.wsf - o.wsf;
    }
}
