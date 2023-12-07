package com.example.transportsapi.service;

import com.example.transportsapi.models.CityModel;
import com.example.transportsapi.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {


    @Autowired
    private CityRepository cityRepository;

    public List<CityModel> getAll() {
        return cityRepository.findAll();
    }

    public CityModel createOrUpdate(CityModel city) {
        return cityRepository.save(city);
    }

    public void delete(Long id) {
        cityRepository.deleteById(id);
    }
}
