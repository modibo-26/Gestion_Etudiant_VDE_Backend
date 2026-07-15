package com.etudiant.mappers;

import com.etudiant.dto.FiliereDto;
import com.etudiant.entity.Filiere;
import com.etudiant.entity.SuperFiliere;
import com.etudiant.entity.User;
import  com.etudiant.entity.Module;
import com.etudiant.service.ModuleService;
import com.etudiant.service.SuperFiliereService;
import com.etudiant.service.UserService;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;



@Mapper(componentModel ="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class FiliereMapper {

    @Mapping(target = "users", source = "usersIds", qualifiedByName = "mapIdsToUsers")
    @Mapping(target = "modules", source = "modulesIds", qualifiedByName = "mapIdsToModules")
    @Mapping(target = "superFiliere", source = "superFiliereId", qualifiedByName = "mapIdToSuperFiliere")
    public abstract Filiere toFiliere(FiliereDto filiereDto,@Context UserService userService,@Context ModuleService moduleService, @Context SuperFiliereService superFiliereService);

    @Mapping(target = "usersIds", source = "users", qualifiedByName = "mapUsersToIds")
    @Mapping(target = "modulesIds", source = "modules", qualifiedByName = "mapModulesToIds")
    @Mapping(target = "superFiliereId", source = "superFiliere", qualifiedByName = "mapSuperFiliereToId")
    @Mapping(target = "nbEtudiants", source = "nbEtudiants")
    public abstract FiliereDto toFiliereDto(Filiere filiere);

   @Named("mapIdsToUsers")
    protected List<User> mapIdsToUsers(List<Long> ids, @Context UserService userService) {
        if (ids == null) return null;
        return ids.stream()
                .map(userService::findById)
                .collect(Collectors.toList());
    }

    @Named("mapUsersToIds")
    protected List<Long> mapUsersToIds(List<User> users) {
        if (users == null) return null;
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    @Named("mapIdsToModules")
    protected List<Module> mapIdsToModules(List<Long> ids, @Context ModuleService moduleService) {
        if (ids == null) return null;
        return ids.stream()
                .map(moduleService::findById)
                .collect(Collectors.toList());
    }

    @Named("mapModulesToIds")
    protected List<Long> mapModulesToIds(List<Module> modules) {
        if (modules == null) return null;
        List<Long> collect = modules.stream()
                .map(Module::getId)
                .collect(Collectors.toList());
        return collect;
    }

    @Named("mapIdToSuperFiliere")
    protected SuperFiliere mapIdToSuperFiliere(Long id, @Context SuperFiliereService superFiliereService) {
        if (id == null) return null;
        return superFiliereService.findById(id);
    }

    @Named("mapSuperFiliereToId")
    protected Long mapSuperFiliereToId(SuperFiliere superFiliere) {
        if (superFiliere == null) return null;

        return superFiliere.getId();
    }

   /* @Named("mapNbEtudiants")
    protected int mapNbEtudiants(SuperFiliere superFiliere) {
        if (superFiliere == null) return 0;

        return superFiliere.getNbFilieres();
    }*/


}
