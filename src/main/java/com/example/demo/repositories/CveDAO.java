package com.example.demo.repositories;

import com.example.demo.entities.CVE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CveDAO extends JpaRepository<CVE, Long> {
    CVE save(CVE cve);
    Optional<CVE> findById(String id);
}
