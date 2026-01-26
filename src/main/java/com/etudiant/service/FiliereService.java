package com.etudiant.service;

import com.etudiant.entity.*;
import com.etudiant.entity.Module;
import com.etudiant.repository.FiliereRepository;
import com.etudiant.repository.ModuleRepository;
import com.etudiant.repository.ModuleValidationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FiliereService implements IFiliereService{

    private final FiliereRepository repository;
    private final ModuleRepository moduleRepository;
    private final ModuleValidationRepository moduleValidationRepository;

    public FiliereService(FiliereRepository repository, ModuleRepository moduleRepository, ModuleValidationRepository moduleValidationRepository) {
        this.repository = repository;
        this.moduleRepository = moduleRepository;
        this.moduleValidationRepository = moduleValidationRepository;
    }

    @Override
    public List<Filiere> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Filiere> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Filiere save(Filiere filiere) {
        return repository.save(filiere);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> getUsers(Long id) {
        Filiere filiere = repository.findById(id).orElseThrow();
        return filiere.getUsers();
    }

    @Override
    public List<Module> getModules(Long id) {
        Filiere filiere = repository.findById(id).orElseThrow();
        return filiere.getModules();
    }

    @Transactional
    @Override
    public Filiere addModule(Long filiereId, Long moduleId) {
        Filiere filiere = repository.findById(filiereId).orElseThrow();
        Module module = moduleRepository.findById(moduleId).orElseThrow();
        if (!filiere.getModules().contains(module)) {
            filiere.getModules().add(module);
            for (User user : getUsers(filiereId)) {
                ModuleValidation mv = ModuleValidation.builder()
                        .user(user)
                        .module(module)
                        .statut(StatutModule.A_FAIRE)
                        .build();
                moduleValidationRepository.save(mv);
            }
        }
        return repository.save(filiere);
    }

    @Transactional
    @Override
    public Filiere removeModule(Long filiereId, Long moduleId) {
        Filiere filiere = repository.findById(filiereId).orElseThrow();
        Module module = moduleRepository.findById(moduleId).orElseThrow();
        if (filiere.getModules().contains(module)) {
            filiere.getModules().remove(module);
            for (User user : getUsers(filiereId)) {
                moduleValidationRepository.deleteByModuleAndUser(module, user);
            }
        }
        return repository.save(filiere);
    }

}
