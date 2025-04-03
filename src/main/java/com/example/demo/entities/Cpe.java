package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.demo.entities.Cve;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cpe")
public class Cpe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long cpeId;
    @NotNull
    private String cpeName;
    @OneToMany
    private List<Cve> cveList;

    public void addCve(Cve cve) {
        cveList.add(cve);
    }
}
