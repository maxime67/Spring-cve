package com.example.demo.repositories;

import com.example.demo.entities.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.Repository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserDAO extends Repository<User, Long> {
    User save(User User);

    Optional<User> findById(long id);

    User findByFirstName(String firstName);
    User findByUsername(String userName);
}
