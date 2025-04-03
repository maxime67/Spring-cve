package com.example.demo.repositories;

import com.example.demo.entities.Cve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CveDAO extends JpaRepository<Cve, Long> {
    Cve save(Cve cve);
}
