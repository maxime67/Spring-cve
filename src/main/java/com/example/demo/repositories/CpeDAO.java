package com.example.demo.repositories;

import com.example.demo.entities.Cpe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpeDAO extends JpaRepository<Cpe, Long> {
    Cpe save(Cpe cpe);
}
