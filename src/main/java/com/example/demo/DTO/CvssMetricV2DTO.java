package com.example.demo.DTO;

import lombok.Data;

@Data
public class CvssMetricV2DTO {
    private Long id;
    private String source;
    private String type;
    private CvssDataDTO cvssData;
    private String baseSeverity;
    private Double exploitabilityScore;
    private Double impactScore;
}