package com.example.transportsapi.controllers;

import com.example.transportsapi.models.CityModel;
import com.example.transportsapi.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public List<CityModel> getAll(){
        return cityService.getAll();
    }

    @PostMapping
    public CityModel create(@RequestBody CityModel city){
        return cityService.createOrUpdate(city);
    }

    @PutMapping("/{id}")
    public CityModel update(@RequestBody CityModel city, @PathVariable Long id){
        city.setId(id);
        return cityService.createOrUpdate(city);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        cityService.delete(id);
    }
}
