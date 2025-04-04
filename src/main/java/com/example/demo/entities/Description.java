package com.example.demo.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Classe pour les descriptions
@Entity
@Table(name = "descriptions")
public class Description {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lang", length = 255)
    private String lang;

    @Column(name = "desc_value", length = 2048)
    private String value;

    @ManyToOne
    @JoinColumn(name = "cve_id")
    private CVE cve;

    @Override
    public String toString() {
        return "Description{" +
                "id=" + id +
                ", lang='" + lang + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}