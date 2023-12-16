package com.example.controle_aerien.dao;

import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.Avion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.annotation.Transactional;

public interface AeroportRepository extends JpaRepository<Aeroport, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Aeroport a WHERE a.id = :aeroportId AND :avion MEMBER OF a.avionsSol")
    void removeAvionFromAvionsSol(@Param("aeroportId") long aeroportId, @Param("avion") Avion avion);
    @Transactional
    @Modifying
    @Query("DELETE FROM Aeroport a WHERE a.id = :aeroportId AND :avion MEMBER OF a.avionsVol")
    void removeAvionFromAvionsVol(@Param("aeroportId") long aeroportId, @Param("avion") Avion avion);

}
