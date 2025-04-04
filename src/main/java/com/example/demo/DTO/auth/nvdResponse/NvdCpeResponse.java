package com.example.demo.DTO.auth.nvdResponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class NvdCpeResponse {
    @JsonProperty("resultsPerPage")
    private Integer resultsPerPage;

    @JsonProperty("startIndex")
    private Integer startIndex;

    @JsonProperty("totalResults")
    private Integer totalResults;

    @JsonProperty("format")
    private String format;

    @JsonProperty("version")
    private String version;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("products")
    private List<Product> products;
}
