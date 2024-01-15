package com.example.transportsapi.service;

import com.example.transportsapi.models.CityModel;
import com.example.transportsapi.models.MaintenanceRequestModel;
import com.example.transportsapi.models.MovilizationRequestModel;
import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.repository.CityRepository;
import com.example.transportsapi.repository.MaintenanceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.lang.Long;

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

    public List<MaintenanceRequestModel> getMaintenanceRequestsByRequester(Long id) {
        System.out.println("AAAAAAAA");
        MaintenanceRequestModel maintenanceRequest = new MaintenanceRequestModel();
        UserModel user = new UserModel();
        user.setId(id);
        maintenanceRequest.setRequester(user);
        Example<MaintenanceRequestModel> example = Example.of(maintenanceRequest);
        List<MaintenanceRequestModel> results = maintenanceRequestRepository.findAll(example);
        return results;
    }


    public void delete(Long id) {
        maintenanceRequestRepository.deleteById(id);
    }
}
