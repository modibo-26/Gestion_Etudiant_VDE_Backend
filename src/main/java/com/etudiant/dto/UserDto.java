package com.etudiant.dto;

import com.etudiant.entity.Filiere;
import com.etudiant.entity.Role;
import com.etudiant.entity.SuperFiliere;

import java.time.LocalDateTime;

public class UserDto {
    private Long id;
    private String code;
    private String nom;
    private String prenom;
    private String email;
    private Role role;
    private double progression;
    private LocalDateTime dateEntree;
    private Long filiereId;
    private Long superFiliereId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public double getProgression() {
        return progression;
    }

    public void setProgression(double progression) {
        this.progression = progression;
    }

    public LocalDateTime getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(LocalDateTime dateEntree) {
        this.dateEntree = dateEntree;
    }

    public Long getFiliereId() {
        return filiereId;
    }

    public void setFiliereId(Long filiereId) {
        this.filiereId = filiereId;
    }

    public Long getSuperFiliereId() {return superFiliereId;}

    public void setSuperFiliereId(Long superFiliere) {this.superFiliereId = superFiliere;}

}

