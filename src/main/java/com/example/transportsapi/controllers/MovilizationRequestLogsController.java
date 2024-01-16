package com.example.transportsapi.controllers;

import com.example.transportsapi.models.MovilizationRequestLogs;
import com.example.transportsapi.models.MovilizationRequestModel;
import com.example.transportsapi.service.MovilizationRequestLogsService;
import com.example.transportsapi.service.MovilizationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MovilizationRequestLogsController {

    @Autowired
    private MovilizationRequestLogsService movilizationRequestLogsService;

    @RequestMapping(value = "api/movilizationRequestLogsByRequest/{id}", method = RequestMethod.GET)
    public List<MovilizationRequestLogs> getByRequest(@PathVariable Integer id) {
        return movilizationRequestLogsService.getMovilizationLogsByRequestId(id);
    }

}