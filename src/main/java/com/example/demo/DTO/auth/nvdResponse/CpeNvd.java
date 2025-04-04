package com.example.demo.DTO.auth.nvdResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CpeNvd {
    @JsonProperty("deprecated")
    private Boolean deprecated;

    @JsonProperty("cpeName")
    private String cpeName;

    @JsonProperty("cpeNameId")
    private String cpeNameId;

    @JsonProperty("lastModified")
    private String lastModified;

    @JsonProperty("created")
    private String created;

    @JsonProperty("titles")
    private List<Title> titles;

    @JsonProperty("refs")
    private List<Reference> refs;

}
