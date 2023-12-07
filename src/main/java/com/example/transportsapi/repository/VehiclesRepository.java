package com.example.transportsapi.repository;

import com.example.transportsapi.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiclesRepository extends JpaRepository<VehicleModel, Long> {
}

