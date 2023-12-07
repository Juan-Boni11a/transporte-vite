package com.example.transportsapi.repository;

import com.example.transportsapi.models.MovilizationValidities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovilizationValiditiesRepository extends JpaRepository<MovilizationValidities, Long> {
}
