package com.example.demo.repositories;

import com.example.demo.entities.Weakness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaknessDAO extends JpaRepository<Weakness, Long> {
    Weakness findWeaknessById(long id);
    Weakness save(Weakness w);
}
