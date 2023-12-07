package com.example.transportsapi.repository;

import com.example.transportsapi.models.MechanicalExitLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MechanicalExitLogRepository extends JpaRepository<MechanicalExitLogModel, Long> {
}
