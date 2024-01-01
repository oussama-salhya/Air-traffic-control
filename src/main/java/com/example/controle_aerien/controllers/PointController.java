package com.example.controle_aerien.controllers;


import com.example.controle_aerien.entities.Point;
import com.example.controle_aerien.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin

public class PointController {

    @Autowired
    private PointService pointService;


    @GetMapping("/points")
    public List<Point> getAllPoints()
    {
        return pointService.getAllPoint();
    }

    @GetMapping("/points/{id}")
    public ResponseEntity<Point> getpointById(@PathVariable Long id)
    {
        Point point = pointService.getPointById(id);
        if(point!=null)
            return ResponseEntity.ok(point);
        else
            return ResponseEntity.notFound().build();
    }
    @PostMapping("/create_point")
    public void savePoint(@RequestBody Point point)
    {
        pointService.addPoint(point);
    }

    @DeleteMapping ("/delete_point/{id}")
    public void deletePoint(@PathVariable Long id)
    {
        pointService.deletePointById(id);
    }
}

