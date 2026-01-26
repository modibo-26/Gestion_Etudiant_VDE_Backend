package com.etudiant.repository;

import com.etudiant.entity.ModuleValidation;
import com.etudiant.entity.Role;
import com.etudiant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);

    Boolean existsUserByEmail(String email);
}
