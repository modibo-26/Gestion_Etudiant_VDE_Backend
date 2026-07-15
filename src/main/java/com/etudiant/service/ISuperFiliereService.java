package com.etudiant.service;

import com.etudiant.entity.Filiere;
import com.etudiant.entity.SuperFiliere;
import com.etudiant.entity.User;

import java.util.List;

public interface ISuperFiliereService extends ICrudService<SuperFiliere, Long> {

    //public List<User> getUsers(Long id);

    List<SuperFiliere> findAll();

    public List<Filiere> getFilieres(Long id);

    public SuperFiliere save(SuperFiliere superFiliere);


}
