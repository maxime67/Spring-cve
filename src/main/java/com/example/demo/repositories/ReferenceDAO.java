package com.example.demo.repositories;

import com.example.demo.entities.Reference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenceDAO extends JpaRepository<Reference, Long> {
    Reference findById(long id);
    Reference save(Reference reference);
}
