package com.example.controle_aerien;

import com.example.controle_aerien.controllers.PointRepository;
import com.example.controle_aerien.entities.Point;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ControleAerienApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControleAerienApplication.class, args);
    }

}
