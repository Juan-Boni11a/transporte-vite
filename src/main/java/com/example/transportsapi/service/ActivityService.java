package com.example.transportsapi.service;

import com.example.transportsapi.models.ActivityModel;
import com.example.transportsapi.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public List<ActivityModel> getAll() {
        return activityRepository.findAll();
    }

    public ActivityModel createOrUpdate(ActivityModel activity) {
        return activityRepository.save(activity);
    }

    public void delete(Long id) {
        activityRepository.deleteById(id);
    }
}
