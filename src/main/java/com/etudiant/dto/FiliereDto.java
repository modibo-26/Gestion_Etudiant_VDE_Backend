package com.etudiant.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class FiliereDto {
    private Long id;
    private String nom;
    private String description;
    private List<Long> usersIds;
    private List<Long> modulesIds;
    private Long superFiliereId;
    private Integer nbEtudiants;
}
