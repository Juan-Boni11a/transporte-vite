package com.example.transportsapi.controllers;

import com.example.transportsapi.models.MaintenanceRequestModel;
import com.example.transportsapi.service.MaintenanceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        return maintenanceRequestService.create(maintenanceRequest);
    }

    @PutMapping("/{id}")
    public MaintenanceRequestModel update(@RequestBody MaintenanceRequestModel maintenanceRequest, @PathVariable Long id) throws IOException {
        maintenanceRequest.setId(id);
        return maintenanceRequestService.update(maintenanceRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        maintenanceRequestService.delete(id);
    }

    @PostMapping("/{maintenanceRequestId}/activity/{activityId}")
    public MaintenanceRequestModel addRoleToUser(@PathVariable  Long maintenanceRequestId, @PathVariable Long activityId) throws IOException {
        return maintenanceRequestService.addActivityToMaintenance(maintenanceRequestId, activityId);
    }

    @PostMapping("/{maintenanceRequestId}/buildPdf")
    public ResponseEntity<Map<String, Object>> buildPdf(@PathVariable  Integer maintenanceRequestId) throws IOException {
        maintenanceRequestService.buildPdf(maintenanceRequestId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
