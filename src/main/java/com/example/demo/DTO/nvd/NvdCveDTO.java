package com.example.demo.DTO.nvd;

import com.example.demo.entities.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO pour les données reçues de l'API NVD
 * Contient uniquement les champs essentiels
 */

@Data
public class NvdCveDTO {
    private String id;
    private LocalDateTime published;
    private LocalDateTime lastModified;
    private List<NvdDescriptionDTO> descriptions = new ArrayList<>();
    private NvdMetricsDTO metrics;

    /**
     * Convertit le DTO en entité CVE pour le stockage en base de données
     * Ne garde que les champs essentiels
     */
    public CVE toEntity() {
        CVE cve = new CVE();
        cve.setId(this.id);
        cve.setPublished(this.published);
        cve.setLastModified(this.lastModified);

        // Conversion des descriptions
        if (this.descriptions != null) {
            List<Description> descriptionEntities = new ArrayList<>();
            for (NvdDescriptionDTO descDTO : this.descriptions) {
                // Ne conserver que la description en anglais
                if ("en".equals(descDTO.getLang())) {
                    Description desc = new Description();
                    desc.setLang(descDTO.getLang());
                    desc.setValue(descDTO.getValue());
                    desc.setCve(cve);
                    descriptionEntities.add(desc);
                }
            }
            cve.setDescriptions(descriptionEntities);
        }

        // Conversion des métriques
        if (this.metrics != null && this.metrics.getCvssMetricV2() != null && !this.metrics.getCvssMetricV2().isEmpty()) {
            Metrics metricsEntity = new Metrics();
            List<CvssMetricV2> metricEntities = new ArrayList<>();

            // Ne conserver que les métriques importantes
            for (NvdCvssMetricV2DTO metricDTO : this.metrics.getCvssMetricV2()) {
                if (metricDTO.getImpactScore() > 5) {  // Filtre par impact score
                    CvssMetricV2 metric = new CvssMetricV2();
                    metric.setSource(metricDTO.getSource());
                    metric.setType(metricDTO.getType());
                    metric.setBaseSeverity(metricDTO.getBaseSeverity());
                    metric.setExploitabilityScore(metricDTO.getExploitabilityScore());
                    metric.setImpactScore(metricDTO.getImpactScore());

                    // Conversion des données CVSS
                    if (metricDTO.getCvssData() != null) {
                        CvssData cvssData = new CvssData();
                        cvssData.setBaseScore(metricDTO.getCvssData().getBaseScore());

                        // On ne conserve que les données essentielles du vecteur CVSS
                        cvssData.setAccessVector(metricDTO.getCvssData().getAccessVector());
                        cvssData.setAccessComplexity(metricDTO.getCvssData().getAccessComplexity());
                        cvssData.setConfidentialityImpact(metricDTO.getCvssData().getConfidentialityImpact());

                        metric.setCvssData(cvssData);
                    }

                    metricEntities.add(metric);
                }
            }

            metricsEntity.setCvssMetricV2(metricEntities);
            cve.setMetrics(metricsEntity);
        }

        return cve;
    }
}