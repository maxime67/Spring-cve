package com.example.demo.DTO.nvd;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class NvdResponseDTO {
    private int resultsPerPage;
    private int startIndex;
    private int totalResults;
    private String format;
    private String version;
    private LocalDateTime timestamp;
    private List<NvdVulnerabilityDTO> vulnerabilities = new ArrayList<>();
}

@Data
class NvdVulnerabilityDTO {
    private NvdCveDTO cve;
}
