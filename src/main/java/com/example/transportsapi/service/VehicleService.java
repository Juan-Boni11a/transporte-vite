package com.example.transportsapi.service;

import com.example.transportsapi.models.VehicleModel;
import com.example.transportsapi.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    private VehiclesRepository vehiclesRepository;

    public List<VehicleModel> getAll() {
        return vehiclesRepository.findAll();
    }

    public VehicleModel createOrUpdate(VehicleModel vehicle) {
        return vehiclesRepository.save(vehicle);
    }

    public void delete(Long id) {
        vehiclesRepository.deleteById(id);
    }
}
