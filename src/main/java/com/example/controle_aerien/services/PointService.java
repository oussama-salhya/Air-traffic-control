package com.example.controle_aerien.services;

import com.example.controle_aerien.dao.PointRepository;
import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.Point;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PointService {
    @Autowired
    private PointRepository pointRepo;

    public void addPoint(Point point)
    {
        pointRepo.save(point);
    }
    public Point getPointById(Long id)
    {
        return pointRepo.findById(id).get();
    }
    public List<Point> getAllPoint()
    {
        return pointRepo.findAll();
    }
    public void deletePointById(Long id)
    {
        pointRepo.deleteById(id);
    }

}
