package com.example.transportsapi.service;

import com.example.transportsapi.models.MovilizationRequestLogs;
import com.example.transportsapi.models.MovilizationRequestModel;
import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.repository.MovilizationRequestLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MovilizationRequestLogsService {

    @Autowired
    private MovilizationRequestLogsRepository movilizationRequestLogsRepository;


    public List<MovilizationRequestLogs> getMovilizationLogsByRequestId(Integer requestId) {

        MovilizationRequestModel movilizationRequest = new MovilizationRequestModel();
        movilizationRequest.setId(Long.valueOf(requestId));

        MovilizationRequestLogs movilizationRequestLog = new MovilizationRequestLogs();
        movilizationRequestLog.setMovilizationRequest(movilizationRequest);

        Example<MovilizationRequestLogs> example = Example.of(movilizationRequestLog);

        List<MovilizationRequestLogs> results = movilizationRequestLogsRepository.findAll(example);

        return results;
    }
}
