package com.example.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long UserId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String roles;
    @NotNull
    private String password;
    @OneToMany
    private List<Technology> technologyList = new ArrayList<>();


    public User(String firstName, String lastName, String username, String email, String phoneNumber, String roles, String password, List<Technology> technologyList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
        this.password = password;
        this.technologyList = technologyList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(UserId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone_number='").append(phoneNumber).append('\'');
        sb.append(", roles='").append(roles).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", technologyList=").append(technologyList);
        sb.append('}');
        return sb.toString();
    }
    public void addTechnology(Technology technology) {
        technologyList.add(technology);
    }
}