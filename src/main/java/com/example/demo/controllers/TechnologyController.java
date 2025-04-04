package com.example.demo.controllers;

import com.example.demo.entities.CVE;
import com.example.demo.entities.Technology;
import com.example.demo.services.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/technology")
public class TechnologyController {
    private static final Logger logger = Logger.getLogger(CveController.class.getName());

    @Autowired
    TechnologyService technologyService;
    @GetMapping("/all")
    public ResponseEntity<List<Technology>> getAllCves() {

        logger.info("Requête reçue pour les toute les technologies");
        List<Technology> cves = technologyService.findAll();
        return ResponseEntity.ok(cves);
    }
}
