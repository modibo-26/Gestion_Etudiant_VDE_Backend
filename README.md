# GestionEtu - Backend

API REST de gestion des étudiants, filières et modules avec authentification JWT et gestion des rôles.

## Stack technique

- **Java 17** / **Spring Boot 3**
- **Spring Security** + **JWT**
- **Spring Data JPA** (Hibernate)
- **SQL Server**
- **Lombok**

## Fonctionnalités

- **Authentification JWT** avec gestion de rôles (ADMIN, FORMATEUR, ETUDIANT)
- **CRUD complet** : Users, Filières, Modules, ModuleValidation
- **Génération automatique d'email** au format `prenom.nom@vde.com`
- **Progression calculée** par étudiant (% de modules validés via DTO)
- **Synchronisation des ModuleValidation** à l'ajout/suppression de modules
- **Contrainte d'unicité** sur le couple `(user_id, module_id)`

## Architecture

```
src/main/java/com/gestion/filieres/
├── config/          # SecurityConfig, CorsConfig, JwtFilter
├── controller/      # AuthController, UserController, FiliereController, ModuleController
├── dto/             # UserDTO (avec progression calculée)
├── entity/          # User, Filiere, Module, ModuleValidation, Role (enum)
├── repository/
├── service/
│   ├── interfaces/
│   └── impl/
└── security/        # JwtUtil, JwtAuthenticationFilter
```

## Modèle de données

| Entité | Description |
|--------|-------------|
| **User** | Étudiants, formateurs et admins (email, password, nom, prénom, rôle, filière) |
| **Filiere** | Filières de formation contenant des modules |
| **Module** | Modules rattachés à une filière |
| **ModuleValidation** | État de validation d'un module par un étudiant (EN_COURS, VALIDE, NON_VALIDE) |

### Relations

- `User` → `Filiere` : ManyToOne
- `Filiere` → `Module` : OneToMany
- `ModuleValidation` → `User` + `Module` : ManyToOne (contrainte unique sur le couple)

## Rôles

| Rôle | Permissions |
|------|-------------|
| **ADMIN** | Création d'utilisateurs, gestion complète |
| **FORMATEUR** | Validation des modules, consultation des étudiants et filières |
| **ETUDIANT** | Consultation de sa progression et de ses modules |

## Installation

### Prérequis

- Java 17+
- SQL Server (port 1433)
- Maven

### Configuration

Créer la base de données :

```sql
CREATE DATABASE gestion_etudiants;
```

Configurer `application.yml` :

```yaml
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=gestion_etudiants;encrypt=true;trustServerCertificate=true
    username: sa
    password: votre_mot_de_passe
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8080
```

### Lancer le projet

```bash
mvn spring-boot:run
```

## Endpoints principaux

### Authentification

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/auth/login` | Connexion (retourne un token JWT) |

### Users

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/users` | Liste des utilisateurs |
| GET | `/users/{id}` | Détail d'un utilisateur (avec progression) |
| POST | `/users` | Créer un utilisateur |
| PUT | `/users/{id}` | Modifier un utilisateur |
| DELETE | `/users/{id}` | Supprimer un utilisateur |

### Filières

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/filieres` | Liste des filières |
| GET | `/filieres/{id}` | Détail d'une filière |
| POST | `/filieres` | Créer une filière |
| PUT | `/filieres/{id}` | Modifier une filière |
| DELETE | `/filieres/{id}` | Supprimer une filière |

### Modules

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/modules` | Liste des modules |
| POST | `/modules` | Créer un module (sync auto des ModuleValidation) |
| DELETE | `/modules/{id}` | Supprimer un module (sync auto) |

### ModuleValidation

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/validations/user/{userId}` | Validations d'un étudiant |
| PUT | `/validations/{id}` | Modifier le statut de validation |

## Frontend

Le frontend Angular associé est dans un dépôt séparé. Il inclut des dashboards par rôle, un système Trello avec drag & drop pour la gestion des validations, et une recherche avec normalisation des accents.
