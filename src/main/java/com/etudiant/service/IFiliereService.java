package com.etudiant.service;

import com.etudiant.dto.FiliereDto;
import com.etudiant.entity.Filiere;
import com.etudiant.entity.Module;
import com.etudiant.entity.User;

import java.util.List;

public interface IFiliereService extends ICrudService<Filiere, Long> {

    List<FiliereDto> findAll();
    public List<User> getUsers(Long id);
    public FiliereDto save(FiliereDto filiere);
    public List<Module> getModules(Long id);

    public Filiere addModule(Long filiereId, Long moduleId);

    public Filiere removeModule(Long filiereId, Long moduleId);

    List<FiliereDto> getFilieresBySuperFiliere_Id(Long superFiliereId);
}
