package com.etudiant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "module")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    @ManyToMany(mappedBy = "modules")
    @JsonIgnore
    private List<Filiere> fillieres;
}
