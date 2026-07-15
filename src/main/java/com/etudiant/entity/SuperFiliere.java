package com.etudiant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "super_filiere")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuperFiliere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    @OneToMany(mappedBy = "superFiliere")
    @JsonIgnore
    private List<Filiere> filieres;

    public int getNbFilieres() {
        return filieres != null ? filieres.size() : 0;
    }

}
