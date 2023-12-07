package com.example.transportsapi.repository;

import com.example.transportsapi.models.CityModel;
import com.example.transportsapi.models.MaintenanceRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequestModel, Long> {
}
