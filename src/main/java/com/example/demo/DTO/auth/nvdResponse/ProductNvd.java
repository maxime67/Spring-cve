package com.example.demo.DTO.auth.nvdResponse;

import com.example.demo.entities.Cpe;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductNvd {
    @JsonProperty("cpe")
    private Cpe cpe;

    // Getters and Setters
    public Cpe getCpe() {
        return cpe;
    }

    public void setCpe(Cpe cpe) {
        this.cpe = cpe;
    }
}