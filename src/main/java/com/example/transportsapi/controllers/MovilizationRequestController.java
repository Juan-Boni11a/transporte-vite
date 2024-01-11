package com.example.transportsapi.controllers;

import com.example.transportsapi.models.MovilizationRequestModel;
import com.example.transportsapi.service.MovilizationRequestService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MovilizationRequestController {

    @Autowired
    private MovilizationRequestService movilizationRequestService;


    @RequestMapping(value = "api/movilizationRequests", method = RequestMethod.GET)
    public List<MovilizationRequestModel> getAll() {
        return movilizationRequestService.getAllMovilizationRequests();
    }

    @RequestMapping(value = "api/movilizationRequestsByRequester/{id}", method = RequestMethod.GET)
    public List<MovilizationRequestModel> getByRequester(@PathVariable Long id) {
        return movilizationRequestService.getMovilizationRequestsByRequester(id);
    }

    @RequestMapping(value = "api/movilizationRequests", method = RequestMethod.POST)
    public MovilizationRequestModel create(@RequestBody MovilizationRequestModel movilizationRequest) throws DocumentException, IOException {
        return movilizationRequestService.createMovilizationRequest(movilizationRequest);
    }

    @RequestMapping(value = "api/movilizationRequests/{id}", method = RequestMethod.PUT)
    public MovilizationRequestModel update(@RequestBody MovilizationRequestModel movilizationRequest, @PathVariable Long id) {
        movilizationRequest.setId(id);
        return movilizationRequestService.updateMovilizationRequest(movilizationRequest);
    }

    @RequestMapping(value = "api/movilizationRequests/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        movilizationRequestService.deleteMovilizationRequest(id);
    }

}