package com.example.controle_aerien.dao;

import com.example.controle_aerien.entities.Vol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VolRepository extends JpaRepository<Vol, Long> {

}

