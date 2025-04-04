package com.example.demo.services;

import com.example.demo.entities.Technology;
import com.example.demo.repositories.TechnologyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {
      @Autowired
    TechnologyDAO technologyDAO;

      public List<Technology> findAll(){
          return technologyDAO.findAll();
      }
}
