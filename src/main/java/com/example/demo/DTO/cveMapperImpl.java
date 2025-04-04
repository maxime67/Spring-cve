package com.example.demo.DTO;

import com.example.demo.entities.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class cveMapperImpl implements CveMapper {
    @Override
    public CveDTO toDto(CVE cve) {
        if (cve == null) {
            return null;
        }

        CveDTO cveDTO = new CveDTO();

        cveDTO.setSourceIdentifier(cve.getSourceIdentifier());

        cveDTO.setId(cve.getId());
        cveDTO.setPublished(cve.getPublished());
        cveDTO.setLastModified(cve.getLastModified());
        cveDTO.setVulnStatus(cve.getVulnStatus());

        if (cve.getCveTags() != null) {
            cveDTO.setCveTags(new ArrayList<>(cve.getCveTags()));
        }

        if (cve.getDescriptions() != null) {
            List<DescriptionDTO> descriptionDTOs = new ArrayList<>();
            for (Description desc : cve.getDescriptions()) {
                descriptionDTOs.add(toDto(desc));
            }
            cveDTO.setDescriptions(descriptionDTOs);
        }

        if (cve.getMetrics() != null) {
            cveDTO.setMetrics(toDto(cve.getMetrics()));
        }

        return cveDTO;
    }

    @Override
    public List<CveDTO> toDtoList(List<CVE> cves) {
        if (cves == null) {
            return null;
        }

        List<CveDTO> list = new ArrayList<>(cves.size());
        for (CVE cve : cves) {
            list.add(toDto(cve));
        }

        return list;
    }

    @Override
    public DescriptionDTO toDto(Description description) {
        if (description == null) {
            return null;
        }

        DescriptionDTO descriptionDTO = new DescriptionDTO();

        descriptionDTO.setId(description.getId());
        descriptionDTO.setLang(description.getLang());
        descriptionDTO.setValue(description.getValue());

        return descriptionDTO;
    }

    @Override
    public MetricsDTO toDto(Metrics metrics) {
        if (metrics == null) {
            return null;
        }

        MetricsDTO metricsDTO = new MetricsDTO();

        metricsDTO.setId(metrics.getId());

        if (metrics.getCvssMetricV2() != null) {
            List<CvssMetricV2DTO> cvssMetricV2DTOs = new ArrayList<>();
            for (CvssMetricV2 metric : metrics.getCvssMetricV2()) {
                cvssMetricV2DTOs.add(toDto(metric));
            }
            metricsDTO.setCvssMetricV2(cvssMetricV2DTOs);
        }

        return metricsDTO;
    }

    @Override
    public CvssMetricV2DTO toDto(CvssMetricV2 cvssMetricV2) {
        if (cvssMetricV2 == null) {
            return null;
        }

        CvssMetricV2DTO cvssMetricV2DTO = new CvssMetricV2DTO();

        cvssMetricV2DTO.setId(cvssMetricV2.getId());
        cvssMetricV2DTO.setSource(cvssMetricV2.getSource());
        cvssMetricV2DTO.setType(cvssMetricV2.getType());
        cvssMetricV2DTO.setBaseSeverity(cvssMetricV2.getBaseSeverity());
        cvssMetricV2DTO.setExploitabilityScore(cvssMetricV2.getExploitabilityScore());
        cvssMetricV2DTO.setImpactScore(cvssMetricV2.getImpactScore());

        if (cvssMetricV2.getCvssData() != null) {
            cvssMetricV2DTO.setCvssData(toDto(cvssMetricV2.getCvssData()));
        }

        return cvssMetricV2DTO;
    }

    @Override
    public CvssDataDTO toDto(CvssData cvssData) {
        if (cvssData == null) {
            return null;
        }

        CvssDataDTO cvssDataDTO = new CvssDataDTO();

        cvssDataDTO.setId(cvssData.getId());
        cvssDataDTO.setVersion(cvssData.getVersion());
        cvssDataDTO.setVectorString(cvssData.getVectorString());
        cvssDataDTO.setBaseScore(cvssData.getBaseScore());
        cvssDataDTO.setAccessVector(cvssData.getAccessVector());
        cvssDataDTO.setAccessComplexity(cvssData.getAccessComplexity());
        cvssDataDTO.setAuthentication(cvssData.getAuthentication());
        cvssDataDTO.setConfidentialityImpact(cvssData.getConfidentialityImpact());
        cvssDataDTO.setIntegrityImpact(cvssData.getIntegrityImpact());
        cvssDataDTO.setAvailabilityImpact(cvssData.getAvailabilityImpact());

        return cvssDataDTO;
    }
}
