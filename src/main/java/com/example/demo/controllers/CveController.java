package com.example.demo.controllers;

import com.example.demo.entities.CVE;
import com.example.demo.services.CveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cve")
public class CveController {

    private final CveService cveService;

    public CveController(CveService cveService) {
        this.cveService = cveService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CVE>> followElement() {
        return ResponseEntity.ok(cveService.getAllCve());
    }
}
