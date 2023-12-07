package com.example.transportsapi.service;

import com.example.transportsapi.models.SupplyRequestModel;
import com.example.transportsapi.models.TransportationRequestModel;
import com.example.transportsapi.repository.SupplyRequestRepository;
import com.example.transportsapi.repository.TransportationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyRequestService {

    @Autowired
    private SupplyRequestRepository supplyRequestRepository;

    public List<SupplyRequestModel> getAll() {
        return supplyRequestRepository.findAll();
    }

    public SupplyRequestModel createOrUpdate(SupplyRequestModel supplyRequest) {
        return supplyRequestRepository.save(supplyRequest);
    }

    public void delete(Long id) {
        supplyRequestRepository.deleteById(id);
    }

}
