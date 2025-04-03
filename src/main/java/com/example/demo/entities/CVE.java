package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cves")
public class CVE implements Serializable {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "source_identifier")
    private String sourceIdentifier;

    @Column(name = "published")
    private LocalDateTime published;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @Column(name = "vuln_status")
    private String vulnStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cve_tags", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "tag")
    private List<String> cveTags = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "id" )
    private List<Description> descriptions = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "metrics_id")
    private Metrics metrics;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "id")
    private List<Weakness> weaknesses = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "id")
    private List<Configuration> configurations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "id")
    private List<Reference> references = new ArrayList<>();

    @ManyToMany(mappedBy = "followCveList")
    private Collection<User> users;
    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceIdentifier() {
        return sourceIdentifier;
    }

    public void setSourceIdentifier(String sourceIdentifier) {
        this.sourceIdentifier = sourceIdentifier;
    }

    public LocalDateTime getPublished() {
        return published;
    }

    public void setPublished(LocalDateTime published) {
        this.published = published;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getVulnStatus() {
        return vulnStatus;
    }

    public void setVulnStatus(String vulnStatus) {
        this.vulnStatus = vulnStatus;
    }

    public List<String> getCveTags() {
        return cveTags;
    }

    public void setCveTags(List<String> cveTags) {
        this.cveTags = cveTags;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public List<Weakness> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<Weakness> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "CVE{" +
                ", cveId='" + id + '\'' +
                ", sourceIdentifier='" + sourceIdentifier + '\'' +
                ", published=" + published +
                ", lastModified=" + lastModified +
                ", vulnStatus='" + vulnStatus + '\'' +
                ", cveTags=" + cveTags +
                ", descriptions=" + descriptions +
                ", metrics=" + metrics +
                ", weaknesses=" + weaknesses +
                ", configurations=" + configurations +
                ", references=" + references +
                '}';
    }


}