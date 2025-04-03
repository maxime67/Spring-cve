package com.example.demo.repositories;

import com.example.demo.entities.CvssData;
import com.example.demo.entities.CvssMetricV2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvssMetricV2DAO extends JpaRepository<CvssMetricV2, Long> {
    CvssMetricV2 findById(long id);
    CvssData save(CvssData cvssData);
}
