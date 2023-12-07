package com.example.transportsapi.service;

import com.example.transportsapi.models.CityModel;
import com.example.transportsapi.models.MaintenanceRequestModel;
import com.example.transportsapi.repository.CityRepository;
import com.example.transportsapi.repository.MaintenanceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceRequestService {
    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;

    public List<MaintenanceRequestModel> getAll() {
        return maintenanceRequestRepository.findAll();
    }

    public MaintenanceRequestModel createOrUpdate(MaintenanceRequestModel maintenanceRequest) {
        return maintenanceRequestRepository.save(maintenanceRequest);
    }

    public void delete(Long id) {
        maintenanceRequestRepository.deleteById(id);
    }
}
