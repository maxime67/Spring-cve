package com.example.demo.controllers;

import com.example.demo.entities.Cpe;
import com.example.demo.services.CpeService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cpe")
public class CpeController {
    private final CpeService cpeService;

    public CpeController(CpeService cpeService) {
        this.cpeService = cpeService;
    }

    @GetMapping("/list")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Cpe>> followElement() {
        return ResponseEntity.ok(cpeService.findAll());
    }
}
