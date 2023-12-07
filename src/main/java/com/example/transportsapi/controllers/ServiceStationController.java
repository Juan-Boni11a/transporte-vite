package com.example.transportsapi.controllers;

import com.example.transportsapi.models.ServiceStationModel;
import com.example.transportsapi.service.ServiceStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/serviceStations")
public class ServiceStationController {


    @Autowired
    private ServiceStationService serviceStationService;

    @GetMapping
    public List<ServiceStationModel> getAll(){
        return serviceStationService.getAll();
    }

    @PostMapping
    public ServiceStationModel create(@RequestBody ServiceStationModel serviceStation){
        return serviceStationService.createOrUpdate(serviceStation);
    }

    @PutMapping("/{id}")
    public ServiceStationModel update(@RequestBody ServiceStationModel serviceStation, @PathVariable Long id){
        serviceStation.setId(id);
        return serviceStationService.createOrUpdate(serviceStation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        serviceStationService.delete(id);
    }
}
