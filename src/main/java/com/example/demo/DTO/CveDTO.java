package com.example.demo.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CveDTO {
    private String id;
    private String sourceIdentifier;
    private LocalDateTime published;
    private LocalDateTime lastModified;
    private String vulnStatus;
    private List<String> cveTags = new ArrayList<>();
    private List<DescriptionDTO> descriptions = new ArrayList<>();
    private MetricsDTO metrics;
}
