package com.example.demo.repositories;

import com.example.demo.entities.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricsDAO extends JpaRepository<Metrics, Long> {
    Metrics findById(long id);
    Metrics save(Metrics metrics);
}
