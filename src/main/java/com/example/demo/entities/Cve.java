package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cve")
public class Cve {
    @Id
    @NotNull
    private String cveId;
    @NotNull
    private Date created_at;
    @NotNull
    private Date update_at;
    @NotNull
    @Column(length = 524)
    private String description;
    @NotNull
    private String title;
    @NotNull
    private Float score;

}
