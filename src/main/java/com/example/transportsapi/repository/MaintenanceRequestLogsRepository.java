package com.example.transportsapi.repository;

import com.example.transportsapi.models.MaintenanceRequestLogs;
import com.example.transportsapi.models.MovilizationRequestLogs;
import com.example.transportsapi.models.MovilizationRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRequestLogsRepository extends JpaRepository<MaintenanceRequestLogs, Long> {
}

