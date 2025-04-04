package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cvss_data")
public class CvssData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "version")
    private String version;

    @Column(name = "vector_string")
    private String vectorString;

    @Column(name = "base_score")
    private Double baseScore;

    @Column(name = "access_vector")
    private String accessVector;

    @Column(name = "access_complexity")
    private String accessComplexity;

    @Column(name = "authentication")
    private String authentication;

    @Column(name = "confidentiality_impact")
    private String confidentialityImpact;

    @Column(name = "integrity_impact")
    private String integrityImpact;

    @Column(name = "availability_impact")
    private String availabilityImpact;

    // Suppression des getters/setters redondants avec @Data
}