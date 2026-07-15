package com.etudiant.service;

import com.etudiant.dto.FiliereDto;
import com.etudiant.entity.*;
import com.etudiant.entity.Module;
import com.etudiant.mappers.FiliereMapper;
import com.etudiant.repository.FiliereRepository;
import com.etudiant.repository.ModuleRepository;
import com.etudiant.repository.ModuleValidationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class FiliereService implements IFiliereService{

    private final FiliereRepository repository;
    private final ModuleRepository moduleRepository;
    private final ModuleValidationRepository moduleValidationRepository;
    private final FiliereMapper filiereMapper;
    private final UserService userService;
    private final ModuleService moduleService;
    private final SuperFiliereService superFiliereService;


    @Override
    public List<FiliereDto> findAll() {
        return repository.findAll().stream()
                .filter(Objects::nonNull)
                .map(filiere-> filiereMapper.toFiliereDto(filiere))
                .collect(Collectors.toList());
    }

    @Override
    public Filiere findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Filière non trouvée"));
    }

    @Override
    public FiliereDto save(FiliereDto filiere) {
        Filiere fil=filiereMapper.toFiliere(filiere,userService,moduleService,superFiliereService);
        Filiere savedFiliere = repository.save(fil);
        return filiereMapper.toFiliereDto(savedFiliere);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> getUsers(Long id) {
        Filiere filiere = findById(id);
        return filiere.getUsers().stream().filter(user->user.getRole().name()=="ETUDIANT").collect(Collectors.toList());
    }

    @Override
    public List<Module> getModules(Long id) {
        Filiere filiere = findById(id);
        return filiere.getModules();
    }

    @Transactional
    @Override
    public Filiere addModule(Long filiereId, Long moduleId) {
        Filiere filiere = findById(filiereId);
        Module module = moduleRepository.findById(moduleId).orElseThrow();
        if (!filiere.getModules().contains(module)) {
            filiere.getModules().add(module);
            for (User user : getUsers(filiereId)) {
                ModuleValidation mv = new ModuleValidation();
                mv.setUser(user);
                mv.setModule(module);
                mv.setStatut(StatutModule.A_FAIRE);
                moduleValidationRepository.save(mv);
            }
        }
        return repository.save(filiere);
    }

    @Transactional
    @Override
    public Filiere removeModule(Long filiereId, Long moduleId) {
        Filiere filiere = findById(filiereId);
        Module module = moduleRepository.findById(moduleId).orElseThrow();
        if (filiere.getModules().contains(module)) {
            filiere.getModules().remove(module);
            for (User user : getUsers(filiereId)) {
                moduleValidationRepository.deleteByModuleAndUser(module, user);
            }
        }
        return repository.save(filiere);
    }

    @Override
    public List<FiliereDto> getFilieresBySuperFiliere_Id(Long superFiliereId) {
        return repository.getFilieresBySuperFiliere_Id(superFiliereId)
                .stream()
                .map(filiere ->filiereMapper.toFiliereDto(filiere))
                .collect(Collectors.toList());
    }

    public List<Module> getModulesByFiliere(Long id) {
        return repository.findById(id).get().getModules();
    }

}
