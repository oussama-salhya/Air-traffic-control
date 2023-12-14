package com.example.controle_aerien.Djikstra.services;

import com.example.controle_aerien.Djikstra.classes.Edge;
import com.example.controle_aerien.Djikstra.classes.Graph;
import com.example.controle_aerien.dao.DistanceAeroportRepository;
import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.Point;
import com.example.controle_aerien.exceptions.AeroportNotfound;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


