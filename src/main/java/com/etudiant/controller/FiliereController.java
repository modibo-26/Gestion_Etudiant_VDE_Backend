package com.etudiant.controller;

import com.etudiant.dto.FiliereDto;
import com.etudiant.entity.Filiere;
import com.etudiant.entity.Module;
import com.etudiant.entity.User;
import com.etudiant.service.FiliereService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filieres")
@AllArgsConstructor
public class FiliereController {
    private final FiliereService service;


    @GetMapping()
    public List<FiliereDto> findAll() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Filiere findByID(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/{id}/users")
    public List<User> getUsers(@PathVariable Long id) {
        return service.getUsers(id);
    }

    @GetMapping("/{id}/filiere_modules")
    public List<Module> getModulesByFiliere(@PathVariable Long id) {
        return service.getModulesByFiliere(id);
    }

    @GetMapping("/{id}/modules")
    public List<Module> getModule(@PathVariable Long id) {
        return service.getModules(id);
    }

    @GetMapping("/{id}/superFiliere")
    public List<Module> getBySUperFiliere(@PathVariable Long superFiliereId) {
        return service.getModules(superFiliereId);
    }

    @PostMapping()
    public FiliereDto save(@RequestBody FiliereDto filiereDto) {

        System.out.println(">>>>>>>>>>>>>>>>>><<<<<<<<<<<< POST /filieres");
        return service.save(filiereDto);
    }

    @PostMapping("/{id}/modules/{moduleId}")
    public Filiere addModule(@PathVariable Long  id, @PathVariable Long moduleId) {
        return service.addModule(id, moduleId);
    }

    @PutMapping()
    public FiliereDto update(@RequestBody FiliereDto filiereDto) {
        return service.save(filiereDto);
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
