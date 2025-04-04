package com.example.demo.controllers;

import com.example.demo.DTO.CveDTO;
import com.example.demo.services.CveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/cve")
public class CveController {
    private static final Logger logger = Logger.getLogger(CveController.class.getName());

    private final CveService cveService;

    public CveController(CveService cveService) {
        this.cveService = cveService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CveDTO>> getAllCves() {
        logger.info("Received request to fetch all CVEs");
        List<CveDTO> cves = cveService.findAll();
        logger.info("Returning " + cves.size() + " CVEs");
        return ResponseEntity.ok(cves);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CveDTO> getCveById(@PathVariable String id) {
        logger.info("Received request to fetch CVE with ID: " + id);
        CveDTO cve = cveService.findById(id);
        if (cve == null) {
            logger.warning("No CVE found with ID: " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cve);
    }
}