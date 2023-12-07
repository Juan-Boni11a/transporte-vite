package com.example.transportsapi.service;

import com.example.transportsapi.models.MovilizationValidities;
import com.example.transportsapi.repository.MovilizationValiditiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovilizationValiditiesService {
    @Autowired
    private MovilizationValiditiesRepository movilizationValiditiesRepository;


    public List<MovilizationValidities> getAll() {
        return movilizationValiditiesRepository.findAll();
    }

    public MovilizationValidities createOrUpdate(MovilizationValidities movilizationValidity) {
        return movilizationValiditiesRepository.save(movilizationValidity);
    }

    public void delete(Long id) {
        movilizationValiditiesRepository.deleteById(id);
    }

}
