package com.etudiant.service;

import com.etudiant.dto.UserCreationResponse;
import com.etudiant.dto.UserDto;
import com.etudiant.entity.*;
import com.etudiant.entity.Module;
import com.etudiant.repository.FiliereRepository;
import com.etudiant.repository.ModuleValidationRepository;
import com.etudiant.repository.UserRepository;
import com.etudiant.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{

    private final UserRepository repository;
    private final FiliereRepository filiereRepository;
    private final ModuleValidationRepository moduleValidationRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwt;


    public UserService(UserRepository repository, FiliereRepository filiereRepository, ModuleValidationRepository moduleValidationRepository, PasswordEncoder passwordEncoder, JwtService jwt) {
        this.repository = repository;
        this.filiereRepository = filiereRepository;
        this.moduleValidationRepository = moduleValidationRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvée"));
    }

    @Override
    public User save(User user) {
        repository.save(user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = findById(id);
        return toDTO(user);
    }

    @Transactional
    @Override
    public void assignerFiliere(Long userId, Long filiereId) {
        User user = findById(userId);
        Filiere filiere = filiereRepository.findById(filiereId).orElseThrow();
        if (user.getFiliere() != null) {
            throw new RuntimeException("L'utilisateur a déjà une filière");
        }
        user.setFiliere(filiere);
        repository.save(user);

        for (Module module : filiere.getModules()) {
            ModuleValidation mv = ModuleValidation.builder()
                .user(user)
                .module(module)
                .statut(StatutModule.A_FAIRE)
                .build();
            moduleValidationRepository.save(mv);
        }
    }

    @Override
    public List<Module> getModules(Long id) {
        User user =  findById(id);
        return user.getFiliere().getModules();
    }

    @Override
    public List<ModuleValidation> getValidations(Long userId) {
        return moduleValidationRepository.findByUserId(userId);
    }

    @Override
    public List<UserDto> getEtudiants() {
        return repository.findByRole(Role.ETUDIANT).stream().map(user -> toDTO(user)).collect(Collectors.toList());
    }

    @Override
    public String generateEmail(User user) {
        String base = user.getPrenom().toLowerCase().replace(" ", ".") + "." + user.getNom().toLowerCase().replace(" ", ".");
        String email = base + "@vde.com";
        int i = 1;
        while (repository.existsUserByEmail(email)) {
            email = base + i + "@vde.com";
            i++;
        }
        return email;
    }

    @Override
    public String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public UserCreationResponse createUser(UserDto userDto) {
        User user = User.builder()
                .nom(userDto.getNom())
                .prenom(userDto.getPrenom())
                .role(userDto.getRole())
                .superFiliere(userDto.getSuperFiliere())
                .build();
        String email = generateEmail(user);
        String rawPassword = generatePassword();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        repository.save(user);
        UserCreationResponse savedUser = new UserCreationResponse();
        savedUser.setEmail(user.getEmail());
        savedUser.setPassword(rawPassword);
        return savedUser;
    }

    @Override
    public UserDto getConectUser(String token) {
        String email = jwt.extractEmail(token);
        User user = repository.findByEmail(email).orElseThrow();
        return toDTO(user);
    }

    @Override
    public List<UserDto> findAllUsers() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UserDto toDTO(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setCode(user.getCode());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setFiliere(user.getFiliere());
        dto.setDateEntree(user.getDateEntree());
        dto.setProgression(calculateProgression(user));
        dto.setSuperFiliere(user.getSuperFiliere());
        return dto;
    }

    private Double calculateProgression(User user) {
        List<ModuleValidation> mvs =  getValidations(user.getId());
        int total = mvs.size();
        double termine = 0.00;
        if (total == 0) return 0.0;
        for (ModuleValidation mv : mvs) {
            if (mv.getStatut() == StatutModule.TERMINE) {
                termine += 1;
            }
        }
        return (termine/total) * 100;
    }
}
