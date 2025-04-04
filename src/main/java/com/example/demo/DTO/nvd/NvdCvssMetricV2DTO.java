package com.example.demo.DTO.nvd;

import lombok.Data;

@Data
public class NvdCvssMetricV2DTO {
    private String source;
    private String type;
    private NvdCvssDataDTO cvssData;
    private String baseSeverity;
    private Double exploitabilityScore;
    private Double impactScore;
}
