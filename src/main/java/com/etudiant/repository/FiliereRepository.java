package com.etudiant.repository;

import com.etudiant.entity.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    List<Filiere> getFilieresBySuperFiliere_Id(Long superFiliereId);
}
