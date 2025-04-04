package com.example.demo.DTO.auth.nvdResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    @JsonProperty("cpe")
    private CpeNvd cpe;

    public CpeNvd getCpe() {
        return cpe;
    }

    public void setCpe(CpeNvd cpe) {
        this.cpe = cpe;
    }
}