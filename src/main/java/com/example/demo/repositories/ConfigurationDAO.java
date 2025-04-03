package com.example.demo.repositories;

import com.example.demo.entities.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigurationDAO extends JpaRepository<Configuration, Long> {
    Configuration save(Configuration configuration);
    Configuration findById(long id);
}
