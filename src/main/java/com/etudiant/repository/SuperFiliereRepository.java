package com.etudiant.repository;

import com.etudiant.entity.SuperFiliere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperFiliereRepository extends JpaRepository<SuperFiliere, Long> {
}
