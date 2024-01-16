package com.example.transportsapi.service;

import com.example.transportsapi.models.VehicleModel;
import com.example.transportsapi.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<VehicleModel> getFreeVehicles() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowAsString = now.format(formatter);


        return vehiclesRepository.findFreeVehicles(nowAsString);
    }


    public List<VehicleModel> getBusyVehicles() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowAsString = now.format(formatter);


        return vehiclesRepository.findBusyVehicles(nowAsString);
    }

}
