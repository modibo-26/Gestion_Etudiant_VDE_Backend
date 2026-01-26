package com.etudiant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "filiere")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    @OneToMany(mappedBy = "filiere")
    @JsonIgnore
    private List<User> users;
    @ManyToMany
    @JoinTable(
        name = "filiere_module",
        joinColumns = @JoinColumn(name = "filliere_id"),
        inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    private List<Module> modules;


    public int getNbEtudiants() {
        return users != null ? users.size() : 0;
    }

}
