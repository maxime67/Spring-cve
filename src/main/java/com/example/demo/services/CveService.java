package com.example.demo.services;

import com.example.demo.DTO.CveDTO;
import com.example.demo.DTO.CveMapper;
import com.example.demo.entities.CVE;
import com.example.demo.repositories.CveDAO;
import jakarta.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CveService {
    private static final Logger logger = Logger.getLogger(CveService.class.getName());

    private final CveDAO cveDAO;
    private final CveMapper cveMapper;

    @Autowired
    public CveService(CveDAO cveDAO, CveMapper cveMapper) {
        this.cveDAO = cveDAO;
        this.cveMapper = cveMapper;
    }

    /**
     * Récupère une CVE par son ID, avec mise en cache
     */
    @Transactional(readOnly = true)
    public CveDTO findById(String id) {
        logger.info("Recherche de la CVE avec l'ID: " + id);
        return cveDAO.findById(id)
                .map(cveMapper::toDto)
                .orElse(null);
    }

    /**
     * Récupère toutes les CVEs avec filtrage et pagination
     */
    @Transactional(readOnly = true)
    public List<CveDTO> findAllWithFilters(String severity, Double minImpactScore, int page, int size) {
        logger.info("Recherche de CVEs avec filtres - severity: " + severity +
                ", minImpactScore: " + minImpactScore +
                ", page: " + page +
                ", size: " + size);

        List<CVE> cves = cveDAO.findAll();

        // Filtrage basé sur les critères
        List<CVE> filteredCves = cves.stream()
                .filter(cve -> {
                    // Filtre par sévérité si spécifié
                    if (severity != null && !severity.isEmpty() && cve.getMetrics() != null &&
                            cve.getMetrics().getCvssMetricV2() != null && !cve.getMetrics().getCvssMetricV2().isEmpty()) {
                        return cve.getMetrics().getCvssMetricV2().stream()
                                .anyMatch(metric -> severity.equalsIgnoreCase(metric.getBaseSeverity()));
                    }
                    return true;
                })
                .filter(cve -> {
                    // Filtre par score d'impact minimum si spécifié
                    if (minImpactScore != null && cve.getMetrics() != null &&
                            cve.getMetrics().getCvssMetricV2() != null && !cve.getMetrics().getCvssMetricV2().isEmpty()) {
                        return cve.getMetrics().getCvssMetricV2().stream()
                                .anyMatch(metric -> metric.getImpactScore() >= minImpactScore);
                    }
                    return true;
                })
                .collect(Collectors.toList());

        // Pagination simple
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, filteredCves.size());

        if (fromIndex > filteredCves.size()) {
            return List.of(); // Page vide
        }

        List<CVE> pagedCves = filteredCves.subList(fromIndex, toIndex);

        logger.info("Retour de " + pagedCves.size() + " CVEs filtrées");
        return cveMapper.toDtoList(pagedCves);
    }

    /**
     * Version simplifiée pour récupérer toutes les CVEs
     */
    @Transactional(readOnly = true)
    public List<CveDTO> findAll() {
        List<CVE> cves = cveDAO.findAll();
        logger.info("Récupération de " + cves.size() + " CVEs");
        return cveMapper.toDtoList(cves);
    }

    /**
     * Récupère les CVEs les plus récentes
     */
    @Transactional(readOnly = true)
    public List<CveDTO> findMostRecent(int limit) {
        List<CVE> cves = cveDAO.findAll();

        // Trie par date de publication (descendant)
        List<CVE> recentCves = cves.stream()
                .sorted((c1, c2) -> c2.getPublished().compareTo(c1.getPublished()))
                .limit(limit)
                .collect(Collectors.toList());

        logger.info("Récupération des " + recentCves.size() + " CVEs les plus récentes");
        return cveMapper.toDtoList(recentCves);
    }
}