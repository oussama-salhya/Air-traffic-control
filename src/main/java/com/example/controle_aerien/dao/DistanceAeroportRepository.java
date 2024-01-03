package com.example.controle_aerien.dao;

import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.DistanceAeroport;
import com.example.controle_aerien.entities.DistanceAeroportId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DistanceAeroportRepository extends JpaRepository<DistanceAeroport, DistanceAeroportId> {
        DistanceAeroport findDistanceAeroportByDistanceAeroportId(DistanceAeroportId distanceAeroportId);
        @Query("SELECT da FROM DistanceAeroport da WHERE da.distanceAeroportId.aeroport1 = :aeroport1 AND da.distanceAeroportId.aeroport2 = :aeroport2")
        DistanceAeroport findByDistanceAeroportId(@Param("aeroport1") Aeroport aeroport1, @Param("aeroport2") Aeroport aeroport2);
}

