package com.example.transportsapi.repository;

import com.example.transportsapi.models.SupplyRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRequestRepository extends JpaRepository<SupplyRequestModel, Long> {
}
