package com.etudiant.repository;

import com.etudiant.entity.Module;
import com.etudiant.entity.ModuleValidation;
import com.etudiant.entity.StatutModule;
import com.etudiant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleValidationRepository extends JpaRepository<ModuleValidation, Long> {

    List<ModuleValidation> findByUserId(Long userId);

    List<ModuleValidation> findByStatut(StatutModule statutModule);

    void deleteByModuleAndUser(Module module, User user);

}
