package com.example.demo.repositories;

import com.example.demo.entities.Description;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionDAO extends JpaRepository<Description, Long> {
    Description findById(long id);
    Description save(Description description);
}
