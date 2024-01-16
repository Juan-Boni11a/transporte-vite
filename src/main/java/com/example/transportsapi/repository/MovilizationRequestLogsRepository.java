package com.example.transportsapi.repository;

import com.example.transportsapi.models.MovilizationRequestLogs;
import com.example.transportsapi.models.MovilizationRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovilizationRequestLogsRepository extends JpaRepository<MovilizationRequestLogs, Long> {
}
