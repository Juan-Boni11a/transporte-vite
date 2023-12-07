package com.example.transportsapi.repository;

import com.example.transportsapi.models.TransportationRequestModel;
import com.example.transportsapi.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationRequestRepository extends JpaRepository<TransportationRequestModel, Long> {
}
