package com.example.transportsapi.controllers;

import com.example.transportsapi.models.MaintenanceRequestModel;
import com.example.transportsapi.models.MovilizationRequestModel;
import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.service.MaintenanceRequestService;
import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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


    @GetMapping(value = "/{id}")
    public List<MaintenanceRequestModel> getByRequester(@PathVariable Long id) {
        return maintenanceRequestService.getMaintenanceRequestsByRequester(id);
    }

    @PostMapping
    public MaintenanceRequestModel create(@RequestBody MaintenanceRequestModel maintenanceRequest) throws IOException {
        return maintenanceRequestService.createOrUpdate(maintenanceRequest);
    }

    @PutMapping("/{id}")
    public MaintenanceRequestModel update(@RequestBody MaintenanceRequestModel maintenanceRequest, @PathVariable Long id) throws IOException {
        maintenanceRequest.setId(id);
        return maintenanceRequestService.createOrUpdate(maintenanceRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        maintenanceRequestService.delete(id);
    }

    @PostMapping("/{maintenanceRequestId}/activity/{activityId}")
    public MaintenanceRequestModel addRoleToUser(@PathVariable  Long maintenanceRequestId, @PathVariable Long activityId) {
        return maintenanceRequestService.addActivityToMaintenance(maintenanceRequestId, activityId);
    }
}
