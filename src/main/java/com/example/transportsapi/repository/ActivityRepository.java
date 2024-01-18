package com.example.transportsapi.repository;

import com.example.transportsapi.models.ActivityModel;
import com.example.transportsapi.models.CityModel;
import com.example.transportsapi.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityModel, Long> {

    @Query("SELECT act, man_act FROM ActivityModel act " +
            "JOIN MaintenanceActivitiesModel man_act ON man_act.activity.id = act.id " +
            "WHERE man_act.maintenanceRequest.id = :manId")
    List<ActivityModel> findByMaintenanceRequest(@Param("manId") Long manId);
}
