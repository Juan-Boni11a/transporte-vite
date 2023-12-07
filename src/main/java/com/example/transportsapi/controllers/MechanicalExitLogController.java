package com.example.transportsapi.controllers;


import com.example.transportsapi.models.MaintenanceRequestModel;
import com.example.transportsapi.models.MechanicalExitLogModel;
import com.example.transportsapi.service.MaintenanceRequestService;
import com.example.transportsapi.service.MechanicalExitLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/mechanicalExitLogs")
public class MechanicalExitLogController {

    @Autowired
    private MechanicalExitLogService mechanicalExitLogService;

    @GetMapping
    public List<MechanicalExitLogModel> getAll(){
        return mechanicalExitLogService.getAll();
    }

    @PostMapping
    public MechanicalExitLogModel create(@RequestBody MechanicalExitLogModel mechanicalExitLog){
        return mechanicalExitLogService.createOrUpdate(mechanicalExitLog);
    }

    @PutMapping("/{id}")
    public MechanicalExitLogModel update(@RequestBody MechanicalExitLogModel mechanicalExitLog, @PathVariable Long id){
        mechanicalExitLog.setId(id);
        return mechanicalExitLogService.createOrUpdate(mechanicalExitLog);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        mechanicalExitLogService.delete(id);
    }
}
