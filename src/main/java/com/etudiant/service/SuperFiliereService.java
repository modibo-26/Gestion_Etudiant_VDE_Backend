package com.etudiant.service;

import com.etudiant.dto.FiliereDto;
import com.etudiant.entity.Filiere;
import com.etudiant.entity.SuperFiliere;
import com.etudiant.repository.FiliereRepository;
import com.etudiant.repository.SuperFiliereRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuperFiliereService implements ISuperFiliereService {

    private final SuperFiliereRepository repository;
    private final FiliereRepository filiereRepository;

    @Override
    public List<Filiere> getFilieres(Long id) {
        return filiereRepository.findAll().stream().filter(filiere -> filiere.getSuperFiliere().getId().equals(id)).collect(Collectors.toList());
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
    public SuperFiliere save(SuperFiliere superFiliere) {
        return repository.save(superFiliere);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
