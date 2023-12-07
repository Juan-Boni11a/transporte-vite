package com.example.transportsapi.service;

import com.example.transportsapi.models.MovilizationTo;
import com.example.transportsapi.repository.MovilizationToRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovilizationToService {

    @Autowired
    private MovilizationToRepository movilizationToRepository;


    public List<MovilizationTo> getAll() {
        return movilizationToRepository.findAll();
    }

    public MovilizationTo createOrUpdate(MovilizationTo movilizationTo) {
        return movilizationToRepository.save(movilizationTo);
    }

    public void delete(Long id) {
        movilizationToRepository.deleteById(id);
    }

}
