package com.etudiant.service;

import com.etudiant.dto.UserCreationResponse;
import com.etudiant.dto.UserDto;
import com.etudiant.entity.*;
import com.etudiant.entity.Module;

import java.util.List;

public interface IUserService extends ICrudService<User, Long> {

    public UserDto getUserById(Long id);
    public void assignerFiliere(Long userId, Long filiereId);
    public List<Module> getModules(Long id);
    public List<ModuleValidation> getValidations(Long id);
    public List<User> getEtudiants();
    public String generateEmail(User user);
    public String generatePassword();
    public UserCreationResponse createUser(User user);
    public UserDto getConectUser(String token);

}
