package com.example.transportsapi.controllers;

import com.example.transportsapi.models.VehicleModel;
import com.example.transportsapi.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public List<VehicleModel> getAll(){
        return vehicleService.getAll();
    }

    @PostMapping
    public VehicleModel create(@RequestBody VehicleModel vehicle){
        return vehicleService.createOrUpdate(vehicle);
    }

    @PutMapping("/{id}")
    public VehicleModel update(@RequestBody VehicleModel vehicle, @PathVariable Long id){
        vehicle.setId(id);
        return vehicleService.createOrUpdate(vehicle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        vehicleService.delete(id);
    }
}
