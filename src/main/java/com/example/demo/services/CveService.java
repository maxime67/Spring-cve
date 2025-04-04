package com.example.demo.services;

import com.example.demo.entities.CVE;
import com.example.demo.repositories.CveDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CveService {
    @Autowired
    CveDAO cveDAO;
public List<CVE> getAllCve() {
    return cveDAO.findAll();
}
}
