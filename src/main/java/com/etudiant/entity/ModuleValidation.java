package com.etudiant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "module_validation", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "module_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ModuleValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private StatutModule statut = StatutModule.A_FAIRE;
    private LocalDateTime validationDate = null;
    private Double note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
}
