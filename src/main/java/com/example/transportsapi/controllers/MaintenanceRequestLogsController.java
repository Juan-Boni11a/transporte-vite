package com.example.transportsapi.controllers;

import com.example.transportsapi.models.MaintenanceRequestLogs;
import com.example.transportsapi.service.MaintenanceRequestLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MaintenanceRequestLogsController {

    @Autowired
    private MaintenanceRequestLogsService maintenanceRequestLogsService;
    @RequestMapping(value = "api/maintenanceRequestLogsByRequest/{id}", method = RequestMethod.GET)
    public List<MaintenanceRequestLogs> getByRequest(@PathVariable Integer id) {
        return maintenanceRequestLogsService.getMaintenanceLogsByRequestId(id);
    }

}