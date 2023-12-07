package com.example.transportsapi.repository;

import com.example.transportsapi.models.ServiceStationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceStationRepository extends JpaRepository<ServiceStationModel, Long> {
}
