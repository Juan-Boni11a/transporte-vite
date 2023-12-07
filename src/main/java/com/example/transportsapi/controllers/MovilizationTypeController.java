package com.example.transportsapi.controllers;

import com.example.transportsapi.models.MovilizationTypes;
import com.example.transportsapi.service.MovilizationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/movilizationTypes")
public class MovilizationTypeController {

    @Autowired
    private MovilizationTypeService movilizationTypeService;

    @GetMapping
    public List<MovilizationTypes> getAll(){
        return movilizationTypeService.getAll();
    }

    @PostMapping
    public MovilizationTypes create(@RequestBody MovilizationTypes movilizationType){
        return movilizationTypeService.createOrUpdate(movilizationType);
    }

    @PutMapping("/{id}")
    public MovilizationTypes update(@RequestBody MovilizationTypes movilizationType, @PathVariable Long id){
        movilizationType.setId(id);
        return movilizationTypeService.createOrUpdate(movilizationType);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        movilizationTypeService.delete(id);
    }
}
