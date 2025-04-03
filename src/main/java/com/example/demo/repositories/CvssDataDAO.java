package com.example.demo.repositories;

import com.example.demo.entities.CvssData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvssDataDAO extends JpaRepository<CvssData, Long> {
    CvssData findById(long id);
    CvssData save(CvssData cvssData);
}
