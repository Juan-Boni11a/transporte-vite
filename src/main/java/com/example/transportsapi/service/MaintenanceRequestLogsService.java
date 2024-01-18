package com.example.transportsapi.service;

import com.example.transportsapi.models.*;
import com.example.transportsapi.repository.MaintenanceRequestLogsRepository;
import com.example.transportsapi.repository.MovilizationRequestLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MaintenanceRequestLogsService {



    @Autowired
    private MaintenanceRequestLogsRepository maintenanceRequestLogsRepository;


    public List<MaintenanceRequestLogs> getMaintenanceLogsByRequestId(Integer requestId) {

        MaintenanceRequestModel maintenanceRequest = new MaintenanceRequestModel();
        maintenanceRequest.setId(Long.valueOf(requestId));

        MaintenanceRequestLogs maintenanceRequestLog = new MaintenanceRequestLogs();
        maintenanceRequestLog.setMaintenanceRequest(maintenanceRequest);

        Example<MaintenanceRequestLogs> example = Example.of(maintenanceRequestLog);
        List<MaintenanceRequestLogs> results = maintenanceRequestLogsRepository.findAll(example);
        return results;
    }
}
