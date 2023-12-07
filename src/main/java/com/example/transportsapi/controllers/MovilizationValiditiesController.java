package com.example.transportsapi.controllers;

import com.example.transportsapi.models.MovilizationValidities;
import com.example.transportsapi.service.MovilizationValiditiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/movilizationValidities")
public class MovilizationValiditiesController {

    @Autowired
    private MovilizationValiditiesService movilizationValiditiesService;

    @GetMapping
    public List<MovilizationValidities> getAll(){
        return movilizationValiditiesService.getAll();
    }

    @PostMapping
    public MovilizationValidities create(@RequestBody MovilizationValidities movilizationValidity){
        return movilizationValiditiesService.createOrUpdate(movilizationValidity);
    }

    @PutMapping("/{id}")
    public MovilizationValidities update(@RequestBody MovilizationValidities movilizationValidity, @PathVariable Long id){
        movilizationValidity.setId(id);
        return movilizationValiditiesService.createOrUpdate(movilizationValidity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        movilizationValiditiesService.delete(id);
    }
}

