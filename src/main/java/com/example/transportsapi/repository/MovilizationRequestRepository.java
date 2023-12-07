package com.example.transportsapi.repository;

import com.example.transportsapi.models.MovilizationRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovilizationRequestRepository extends JpaRepository<MovilizationRequestModel, Long> {
}
