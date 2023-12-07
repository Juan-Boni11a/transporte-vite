package com.example.transportsapi.service;

import com.example.transportsapi.models.TransportationRequestModel;
import com.example.transportsapi.models.VehicleModel;
import com.example.transportsapi.repository.TransportationRequestRepository;
import com.example.transportsapi.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportationRequestService {

    @Autowired
    private TransportationRequestRepository transportationRequestRepository;

    public List<TransportationRequestModel> getAll() {
        return transportationRequestRepository.findAll();
    }

    public TransportationRequestModel createOrUpdate(TransportationRequestModel transportationRequest) {
        return transportationRequestRepository.save(transportationRequest);
    }

    public void delete(Long id) {
        transportationRequestRepository.deleteById(id);
    }

}
