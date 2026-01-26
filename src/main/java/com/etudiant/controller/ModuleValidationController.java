package com.etudiant.controller;

import com.etudiant.entity.ModuleValidation;
import com.etudiant.entity.StatutModule;
import com.etudiant.entity.User;
import com.etudiant.service.ModuleValidationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/module_validation")
public class ModuleValidationController {

    private final ModuleValidationService service;

    public ModuleValidationController(ModuleValidationService service) {
        this.service = service;
    }

    @GetMapping()
    public List<ModuleValidation> findAll() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Optional<ModuleValidation> findByID(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/statut/{statut}")
    public List<ModuleValidation> findByStatut(@PathVariable StatutModule statut) {
        return service.findByStatut(statut);
    }

    @PostMapping()
    public ModuleValidation save(@RequestBody ModuleValidation moduleValidation) {
        return service.save(moduleValidation);
    }

    @PutMapping("/{id}/statut/{statut}")
    public ModuleValidation updateStatut(@PathVariable Long id, @PathVariable StatutModule statut) {
        return service.updateStatut(id, statut);
    }

    @PutMapping("/{id}/note/{note}")
    public ModuleValidation updateNote(@PathVariable Long id, @PathVariable Double note) {
        return service.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void DeleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
