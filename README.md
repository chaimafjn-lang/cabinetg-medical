# 🏥 Cabinet Médical — Spring Boot

> Projet Mini Spring Boot — FST Master STR 2025/2026  
> Encadrant : Nader Belhadj — Software Ing. | Chercheur IA

---

## 👥 Équipe de développement

| Noms:

| Chaima | 
| Nawres |
| Faiza |

---

## 📋 Description du projet

Application web de gestion d'un cabinet médical développée 
avec Spring Boot 3. Elle permet de gérer les patients, 
les médecins, les rendez-vous et les ordonnances avec 
un système d'authentification multi-rôles.

---

## 🛠️ Technologies utilisées

| Couche | Technologie |
|--------|-------------|
| Backend | Spring Boot 3.2, Spring MVC, Spring Data JPA |
| Frontend | Thymeleaf 3, Bootstrap 5, Font Awesome |
| Base de données | MySQL 8, Flyway |
| Sécurité | Spring Security (ADMIN, MEDECIN, SECRETAIRE, PATIENT) |
| Build | Maven, Lombok |

---

## ⚙️ Prérequis

Avant de lancer le projet, assurez-vous d'avoir :

- Java 17
- Maven
- MySQL 8 (ou XAMPP)
- Eclipse IDE

---

## 🚀 Installation et lancement

### 1. Cloner le projet
```bash
git clone https://github.com/cabinetg-medical/cabinet-medical.git
cd cabinet-medical
```

### 2. Configurer la base de données
Ouvrir `src/main/resources/application.properties` et modifier :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cabinet_medical?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=VOTRE_MOT_DE_PASSE
```

### 3. Lancer l'application
Dans Eclipse : clic droit sur le projet → `Run As` → `Spring Boot App`

Ou avec Maven :
```bash
mvn spring-boot:run
```

### 4. Accéder à l'application
Ouvrir le navigateur et aller sur :
http://localhost:8080

---

## 🔐 Comptes de test

| Utilisateur | Mot de passe | Rôle |
|-------------|--------------|------|
| admin | password | password |
| medecin1 | password | password |
| secretaire1 | password | password |

---

## 📁 Structure du projet
src/
├── main/
│   ├── java/com/fst/cabinet/
│   │   ├── config/          → Security + UserDetailsService
│   │   ├── controller/      → Controllers MVC
│   │   ├── dto/             → DTOs
│   │   ├── entity/          → Entités JPA
│   │   ├── repository/      → Spring Data Repositories
│   │   └── service/         → Logique métier
│   └── resources/
│       ├── db/migration/    → Scripts Flyway
│       └── templates/       → Pages Thymeleaf
└── test/

---

## 📊 Fonctionnalités

### ✅ Fonctionnalités implémentées

- [x] Page d'accueil publique
- [x] Authentification multi-rôles
- [x] Inscription patient
- [x] CRUD Patients
- [x] CRUD Médecins
- [x] Gestion des Rendez-vous
- [x] Vérification non-chevauchement des RDV
- [x] Gestion des Ordonnances
- [x] Tableau de bord avec statistiques
- [x] Recherche patients et médecins

### 🔜 Fonctionnalités bonus

- [ ] Calendrier hebdomadaire visuel
- [ ] Export ordonnance en PDF
- [ ] Notifications RDV imminents

---

## 🗄️ Modèle de données
Patient ──────┐
├──── RendezVous ──── Ordonnance ──── LigneMedicament
Medecin ──────┘

### Entités principales :
- **Patient** : CIN, nom, prénom, date naissance, antécédents
- **Medecin** : nom, spécialité, numéro d'ordre, actif
- **RendezVous** : patient, médecin, date/heure, durée, statut
- **Ordonnance** : rendez-vous, médicaments, observations
- **LigneMedicament** : nom, posologie, durée
- **Utilisateur** : username, password, rôle

---

## 📅 Planning

| Semaine | Objectifs | Statut |
|---------|-----------|--------|
| S1 | Setup, entités JPA, Flyway, Security | ✅ |
| S2 | CRUD Patients et Médecins | ✅ |
| S3 | Rendez-vous et Ordonnances | ✅ |
| S4 | Dashboard, rapport, soutenance | 🔜 |

---

## 📝 Notes importantes

- Les migrations BDD utilisent **Flyway** 
  (pas de `ddl-auto=create`)
- La validation de non-chevauchement des RDV 
  est dans la **couche service**
- Les mots de passe sont cryptés avec **BCrypt**
- Toutes les dates utilisent **java.time**

---

*FST — Faculté des Sciences de Tunis — Master STR — 2025/2026*
