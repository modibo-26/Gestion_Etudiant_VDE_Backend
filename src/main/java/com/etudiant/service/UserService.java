package com.etudiant.service;

import com.etudiant.dto.FiliereDto;
import com.etudiant.dto.UserCreationResponse;
import com.etudiant.dto.UserDto;
import com.etudiant.entity.*;
import com.etudiant.entity.Module;
import com.etudiant.repository.FiliereRepository;
import com.etudiant.repository.ModuleValidationRepository;
import com.etudiant.repository.SuperFiliereRepository;
import com.etudiant.repository.UserRepository;
import com.etudiant.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class UserService implements IUserService{

    private final UserRepository repository;
    private final FiliereRepository filiereRepository;
    private final SuperFiliereRepository superFiliereRepository;
    private final ModuleValidationRepository moduleValidationRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwt;


    @Override
    public List<UserDto> findAll() {
        return repository.findAll().stream().map(x -> toDTO(x)).collect(Collectors.toList());
    }
    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvée"));
    }

    @Override
    public void deleteById(Long id) {
        User userToDelete = repository.findById(id).orElseThrow();
        List <ModuleValidation> userToDeleteModules =moduleValidationRepository.findByUserId(id);
        Iterator<ModuleValidation> iterator = userToDeleteModules.iterator();
        while (iterator.hasNext()) {
            moduleValidationRepository.delete(iterator.next());
        }

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
            ModuleValidation mv = new  ModuleValidation();
            mv.setUser(user);
            mv.setModule(module);
            mv.setStatut(StatutModule.A_FAIRE);
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
        return repository.findByRole(Role.ETUDIANT).stream().map(user -> toDTO(user)).filter(user->user.getRole().name()=="ETUDIANT").collect(Collectors.toList());
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
                .superFiliere(superFiliereRepository.findById(userDto.getSuperFiliereId()).get())
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

    @Override
    public UserDto save(User user) {
        repository.save(user);
        return toDTO(user);
    }

    public UserDto toDTO(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setCode(user.getCode());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setSuperFiliereId(user.getSuperFiliere().getId());
        dto.setDateEntree(user.getDateEntree());
        dto.setProgression(calculateProgression(user));
        if(user.getFiliere()!=null) {
        dto.setFiliereId(user.getFiliere().getId());}
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

    public UserCreationResponse resetPassword(Long id) {
        String newPassword = generatePassword();
        User user = repository.findById(id).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);

        //réponse
        UserCreationResponse gen = new UserCreationResponse();
        gen.setEmail(user.getEmail());
        gen.setPassword(newPassword);
        return gen;
    }

    @Override
    public List<UserDto> getUsersBySuperFiliere(Long superFiliereId) {
        return repository.findBySuperFiliere_Id(superFiliereId).stream()
                .map(x -> toDTO(x)).filter(user->user.getRole().name()=="ETUDIANT").collect(Collectors.toList());
    }
}
