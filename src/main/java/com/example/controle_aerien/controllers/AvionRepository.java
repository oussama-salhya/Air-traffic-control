package com.example.controle_aerien.controllers;

import com.example.controle_aerien.entities.Avion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvionRepository extends JpaRepository<Avion, Long> {

}

