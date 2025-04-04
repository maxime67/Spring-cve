package com.example.demo.controllers;

import com.example.demo.entities.CVE;
import com.example.demo.services.CveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/cve/v2")
public class CveController {
    private static final Logger logger = Logger.getLogger(CveController.class.getName());

    private final CveService cveService;

    @Autowired
    public CveController(CveService cveService) {
        this.cveService = cveService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CVE>> getAllCves(
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) Double minImpactScore,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Requête reçue pour les CVEs filtrées");
        List<CVE> cves = cveService.findAllWithFilters(severity, minImpactScore, page, size);
        return ResponseEntity.ok(cves);
    }
    @GetMapping("/all")
    public ResponseEntity<List<CVE>> getAllCves() {

        logger.info("Requête reçue pour les toute les CVEs");
        List<CVE> cves = cveService.findAll();
        return ResponseEntity.ok(cves);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CVE> getCveById(@PathVariable String id) {
        logger.info("Requête reçue pour la CVE avec ID: " + id);
        CVE cve = cveService.findById(id);
        if (cve == null) {
            logger.warning("Aucune CVE trouvée avec l'ID: " + id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cve);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<CVE>> getRecentCves(
            @RequestParam(defaultValue = "5") int limit) {

        logger.info("Requête reçue pour les " + limit + " CVEs les plus récentes");
        List<CVE> cves = cveService.findMostRecent(limit);
        return ResponseEntity.ok(cves);
    }
}