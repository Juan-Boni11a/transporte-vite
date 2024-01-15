package com.example.transportsapi.service;

import com.example.transportsapi.models.MovilizationRequestModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

public class GenerateCodeService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void generateMrCode(MovilizationRequestModel movilizationRequest) {
        if (movilizationRequest.getCode() == null) {
            movilizationRequest.setCode("M-" + String.format("%03d", movilizationRequest.getId()));
            entityManager.merge(movilizationRequest);
        }
    }
}
