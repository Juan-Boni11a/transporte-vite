package com.example.transportsapi.service;

import com.example.transportsapi.models.MechanicalExitLogModel;
import com.example.transportsapi.repository.MechanicalExitLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicalExitLogService {

    @Autowired
    private MechanicalExitLogRepository mechanicalExitLogRepository;

    public List<MechanicalExitLogModel> getAll() {
        return mechanicalExitLogRepository.findAll();
    }

    public MechanicalExitLogModel createOrUpdate(MechanicalExitLogModel mechanicalExitLog) {
        return mechanicalExitLogRepository.save(mechanicalExitLog);
    }

    public void delete(Long id) {
        mechanicalExitLogRepository.deleteById(id);
    }
}
