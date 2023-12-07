package com.example.transportsapi.service;

import com.example.transportsapi.models.MovilizationTypes;
import com.example.transportsapi.repository.MovilizationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovilizationTypeService {

    @Autowired
    private MovilizationTypeRepository movilizationTypeRepository;

    public List<MovilizationTypes> getAll() {
        return movilizationTypeRepository.findAll();
    }

    public MovilizationTypes createOrUpdate(MovilizationTypes movilizationType) {
        return movilizationTypeRepository.save(movilizationType);
    }

    public void delete(Long id) {
        movilizationTypeRepository.deleteById(id);
    }
}
