package com.etudiant.controller;

import com.etudiant.dto.UserCreationResponse;
import com.etudiant.dto.UserDto;
import com.etudiant.entity.Module;
import com.etudiant.entity.ModuleValidation;
import com.etudiant.entity.User;
import com.etudiant.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/me")
    public UserDto getConnectUser(@RequestHeader("Authorization") String token) {
        return service.getConectUser(token.replace("Bearer ", ""));
    }

    @GetMapping("/etudiants")
    public List<User> getEtudiants() {
        return service.getEtudiants();
    }

    @GetMapping("/{id}")
    public UserDto findByID(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping("/{id}/modules")
    public List<Module> getModules(@PathVariable Long id) {
        return service.getModules(id);
    }

    @GetMapping("/{id}/validations")
    public List<ModuleValidation> getValidations(@PathVariable Long id) {
        return service.getValidations(id);
    }

    @PostMapping()
    public User save(@RequestBody User user) {
        return service.save(user);
    }
    @PostMapping("/create")
    public UserCreationResponse createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @PostMapping("/{id}/filiere/{filiereId}")
    public void assignerFiliere(@PathVariable Long id, @PathVariable Long filiereId) {
        service.assignerFiliere(id, filiereId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void DeleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

}
