package com.example.transportsapi.controllers;

import com.example.transportsapi.models.MaintenanceRequestModel;
import com.example.transportsapi.service.MaintenanceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/maintenanceRequests")
public class MaintenanceRequestController {

    @Autowired
    private MaintenanceRequestService maintenanceRequestService;

    @GetMapping
    public List<MaintenanceRequestModel> getAll(){
        return maintenanceRequestService.getAll();
    }

    @PostMapping
    public MaintenanceRequestModel create(@RequestBody MaintenanceRequestModel maintenanceRequest){
        return maintenanceRequestService.createOrUpdate(maintenanceRequest);
    }

    @PutMapping("/{id}")
    public MaintenanceRequestModel update(@RequestBody MaintenanceRequestModel maintenanceRequest, @PathVariable Long id){
        maintenanceRequest.setId(id);
        return maintenanceRequestService.createOrUpdate(maintenanceRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        maintenanceRequestService.delete(id);
    }
}
