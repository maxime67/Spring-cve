package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cvss_metric_v2")
public class CvssMetricV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "type")
    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cvss_data_id")
    private CvssData cvssData;

    @Column(name = "base_severity")
    private String baseSeverity;

    @Column(name = "exploitability_score")
    private Double exploitabilityScore;

    @Column(name = "impact_score")
    private Double impactScore;

    @Column(name = "ac_insuf_info")
    private Boolean acInsufInfo;

    @Column(name = "obtain_all_privilege")
    private Boolean obtainAllPrivilege;

    @Column(name = "obtain_user_privilege")
    private Boolean obtainUserPrivilege;

    @Column(name = "obtain_other_privilege")
    private Boolean obtainOtherPrivilege;

    @Column(name = "user_interaction_required")
    private Boolean userInteractionRequired;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CvssData getCvssData() {
        return cvssData;
    }

    public void setCvssData(CvssData cvssData) {
        this.cvssData = cvssData;
    }

    public String getBaseSeverity() {
        return baseSeverity;
    }

    public void setBaseSeverity(String baseSeverity) {
        this.baseSeverity = baseSeverity;
    }

    public Double getExploitabilityScore() {
        return exploitabilityScore;
    }

    public void setExploitabilityScore(Double exploitabilityScore) {
        this.exploitabilityScore = exploitabilityScore;
    }

    public Double getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(Double impactScore) {
        this.impactScore = impactScore;
    }

    public Boolean getAcInsufInfo() {
        return acInsufInfo;
    }

    public void setAcInsufInfo(Boolean acInsufInfo) {
        this.acInsufInfo = acInsufInfo;
    }

    public Boolean getObtainAllPrivilege() {
        return obtainAllPrivilege;
    }

    public void setObtainAllPrivilege(Boolean obtainAllPrivilege) {
        this.obtainAllPrivilege = obtainAllPrivilege;
    }

    public Boolean getObtainUserPrivilege() {
        return obtainUserPrivilege;
    }

    public void setObtainUserPrivilege(Boolean obtainUserPrivilege) {
        this.obtainUserPrivilege = obtainUserPrivilege;
    }

    public Boolean getObtainOtherPrivilege() {
        return obtainOtherPrivilege;
    }

    public void setObtainOtherPrivilege(Boolean obtainOtherPrivilege) {
        this.obtainOtherPrivilege = obtainOtherPrivilege;
    }

    public Boolean getUserInteractionRequired() {
        return userInteractionRequired;
    }

    public void setUserInteractionRequired(Boolean userInteractionRequired) {
        this.userInteractionRequired = userInteractionRequired;
    }
}