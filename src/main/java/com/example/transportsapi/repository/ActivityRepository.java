package com.example.transportsapi.repository;

import com.example.transportsapi.models.ActivityModel;
import com.example.transportsapi.models.CityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityModel, Long> {
}
