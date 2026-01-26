package com.etudiant.service;

import com.etudiant.entity.ModuleValidation;
import com.etudiant.entity.StatutModule;

import java.util.List;

public interface IModuleValidationService extends ICrudService<ModuleValidation, Long> {

    public ModuleValidation updateStatut(Long id, StatutModule statut);

    public ModuleValidation updateNote(Long id, Double note);

    public List<ModuleValidation> findByStatut(StatutModule statutModule);
}
