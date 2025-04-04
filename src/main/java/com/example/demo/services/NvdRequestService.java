package com.example.demo.services;

import com.example.demo.DTO.nvd.NvdCveDTO;
import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class NvdRequestService {
    private static final Logger logger = Logger.getLogger(NvdRequestService.class.getName());

    @Autowired
    TechnologyDAO technologyDAO;

    @Autowired
    CpeDAO cpeDAO;

    @Autowired
    private CveDAO cveDAO;

    @Autowired
    private VulnerabilityDAO vulnerabilityDAO;

    private final RestTemplate restTemplate;

    @Autowired
    private NvdMapperService nvdMapperService;

    @Autowired
    public NvdRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void fetchVulnerabilities() {
        try {
            logger.info("Démarrage de la récupération des vulnérabilités...");

            // Récupération de toutes les technologies
            technologyDAO.findAll().forEach(technology -> {
                logger.info("Traitement de la technologie: " + technology.getName());

                // Pour chaque CPE associé à la technologie
                technology.getCpeList().forEach(cpe -> {
                    try {
                        processCpe(cpe);
                    } catch (Exception e) {
                        logger.log(Level.WARNING, "Erreur lors du traitement du CPE: " + cpe.getCpeName(), e);
                    }
                });
            });

            logger.info("Récupération des vulnérabilités terminée");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erreur globale lors de la récupération des vulnérabilités", e);
        }
    }

    /**
     * Traite un CPE individuel pour récupérer ses vulnérabilités
     */
    private void processCpe(Cpe cpe) {
        logger.info("Récupération des vulnérabilités pour le CPE: " + cpe.getCpeName());

        // Construction de l'URL de l'API NVD
        String url = "https://services.nvd.nist.gov/rest/json/cves/2.0?cpeName=" + cpe.getCpeName() + "&noRejected";

        try {
            // Requête à l'API NVD
            ResponseEntity<CveResponse> response = restTemplate.getForEntity(url, CveResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // Traitement direct de la réponse pour conserver la compatibilité avec le modèle actuel
                processCveResponse(response.getBody(), cpe);
            } else {
                logger.warning("Réponse non réussie de l'API NVD pour le CPE: " + cpe.getCpeName() +
                        ", code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Erreur lors de l'appel à l'API NVD pour le CPE: " + cpe.getCpeName(), e);
        }
    }

    /**
     * Traite la réponse complète de l'API NVD pour un CPE
     */
    private void processCveResponse(CveResponse response, Cpe cpe) {
        if (response.getVulnerabilities() == null || response.getVulnerabilities().isEmpty()) {
            logger.info("Aucune vulnérabilité trouvée pour le CPE: " + cpe.getCpeName());
            return;
        }

        logger.info("Traitement de " + response.getVulnerabilities().size() + " vulnérabilités pour le CPE: " + cpe.getCpeName());

        // Pour chaque vulnérabilité dans la réponse
        for (Vulnerability vulnerability : response.getVulnerabilities()) {
            try {
                if (vulnerability.getCve() == null) {
                    continue;
                }

                CVE cve = vulnerability.getCve();

                // Vérifie si la CVE a un impact élevé
                if (hasHighImpactScore(cve)) {
                    processHighImpactCve(vulnerability, cpe);
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Erreur lors du traitement d'une vulnérabilité", e);
            }
        }

        // Sauvegarde du CPE après traitement de toutes les vulnérabilités
        cpeDAO.save(cpe);
    }

    /**
     * Vérifie si une CVE a un score d'impact élevé (> 5)
     */
    private boolean hasHighImpactScore(CVE cve) {
        if (cve.getMetrics() != null &&
                cve.getMetrics().getCvssMetricV2() != null &&
                !cve.getMetrics().getCvssMetricV2().isEmpty()) {

            // Vérifie si au moins une métrique a un score d'impact > 5
            return cve.getMetrics().getCvssMetricV2().stream()
                    .anyMatch(metric -> metric.getImpactScore() != null && metric.getImpactScore() > 5);
        }
        return false;
    }

    /**
     * Traite une CVE à impact élevé
     */
    private void processHighImpactCve(Vulnerability vulnerability, Cpe cpe) {
        CVE cve = vulnerability.getCve();
        String cveId = cve.getId();

        // Vérifie si la CVE existe déjà
        Optional<CVE> existingCve = cveDAO.findById(cveId);

        if (existingCve.isEmpty()) {
            logger.info("Nouvelle CVE à impact élevé détectée: " + cveId);

            // Sauvegarde de la CVE et de la vulnérabilité
            CVE savedCve = cveDAO.save(cve);

            // Crée et sauvegarde la vulnérabilité
            Vulnerability newVulnerability = new Vulnerability();
            newVulnerability.setCve(savedCve);
            vulnerabilityDAO.save(newVulnerability);

            // Ajoute la CVE au CPE
            if (cpe.getCveList().stream().noneMatch(c -> c.getId().equals(cveId))) {
                cpe.addCve(savedCve);
            }
        } else {
            // La CVE existe déjà, vérifie si elle est déjà liée au CPE
            if (cpe.getCveList().stream().noneMatch(c -> c.getId().equals(cveId))) {
                cpe.addCve(existingCve.get());
                logger.info("CVE existante " + cveId + " ajoutée au CPE: " + cpe.getCpeName());
            }
        }
    }
}