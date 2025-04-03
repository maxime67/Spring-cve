package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "technologies")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long technologyId;
    @NotNull
    private String name;
    @OneToMany
    private List<Cpe> cpeList;

    public void addCpe(Cpe cpe) {
        cpeList.add(cpe);
    }

}
