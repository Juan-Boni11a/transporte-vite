package com.example.transportsapi.controllers;

import com.example.transportsapi.models.SupplyRequestModel;
import com.example.transportsapi.models.TransportationRequestModel;
import com.example.transportsapi.service.SupplyRequestService;
import com.example.transportsapi.service.TransportationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/supplyRequests")
public class SupplyRequestController {

    @Autowired
    private SupplyRequestService supplyRequestService;

    @GetMapping
    public List<SupplyRequestModel> getAll(){
        return supplyRequestService.getAll();
    }

    @PostMapping
    public SupplyRequestModel create(@RequestBody SupplyRequestModel supplyRequest){
        return supplyRequestService.createOrUpdate(supplyRequest);
    }

    @PutMapping("/{id}")
    public SupplyRequestModel update(@RequestBody SupplyRequestModel supplyRequest, @PathVariable Long id){
        supplyRequest.setId(id);
        return supplyRequestService.createOrUpdate(supplyRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        supplyRequestService.delete(id);
    }

}
