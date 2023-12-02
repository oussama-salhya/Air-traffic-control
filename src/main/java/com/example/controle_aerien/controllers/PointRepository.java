package com.example.controle_aerien.controllers;

import com.example.controle_aerien.entities.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {

}

