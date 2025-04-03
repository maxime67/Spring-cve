package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    public User findUserByUsername(String username) {
        return userDAO.findByUsername(username);
    };
}
