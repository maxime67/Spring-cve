package com.example.demo.DTO.nvd;

import lombok.Data;

@Data
public class NvdCvssDataDTO {
    private String version;
    private String vectorString;
    private Double baseScore;
    private String accessVector;
    private String accessComplexity;
    private String authentication;
    private String confidentialityImpact;
    private String integrityImpact;
    private String availabilityImpact;
}
