package com.example.transportsapi.controllers;

import com.example.transportsapi.models.TransportationRequestModel;
import com.example.transportsapi.models.VehicleModel;
import com.example.transportsapi.service.TransportationRequestService;
import com.example.transportsapi.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/transportationRequests")

public class TransportationRequestController {


    @Autowired
    private TransportationRequestService transportationRequestService;

    @GetMapping
    public List<TransportationRequestModel> getAll(){
        return transportationRequestService.getAll();
    }

    @PostMapping
    public TransportationRequestModel create(@RequestBody TransportationRequestModel transportationRequest){
        return transportationRequestService.createOrUpdate(transportationRequest);
    }

    @PutMapping("/{id}")
    public TransportationRequestModel update(@RequestBody TransportationRequestModel transportationRequest, @PathVariable Long id){
        transportationRequest.setId(id);
        return transportationRequestService.createOrUpdate(transportationRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        transportationRequestService.delete(id);
    }

}
