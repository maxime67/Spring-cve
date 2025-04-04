package com.example.demo.services;

import com.example.demo.entities.CVE;
import com.example.demo.entities.Cpe;
import com.example.demo.repositories.CpeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CpeService {
    @Autowired
    CpeDAO cpeDAO;
    public List<Cpe> findAll() {
        cpeDAO.findAll().forEach(System.out::println);
        return cpeDAO.findAll();
    }
}
