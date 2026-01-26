package com.etudiant.service;

import com.etudiant.entity.Module;
import com.etudiant.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService implements IModuleService{
    private final ModuleRepository repository;

    public ModuleService(ModuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Module> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Module> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Module save(Module module) {
        return repository.save(module);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
