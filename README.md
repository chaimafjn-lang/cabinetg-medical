# 🏥 Cabinet Médical — Spring Boot
> Mini-Projet Spring Boot — FST Master STR 2025/2026  
> Encadrant : Nader Belhadj — Software Ing. | Chercheur IA
---
## 👥 Équipe de développement

 Nom et Rôle principal 
| **Faiza** || **Chaima** | | **Nawres** |
   Entités JPA • CRUD Patient • Controllers • Email Service 
   Repositories • Services • Scripts Flyway • Dashboard 
   Spring Security • Vues Thymeleaf • Pages HTML • Upload Photo


## 📋 Description
Application web complète de gestion d'un cabinet médical développée avec Spring Boot 3. Elle permet de gérer les patients, les médecins, les rendez-vous et les ordonnances avec un système d'authentification multi-rôles et des notifications par email.
## 🛠️ Technologies utilisées
| Couche | Technologie |
|--------|-------------|
| Backend | Spring Boot 3.2 • Spring MVC • Spring Data JPA |
| Frontend | Thymeleaf 3 • Bootstrap 5 • Font Awesome |
| Base de données | MySQL 8 • Flyway |
| Sécurité | Spring Security (ADMIN, MEDECIN, SECRETAIRE, PATIENT) |
| Email | Spring Mail • Gmail SMTP |
| Build | Maven • Lombok |
---

## ⚙️ Prérequis
- Java 17
- Maven
- MySQL 8 ou XAMPP
- Eclipse IDE
- Compte Gmail avec mot de passe application
## 🚀 Installation
### 1. Cloner le projet
```bash
git clone https://github.com/cabinetg-medical/cabinet-medical.git
cd cabinet-medical
```
### 2. Configurer `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cabinet_medical?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=VOTRE_MOT_DE_PASSE

spring.mail.username=cabinetmedical014@gmail.com
spring.mail.password=wkbw izow awwq adzz
### 3. Lancer XAMPP
- Démarrer **MySQL** dans XAMPP Control Panel
### 4. Lancer l'application
Dans Eclipse : clic droit → `Run As` → `Spring Boot App`
### 5. Accéder à l'application
http://localhost:8080
## 🔐 Comptes de test



| Utilisateur | Mot de passe | Rôle | Accès |
|-------------|--------------|------|-------|
| `admin` | `password` | ADMIN | Tout |
| `medecin` | `password` | MEDECIN | Patients • RDV • Ordonnances |
| `secretaire` | `password` | SECRETAIRE | Patients • Médecins • RDV |
> Les patients créent leur compte via la page S'inscrire
## 📁 Structure du projet
src/ ├── main/ │ ├── java/com/fst/cabinet/ │ │ ├── config/ │ │ │ ├── SecurityConfig.java │ │ │ └── CustomUserDetailsService.java │ │ ├── controller/ │ │ │ ├── AuthController.java │ │ │ ├── DashboardController.java │ │ │ ├── PatientController.java │ │ │ ├── MedecinController.java │ │ │ ├── RendezVousController.java │ │ │ ├── OrdonnanceController.java │ │ │ ├── PatientEspaceController.java │ │ │ └── RdvConfirmationController.java │ │ ├── entity/ │ │ │ ├── Patient.java │ │ │ ├── Medecin.java │ │ │ ├── RendezVous.java │ │ │ ├── Ordonnance.java │ │ │ ├── LigneMedicament.java │ │ │ └── Utilisateur.java │ │ ├── repository/ │ │ │ ├── PatientRepository.java │ │ │ ├── MedecinRepository.java │ │ │ ├── RendezVousRepository.java │ │ │ ├── OrdonnanceRepository.java │ │ │ └── UtilisateurRepository.java │ │ └── service/ │ │ ├── PatientService.java │ │ ├── MedecinService.java │ │ ├── RendezVousService.java │ │ ├── OrdonnanceService.java │ │ ├── EmailService.java │ │ ├── PhotoService.java │ │ └── RappelScheduler.java │ └── resources/ │ ├── db/migration/ │ │ ├── V1__init.sql │ │ ├── V2__data.sql │ │ ├── V3__utilisateurs.sql │ │ ├── V4__token_rappel.sql │ │ └── V5__photo_patient.sql │ ├── static/photos/ │ └── templates/ │ ├── accueil.html │ ├── login.html │ ├── register.html │ ├── dashboard.html │ ├── espace-patient.html │ ├── rdv-reponse.html │ ├── patients/ │ ├── medecins/ │ ├── rendezvous/ │ ├── ordonnances/ │ └── patient/

---

## ✅ Fonctionnalités réalisées

### Semaine 1 — Setup & Configuration, CRUD de base
- [x] Création du projet Spring Boot 3
- [x] Configuration MySQL + Flyway
- [x] Entités JPA (Patient, Medecin, RendezVous, Ordonnance, LigneMedicament, Utilisateur)
- [x] Spring Security multi-rôles
- [x] Page d'accueil publique professionnelle
- [x] CRUD complet Patients (liste, ajout, modification, suppression, fiche)
- [x] CRUD complet Médecins (liste, ajout, modification)
- [x] Recherche patients par nom, CIN, téléphone
- [x] Recherche médecins par nom, spécialité

### Semaine 2 — Rendez-vous & Ordonnances + Fonctionnalités avancées
- [x] Gestion des Rendez-vous (création, modification, annulation)
- [x] Vérification automatique de non-chevauchement des RDV
- [x] Gestion des Ordonnances avec lignes médicaments
- [x] Tableau de bord avec statistiques en temps réel
- [x] Espace patient (RDV, ordonnances, profil)
- [x] Inscription patient avec création automatique de fiche
- [x] Upload et affichage de photo patient
- [x] Notifications email automatiques 15 min avant RDV
- [x] Confirmation/Annulation RDV par email avec token sécurisé
- [x] Scheduler automatique (vérifie chaque minute)
---
## 🗄️ Modèle de données
Patient ──────┐ ├──── RendezVous ──── Ordonnance ──── LigneMedicament Medecin ──────┘ Utilisateur (compte login lié au Patient)
## 📧 Système de notification email
Patient prend RDV → entre son email ↓ Scheduler vérifie chaque minute ↓ 15 min avant le RDV → email envoyé automatiquement ↓ Email contient 2 boutons : ✅ Confirmer → statut = CONFIRME ❌ Annuler → statut = ANNULE ↓ Token unique sécurisé (pas besoin de se connecter)
---
## 📅 Planning réalisé

| Semaine | Objectifs | Statut |
|---------|-----------|--------|
| S1 | Setup • Entités • Flyway • Security | ✅ Terminé | Rendez-vous • Ordonnances • Dashboard | ✅ Terminé |
| S2 | CRUD Patients et Médecins | ✅ Terminé | Rendez-vous • Ordonnances • Dashboard | Email • Photo • Espace patient | ✅ Terminé |

---

## 📝 Notes importantes

- Migrations BDD via **Flyway** (pas de `ddl-auto=create`)
- Non-chevauchement RDV géré dans la **couche service**
- Mots de passe cryptés avec **BCrypt**
- Toutes les dates utilisent **java.time**
- Photos stockées dans `static/photos/`
- Token unique par RDV pour confirmation sans login
---
*FST — Faculté des Sciences de Tunis — Master STR — 2025/2026*
