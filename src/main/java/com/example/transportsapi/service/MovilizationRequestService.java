package com.example.transportsapi.service;


import com.example.transportsapi.models.CityModel;
import com.example.transportsapi.models.MovilizationRequestModel;
import com.example.transportsapi.repository.MovilizationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovilizationRequestService {

    @Autowired
    private MovilizationRequestRepository movilizationRequestRepository;

    public List<MovilizationRequestModel> getAllMovilizationRequests() {
        return movilizationRequestRepository.findAll();
    }

    public MovilizationRequestModel createMovilizationRequest(MovilizationRequestModel movilizationRequest) {
        return movilizationRequestRepository.save(movilizationRequest);
    }

    public MovilizationRequestModel updateMovilizationRequest(MovilizationRequestModel movilizationRequest) {
        return movilizationRequestRepository.save(movilizationRequest);
    }

    public void deleteMovilizationRequest(Long id) {
        movilizationRequestRepository.deleteById(id);
    }
}
