package com.etudiant.service;


import com.etudiant.entity.Module;

import java.util.List;

public interface IModuleService extends ICrudService<Module, Long>{
    List<Module> findAll();
    Module save(Module module);
}
