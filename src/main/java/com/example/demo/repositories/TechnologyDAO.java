package com.example.demo.repositories;

import com.example.demo.entities.Technology;
import com.example.demo.entities.User;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TechnologyDAO extends Repository<Technology, Long> {
    Technology save(Technology technology);
    List<Technology> findAll();

}
