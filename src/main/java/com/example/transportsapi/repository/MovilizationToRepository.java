package com.example.transportsapi.repository;

import com.example.transportsapi.models.MovilizationTo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovilizationToRepository extends JpaRepository<MovilizationTo, Long> {
}
