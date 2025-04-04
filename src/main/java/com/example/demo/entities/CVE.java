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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "metrics_id")
    private Metrics metrics;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cve")
    private List<Description> descriptions = new ArrayList<>();

    @ManyToMany(mappedBy = "followCveList")
    private List<User> users = new ArrayList<>();

    // Méthode helper pour maintenir la relation bidirectionnelle
    public void addDescription(Description description) {
        descriptions.add(description);
        description.setCve(this);
    }

    public void removeDescription(Description description) {
        descriptions.remove(description);
        description.setCve(null);
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
                '}';
    }


}