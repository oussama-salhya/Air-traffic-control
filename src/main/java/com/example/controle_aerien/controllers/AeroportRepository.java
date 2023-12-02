package com.example.controle_aerien.controllers;

import com.example.controle_aerien.entities.Aeroport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AeroportRepository extends JpaRepository<Aeroport, Long> {
}
