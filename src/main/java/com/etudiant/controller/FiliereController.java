package com.etudiant.controller;

import com.etudiant.entity.Filiere;
import com.etudiant.entity.Module;
import com.etudiant.entity.User;
import com.etudiant.service.FiliereService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filieres")
public class FiliereController {
    private final FiliereService service;

    public FiliereController(FiliereService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Filiere> findAll() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Filiere> findByID(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/{id}/users")
    public List<User> getUsers(@PathVariable Long id) {
        return service.getUsers(id);
    }

    @GetMapping("/{id}/modules")
    public List<Module> getModule(@PathVariable Long id) {
        return service.getModules(id);
    }

    @PostMapping()
    public Filiere save(@RequestBody Filiere filiere) {
        return service.save(filiere);
    }

    @PostMapping("/{id}/modules/{moduleId}")
    public Filiere addModule(@PathVariable Long  id, @PathVariable Long moduleId) {
        return service.addModule(id, moduleId);
    }

    @PutMapping()
    public Filiere update(@RequestBody Filiere filiere) {
        return service.save(filiere);
    }

    @DeleteMapping("/{id}/modules/{moduleId}")
    public Filiere removeModule(@PathVariable Long  id, @PathVariable Long moduleId) {
        return service.removeModule(id, moduleId);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void DeleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
