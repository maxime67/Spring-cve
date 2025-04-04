package com.example.demo.DTO.auth.nvdResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class Title {
    @JsonProperty("title")
    private String title;

    @JsonProperty("lang")
    private String lang;

}