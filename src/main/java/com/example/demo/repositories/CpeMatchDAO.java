package com.example.demo.repositories;

import com.example.demo.entities.CpeMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpeMatchDAO extends JpaRepository<CpeMatch, Long> {
    CpeMatch findById(long id);
    CpeMatch save(CpeMatch cpeMatch);
}
