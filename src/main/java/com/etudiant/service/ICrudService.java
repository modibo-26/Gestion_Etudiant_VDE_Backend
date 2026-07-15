package com.etudiant.service;

import com.etudiant.dto.FiliereDto;

public interface ICrudService<T, ID> {

    T findById(ID id);
    void deleteById(ID id);
}
