package com.example.transportsapi.controllers;


import com.example.transportsapi.models.MovilizationTo;
import com.example.transportsapi.service.MovilizationToService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/movilizationTo")
public class MovilizationToController {

    @Autowired
    private MovilizationToService movilizationToService;

    @GetMapping
    public List<MovilizationTo> getAll(){
        return movilizationToService.getAll();
    }

    @PostMapping
    public MovilizationTo create(@RequestBody MovilizationTo movilizationTo){
        return movilizationToService.createOrUpdate(movilizationTo);
    }

    @PutMapping("/{id}")
    public MovilizationTo update(@RequestBody MovilizationTo movilizationTo, @PathVariable Long id){
        movilizationTo.setId(id);
        return movilizationToService.createOrUpdate(movilizationTo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        movilizationToService.delete(id);
    }
}

