package com.example.transportsapi.controllers;


import com.example.transportsapi.models.ActivityModel;
import com.example.transportsapi.models.CityModel;
import com.example.transportsapi.service.ActivityService;
import com.example.transportsapi.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public List<ActivityModel> getAll(){
        return activityService.getAll();
    }

    @PostMapping
    public ActivityModel create(@RequestBody ActivityModel activity){
        return activityService.createOrUpdate(activity);
    }

    @PutMapping("/{id}")
    public ActivityModel update(@RequestBody ActivityModel activity, @PathVariable Long id){
        activity.setId(id);
        return activityService.createOrUpdate(activity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        activityService.delete(id);
    }

}
