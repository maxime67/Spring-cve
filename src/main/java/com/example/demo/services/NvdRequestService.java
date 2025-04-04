package com.example.demo.services;

import com.example.demo.DTO.auth.nvdResponse.NvdCpeResponse;
import com.example.demo.DTO.auth.nvdResponse.Product;
import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service gérant les requêtes à l'API du National Vulnerability Database (NVD)
 * pour récupérer les vulnérabilités des technologies surveillées.
 */
@Service
public class NvdRequestService {
    private static final Logger logger = Logger.getLogger(NvdRequestService.class.getName());
    private static final String NVD_CPE_API_URL = "https://services.nvd.nist.gov/rest/json/cpes/2.0";
    private static final String NVD_CVE_API_URL = "https://services.nvd.nist.gov/rest/json/cves/2.0";
    private static final int API_RATE_LIMIT_DELAY_MS = 700;
    private static final double HIGH_IMPACT_THRESHOLD = 5.0;

    // Nombre d'erreurs de limite de taux rencontrées
    private int rateLimitErrorCount = 1000;

    // Clé API pour NVD (devrait être dans les propriétés de l'application)
    private static final String API_KEY = "fb9fc9e6-0eed-4f8b-a051-b0210d08afa6";

    @Autowired
    private TechnologyDAO technologyDAO;

    @Autowired
    private CpeDAO cpeDAO;

    @Autowired
    private CveDAO cveDAO;

    @Autowired
    private VulnerabilityDAO vulnerabilityDAO;

    private final RestTemplate restTemplate;

    @Autowired
    public NvdRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Récupère les vulnérabilités pour toutes les technologies suivies
     */
    @Transactional
    public void fetchVulnerabilities() {
        try {
            logger.info("Démarrage de la récupération des vulnérabilités...");
            rateLimitErrorCount = 0;

            List<Technology> technologies = technologyDAO.findAll();
            logger.info("Traitement de " + technologies.size() + " technologies");

            // Traite chaque technologie
            for (Technology technology : technologies) {
                logger.info("Traitement de la technologie: " + technology.getName());
                fetchCpesForTechnology(technology);

                // Traite chaque CPE associé à la technologie
                for (Cpe cpe : technology.getCpeList()) {
                    try {
                        fetchVulnerabilitiesForCpe(cpe);
                    } catch (Exception e) {
                        handleProcessingException("CPE", cpe.getCpeName(), e);
                    }
                }
            }

            logger.info("Récupération des vulnérabilités terminée avec succès");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erreur globale lors de la récupération des vulnérabilités", e);
        }
    }

    /**
     * Récupère les CPEs associés à une technologie depuis l'API NVD
     */
    private void fetchCpesForTechnology(Technology technology) {
        logger.info("Recherche des CPE pour la technologie: " + technology.getName());

        // Préparation de l'URI pour la requête
        URI uri = UriComponentsBuilder.fromHttpUrl(NVD_CPE_API_URL)
                .queryParam("cpeMatchString", "cpe:2.3:*:" + technology.getName())
                .build()
                .encode()
                .toUri();

        logger.info("URI de requête CPE: " + uri);

        try {
            // Exécute la requête à l'API NVD
            ResponseEntity<NvdCpeResponse> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(createAuthHeaders()),
                    NvdCpeResponse.class
            );

            // Traite la réponse si elle contient des données
            if (response.getBody() != null) {
                saveCpesFromResponse(response.getBody(), technology);
            }
        } catch (Exception e) {
            handleProcessingException("technologie", technology.getName(), e);
        }
    }

    /**
     * Enregistre les CPEs trouvés dans la réponse de l'API
     */
    private void saveCpesFromResponse(NvdCpeResponse response, Technology technology) {
        if (response.getTotalResults() == null || response.getTotalResults() == 0) {
            logger.info("Aucun CPE trouvé pour la technologie: " + technology.getName());
            return;
        }

        logger.info("Traitement de " + response.getTotalResults() + " CPE(s) pour " + technology.getName());

        // Traite chaque produit dans la réponse
        for (Product product : response.getProducts()) {
            try {
                // Vérifie si le CPE existe déjà
                String cpeName = product.getCpe().getCpeName();
                if (cpeName == null || cpeDAO.findByCpeName(cpeName) != null) {
                    continue;
                }

                // Crée et sauvegarde le nouveau CPE
                Cpe cpe = new Cpe();
                cpe.setCpeName(cpeName);
                logger.info("Nouveau CPE trouvé: " + cpeName);

                // Sauvegarde d'abord le CPE
                cpe = cpeDAO.save(cpe);

                // Ajoute le CPE à la technologie
                technology.addCpe(cpe);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Erreur lors du traitement d'un CPE", e);
            }
        }

        // Sauvegarde la technologie avec ses nouveaux CPEs
        technologyDAO.save(technology);
    }

    /**
     * Récupère les vulnérabilités pour un CPE spécifique
     */
    private void fetchVulnerabilitiesForCpe(Cpe cpe) throws InterruptedException {
        logger.info("Recherche des vulnérabilités pour le CPE: " + cpe.getCpeName());

        // Préparation de l'URI pour la requête
        URI uri = UriComponentsBuilder.fromHttpUrl(NVD_CVE_API_URL)
                .queryParam("cpeName", cpe.getCpeName())
                .queryParam("noRejected", "")
                .build()
                .encode()
                .toUri();

        try {
            // Exécute la requête à l'API NVD
            ResponseEntity<CveResponse> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(createAuthHeaders()),
                    CveResponse.class
            );

            // Pause pour respecter la limite de taux de l'API
            Thread.sleep(API_RATE_LIMIT_DELAY_MS);

            // Traitement de la réponse
            if (response.getBody() != null) {
                processVulnerabilities(response.getBody(), cpe);
            }
        } catch (Exception e) {
            handleProcessingException("CPE", cpe.getCpeName(), e);
        }
    }

    /**
     * Traite les vulnérabilités reçues de l'API NVD pour un CPE donné
     */
    private void processVulnerabilities(CveResponse response, Cpe cpe) {
        if (response.getVulnerabilities() == null || response.getVulnerabilities().isEmpty()) {
            logger.info("Aucune vulnérabilité trouvée pour le CPE: " + cpe.getCpeName());
            return;
        }

        logger.info("Traitement de " + response.getVulnerabilities().size() + " vulnérabilité(s) pour " + cpe.getCpeName());

        // Traite chaque vulnérabilité
        for (Vulnerability vulnerability : response.getVulnerabilities()) {
            try {
                if (vulnerability.getCve() == null) {
                    continue;
                }

                CVE cve = vulnerability.getCve();

                // Enregistre seulement les CVE à impact élevé
                if (isHighImpactVulnerability(cve)) {
                    saveHighImpactVulnerability(vulnerability, cpe);
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Erreur lors du traitement d'une vulnérabilité", e);
            }
        }

        // Sauvegarde le CPE après avoir ajouté toutes les vulnérabilités
        cpeDAO.save(cpe);
    }

    /**
     * Détermine si une CVE a un score d'impact considéré comme élevé
     */
    private boolean isHighImpactVulnerability(CVE cve) {
        if (cve.getMetrics() == null || cve.getMetrics().getCvssMetricV2() == null) {
            return false;
        }

        // Vérifie si au moins une métrique dépasse le seuil défini
        return cve.getMetrics().getCvssMetricV2().stream()
                .anyMatch(metric ->
                        metric.getImpactScore() != null &&
                                metric.getImpactScore() > HIGH_IMPACT_THRESHOLD &&
                                metric.getExploitabilityScore() != null &&
                                metric.getExploitabilityScore() > HIGH_IMPACT_THRESHOLD
                );
    }

    /**
     * Enregistre une vulnérabilité à impact élevé pour un CPE donné
     */
    private void saveHighImpactVulnerability(Vulnerability vulnerability, Cpe cpe) {
        CVE cve = vulnerability.getCve();
        String cveId = cve.getId();

        try {
            // Vérifie si la CVE est déjà associée à ce CPE en utilisant l'ID exact
            if (cpe.getCveList().stream().anyMatch(existingCve -> existingCve.getId().equals(cveId))) {
                logger.info("La CVE " + cveId + " est déjà associée au CPE: " + cpe.getCpeName());
                return;
            }

            // Cherche si la CVE existe déjà en base de données
            Optional<CVE> existingCve = cveDAO.findById(cveId);

            if (existingCve.isEmpty()) {
                logger.info("Nouvelle CVE à impact élevé détectée: " + cveId);

                // Sauvegarde la nouvelle CVE
                CVE savedCve = cveDAO.save(cve);

                // Crée et sauvegarde la vulnérabilité
                Vulnerability newVulnerability = new Vulnerability();
                newVulnerability.setCve(savedCve);
                vulnerabilityDAO.save(newVulnerability);

                // Utilise une transaction séparée pour l'association
                addCveToCpe(cpe.getCpeId(), savedCve.getId());
            } else {
                // La CVE existe déjà, ajoute l'association dans une transaction séparée
                addCveToCpe(cpe.getCpeId(), existingCve.get().getId());
                logger.info("CVE existante " + cveId + " ajoutée au CPE: " + cpe.getCpeName());
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Erreur lors de l'association de la CVE " + cveId +
                    " au CPE " + cpe.getCpeName(), e);
        }
    }

    /**
     * Ajoute une CVE à un CPE avec gestion des contraintes d'unicité
     * Cette méthode est exécutée dans sa propre transaction
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = DataIntegrityViolationException.class)
    protected void addCveToCpe(Long cpeId, String cveId) {
        try {
            // Vérifie d'abord si l'association existe déjà
            boolean associationExists = cpeDAO.existsAssociationBetweenCpeAndCve(cpeId, cveId);

            if (!associationExists) {
                // Si l'association n'existe pas, l'ajouter
                cpeDAO.createAssociationBetweenCpeAndCve(cpeId, cveId);
                logger.info("Association créée entre CPE " + cpeId + " et CVE " + cveId);
            } else {
                logger.info("Association entre CPE " + cpeId + " et CVE " + cveId + " déjà existante.");
            }
        } catch (DataIntegrityViolationException e) {
            // Capture les erreurs de contrainte et les traite comme si l'association existait déjà
            logger.log(Level.INFO, "L'association CPE " + cpeId + " et CVE " + cveId +
                    " existe probablement déjà. Ignoré.");
            // Ne pas relancer l'exception - nous voulons continuer
        }
    }

    /**
     * Gère les exceptions lors du traitement des données
     */
    private void handleProcessingException(String type, String name, Exception e) {
        if (e instanceof HttpClientErrorException) {
            HttpClientErrorException httpEx = (HttpClientErrorException) e;
            if (httpEx.getStatusCode() == HttpStatus.FORBIDDEN) {
                // Limite les logs pour les erreurs de limite de taux
                if (rateLimitErrorCount % 50 == 0) {
                    logger.warning("Limite de taux API atteinte pour " + type + ": " + name);
                }
                rateLimitErrorCount++;
            } else {
                logger.log(Level.WARNING, "Erreur HTTP " + httpEx.getStatusCode() +
                        " lors du traitement de " + type + ": " + name, e);
            }
        } else {
            logger.log(Level.WARNING, "Erreur lors du traitement de " + type + ": " + name, e);
        }
    }

    /**
     * Crée les en-têtes d'authentification pour les requêtes API
     */
    private HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        return headers;
    }
}