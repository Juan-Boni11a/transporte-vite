package com.example.transportsapi.repository;

import com.example.transportsapi.models.MovilizationTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovilizationTypeRepository extends JpaRepository<MovilizationTypes, Long> {
}
