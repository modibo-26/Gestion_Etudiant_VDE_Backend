package com.etudiant.service;

import com.etudiant.dto.UserDto;
import com.etudiant.entity.ModuleValidation;
import com.etudiant.entity.StatutModule;

import java.util.List;

public interface IModuleValidationService extends ICrudService<ModuleValidation, Long> {

    List<ModuleValidation> findAll();

    public ModuleValidation save(ModuleValidation moduleValidation);

    public ModuleValidation updateStatut(Long id, StatutModule statut);

    public ModuleValidation updateNote(Long id, Double note);

    public List<ModuleValidation> findByStatut(StatutModule statutModule);
}
