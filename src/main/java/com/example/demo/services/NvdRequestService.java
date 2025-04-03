package com.example.demo.services;

import com.example.demo.entities.CveResponse;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class NvdRequestService {
    @Autowired
    TechnologyDAO technologyDAO;
    @Autowired
    DescriptionDAO descriptionDAO;
    @Autowired
    VulnerabilityDAO vulnerabilityDAO;
    @Autowired
    CpeDAO cpeDAO;
    private final RestTemplate restTemplate;
    @Autowired
    private CveDAO cveDAO;
    @Autowired
    private WeaknessDAO weaknessDAO;

    @Autowired
    public NvdRequestService(RestTemplate restTemplate, RestTemplate restTemplate1) {
        this.restTemplate = restTemplate1;
    }
    @Transactional
    public void fetchVulnerabilities() {
        try {
            technologyDAO.findAll().forEach(technology -> {
                technology.getCpeList().forEach(cpe -> {
                    String url = "https://services.nvd.nist.gov/rest/json/cves/2.0?cpeName=" + cpe.getCpeName() + "&noRejected";
                    ResponseEntity<CveResponse> response = restTemplate.getForEntity(url, CveResponse.class);

                    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                        response.getBody().getVulnerabilities().forEach(cve -> {
                            if (cve.getCve().getMetrics() != null && cve.getCve().getMetrics().getCvssMetricV2() != null) {
                                cve.getCve().getMetrics().getCvssMetricV2().forEach(metric -> {
                                    if (metric.getImpactScore() > 5) {
                                        // Check if CVE is already known
                                        if (cveDAO.findById(cve.getCve().getId()).isEmpty()) {
//                                            System.out.println("Nouvelle CVE : " + cve.getCve().getId());
                                            vulnerabilityDAO.save(cve);
//                                            // Check if CVE is already merged to the target CpeName
                                            if (cpe.getCveList().stream().noneMatch(existingCve ->
                                                    existingCve.getId().equals(cve.getCve().getId()))) {
                                                System.out.println("The CVE : " + cve.getCve().getId() + " was add to CPE " + cpe.getCpeName());
                                                cpe.addCve(cve.getCve());
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        // Handle unsuccessful HTTP response
                        System.err.println("Failed to fetch data from NVD for CPE: " + cpe.getCpeName());
                    }
                    cpeDAO.save(cpe);
                });
            });
        } catch (Exception e) {
            // Handle exceptions (e.g., log the error)
            System.err.println("An error occurred while fetching vulnerabilities: " + e.getMessage());
            e.printStackTrace();
        }
        cpeDAO.findAll().forEach(cpe -> {
            cpe.getCveList().forEach(System.out::println);
        });
    }



}
