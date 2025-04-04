package com.example.demo.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MetricsDTO {
    private Long id;
    private List<CvssMetricV2DTO> cvssMetricV2 = new ArrayList<>();
}
