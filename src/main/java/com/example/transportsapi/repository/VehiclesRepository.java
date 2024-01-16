package com.example.transportsapi.repository;

import com.example.transportsapi.models.UserModel;
import com.example.transportsapi.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VehiclesRepository extends JpaRepository<VehicleModel, Long> {

    @Query("SELECT v, mr FROM VehicleModel v " +
            "JOIN MovilizationRequestModel mr ON v.id = mr.vehicle.id " +
            "WHERE :now >= CONCAT(mr.emitDate, ' ', mr.emitHour) " +
            "AND :now <= CONCAT(mr.expiryDate, ' ', mr.expiryHour) "
    )
    List<VehicleModel> findBusyVehicles(@Param("now") String now);


    @Query("SELECT v " +
            "FROM VehicleModel v " +
            "LEFT JOIN MovilizationRequestModel mr ON v.id = mr.vehicle.id " +
            "AND :now >= CONCAT(mr.emitDate, ' ', mr.emitHour) " +
            "AND :now <= CONCAT(mr.expiryDate, ' ', mr.expiryHour) " +
            "WHERE mr.id IS NULL"
    )
    List<VehicleModel> findFreeVehicles(@Param("now") String now);

}

