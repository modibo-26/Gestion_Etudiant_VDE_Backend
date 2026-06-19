package com.etudiant.controller;

import com.etudiant.entity.Filiere;
import com.etudiant.entity.SuperFiliere;
import com.etudiant.entity.User;
import com.etudiant.service.FiliereService;
import com.etudiant.service.SuperFiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("super_filiere/")
public class SuperFiliereController {
    @Autowired
    private SuperFiliereService service;
    @GetMapping()
    public List<SuperFiliere> findAll() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public SuperFiliere findByID(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/{id}/users")
    public List<User> getUsers(@PathVariable Long id) {
        return service.getUsers(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void DeleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
