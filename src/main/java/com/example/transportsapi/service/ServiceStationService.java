package com.example.transportsapi.service;

import com.example.transportsapi.models.ServiceStationModel;
import com.example.transportsapi.repository.ServiceStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceStationService {

    @Autowired
    private ServiceStationRepository serviceStationRepository;

    public List<ServiceStationModel> getAll() {
        return serviceStationRepository.findAll();
    }

    public ServiceStationModel createOrUpdate(ServiceStationModel serviceStation) {
        return serviceStationRepository.save(serviceStation);
    }

    public void delete(Long id) {
        serviceStationRepository.deleteById(id);
    }
}
