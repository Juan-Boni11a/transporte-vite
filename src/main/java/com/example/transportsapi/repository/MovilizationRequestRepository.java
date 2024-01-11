package com.example.transportsapi.repository;

import com.example.transportsapi.models.MovilizationRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovilizationRequestRepository extends JpaRepository<MovilizationRequestModel, Long> {
}
