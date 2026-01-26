package com.etudiant.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    @Column(unique = true)
    private String email;
//    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    private LocalDateTime dateEntree;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.ETUDIANT;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;


    @PrePersist
    public void prePersist() {
        if (role == null) {
            role = Role.ETUDIANT;
        }
    }

}
