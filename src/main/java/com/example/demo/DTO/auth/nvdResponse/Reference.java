package com.example.demo.DTO.auth.nvdResponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Data
@Getter
public class Reference {
    @JsonProperty("ref")
    private String ref;

    @JsonProperty("type")
    private String type;

}

