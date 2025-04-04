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
@Table(name = "technologies")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Uniformisé
    private Long technologyId;

    @NotNull
    private String name;

    @OneToMany(fetch = FetchType.LAZY) // Changé à LAZY
    private List<Cpe> cpeList = new ArrayList<>(); // Initialisé

    public void addCpe(Cpe cpe) {
        cpeList.add(cpe);
    }

    public void removeCpe(Cpe cpe) {
        cpeList.remove(cpe);
    }
}
