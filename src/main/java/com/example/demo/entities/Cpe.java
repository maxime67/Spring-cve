package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cpe")
public class Cpe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpeId;

    @NotNull
    private String cpeName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CVE> cveList = new ArrayList<>();

    public void addCve(CVE cve) {
        if (!cveList.contains(cve)) {
            cveList.add(cve);
        }
    }

    public void removeCve(CVE cve) {
        cveList.remove(cve);
    }
}
