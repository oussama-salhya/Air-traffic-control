package com.example.controle_aerien.dao;

import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.DistanceAeroport;
import com.example.controle_aerien.entities.DistanceAeroportId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistanceAeroportRepository extends JpaRepository<DistanceAeroport, DistanceAeroportId> {
        DistanceAeroport findDistanceAeroportByDistanceAeroportId(DistanceAeroportId distanceAeroportId);
}

