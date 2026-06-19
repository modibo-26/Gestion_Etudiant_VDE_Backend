package com.etudiant.service;

import com.etudiant.entity.Filiere;
import com.etudiant.entity.SuperFiliere;
import com.etudiant.entity.User;
import com.etudiant.repository.SuperFiliereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuperFiliereService implements ISuperFiliereService {

    private final SuperFiliereRepository repository;

    @Override
    public List<User> getUsers(Long id) {
        List<User> users = repository.findById(id).get().getUsers();
        return users;
    }

    @Override
    public List<Filiere> getFilieres(Long id) {
        return repository.findById(id).get().getFilieres();
    }

    @Override
    public List<SuperFiliere> findAll() {
        return repository.findAll();
    }

    @Override
    public SuperFiliere findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Super Filière non trouvée"));
    }

    @Override
    public SuperFiliere save(SuperFiliere entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
