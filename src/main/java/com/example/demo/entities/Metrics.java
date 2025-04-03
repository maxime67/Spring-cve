package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metrics")
public class Metrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "metrics_id")
    private List<CvssMetricV2> cvssMetricV2 = new ArrayList<>();

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CvssMetricV2> getCvssMetricV2() {
        return cvssMetricV2;
    }

    public void setCvssMetricV2(List<CvssMetricV2> cvssMetricV2) {
        this.cvssMetricV2 = cvssMetricV2;
    }
}
