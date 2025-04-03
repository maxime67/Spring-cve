package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cpe_match")
public class CpeMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vulnerable")
    private Boolean vulnerable;

    @Column(name = "criteria", length = 512)
    private String criteria;

    @Column(name = "version_end_including", length = 100)
    private String versionEndIncluding;

    @Column(name = "match_criteria_id", length = 100)
    private String matchCriteriaId;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getVulnerable() {
        return vulnerable;
    }

    public void setVulnerable(Boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getVersionEndIncluding() {
        return versionEndIncluding;
    }

    public void setVersionEndIncluding(String versionEndIncluding) {
        this.versionEndIncluding = versionEndIncluding;
    }

    public String getMatchCriteriaId() {
        return matchCriteriaId;
    }

    public void setMatchCriteriaId(String matchCriteriaId) {
        this.matchCriteriaId = matchCriteriaId;
    }
}

