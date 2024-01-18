package com.example.transportsapi.controllers;

import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.models.VehicleModel;
import com.example.transportsapi.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping("/free")
    public List<VehicleModel> getFreeVehicles(){
        return vehicleService.getFreeVehicles();
    }


    @GetMapping("/busy")
    public ResponseEntity<Map<String, List<VehicleModel>>> getBusyVehicles(){
        List<VehicleModel> busyOfMr = vehicleService.getBusyVehicles();
        List<VehicleModel> busyOfMa = vehicleService.getVehiclesInMaintenance();

        // Combina las listas y elimina duplicados usando un Set
        Set<VehicleModel> combinedSet = new HashSet<>(busyOfMr);
        combinedSet.addAll(busyOfMa);


        List<Long> vehicleIds = new ArrayList<>();
        for(VehicleModel vehicle: combinedSet){
            vehicleIds.add(vehicle.getId());
        }

        List<VehicleModel> allVehicles = vehicleService.getAll();

        List<VehicleModel> freeVehicles = new ArrayList<>();

        for (VehicleModel vehicle : allVehicles) {
            if (!vehicleIds.contains(vehicle.getId())) {
                freeVehicles.add(vehicle);
            }
        }


        Map<String, List<VehicleModel>> response = new HashMap<>();

        response.put("busyVehicles", new ArrayList<>(combinedSet));
        response.put("freeVehicles", freeVehicles);

        return new ResponseEntity<>(response, HttpStatus.OK);


    }


}
