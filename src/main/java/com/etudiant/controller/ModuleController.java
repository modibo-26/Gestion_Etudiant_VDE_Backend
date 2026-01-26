package com.etudiant.controller;

import com.etudiant.entity.Module;
import com.etudiant.service.ModuleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService service;

    public ModuleController(ModuleService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Module> findAll() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Module> findByID(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping()
    public Module save(@RequestBody Module module) {
        return service.save(module);
    }

    @PutMapping()
    public Module update(@RequestBody Module module) {
        return service.save(module);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void DeleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
