package com.example.demo.DTO.nvd;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NvdMetricsDTO {
    private List<NvdCvssMetricV2DTO> cvssMetricV2 = new ArrayList<>();
}