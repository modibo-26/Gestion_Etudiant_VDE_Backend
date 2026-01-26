package com.etudiant.service;

import com.etudiant.entity.ModuleValidation;
import com.etudiant.entity.StatutModule;
import com.etudiant.repository.ModuleValidationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleValidationService implements IModuleValidationService{

    private final ModuleValidationRepository repository;

    public ModuleValidationService(ModuleValidationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ModuleValidation> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ModuleValidation> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public ModuleValidation save(ModuleValidation moduleValidation) {
        return repository.save(moduleValidation);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ModuleValidation updateStatut(Long id, StatutModule statut) {
        ModuleValidation mv = repository.findById(id).orElseThrow();
        mv.setStatut(statut);
        if (statut == StatutModule.TERMINE) {
            mv.setValidationDate(LocalDateTime.now());
        }
        return repository.save(mv);
    }

    @Override
    public ModuleValidation updateNote(Long id, Double note) {
        ModuleValidation mv = repository.findById(id).orElseThrow();
        mv.setNote(note);
        return repository.save(mv);
    }

    @Override
    public List<ModuleValidation> findByStatut(StatutModule statutModule) {
        return repository.findByStatut(statutModule);
    }
}
