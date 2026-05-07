🏥 FaNaCha TrioCare — Spring Boot
> Mini-Projet Spring Boot — FST Master STR 2025/2026  
> Encadrant : M. Nader Belhadj — Software Ing. | Chercheur IA
## 👥 Équipe de développement
| Nom | Rôle principal |
| **Chaima** | | **Nawres** | | **Faiza** | 
## 📋 Description
Application web complète de gestion d'un cabinet médical développée avec Spring Boot 3. Elle permet de gérer les patients, les médecins, les rendez-vous et les ordonnances avec un système d'authentification multi-rôles, une validation avancée des mots de passe et des notifications automatiques par email.
## 🛠️ Technologies utilisées
| Couche | Technologie |
|--------|-------------|
| Backend | Spring Boot 3.2 · Spring MVC · Spring Data JPA |
| Frontend | Thymeleaf 3 · Bootstrap 5 · Font Awesome · Playfair Display · DM Sans |
| Base de données | MySQL 8 · Flyway (migrations V1→V6) |
| Sécurité | Spring Security · BCrypt · Enum Role · Annotation @ValidPassword |
| Email | Spring Mail · Gmail SMTP · Scheduler @Scheduled |
| Upload | Spring Multipart · PhotoService (JPG/PNG) |
| Build | Maven · Lombok · Git · GitHub · Eclipse IDE |
## ⚙️ Prérequis
- Java 17
- Maven
- MySQL 8 ou XAMPP
- Eclipse IDE
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
spring.datasource.password=
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.thymeleaf.cache=false
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.jdbc.time_zone=Africa/Tunis
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=cabinetmedical@gmail.com
spring.mail.password=******
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
app.url=http://localhost:8080
```
### 3. Lancer XAMPP
- Démarrer **MySQL** dans XAMPP Control Panel
### 4. Lancer l'application
Dans Eclipse : clic droit → `Run As` → `Spring Boot App`
### 5. Accéder à l'application
http://localhost:8080
---
## 🔐 Comptes de test

| Utilisateur | Mot de passe | Rôle | Accès |
|-------------|--------------|------|-------|
| `admin` | `Admin@Cabinet1!` | ADMIN | Tout |
| `medecin1` | `Admin@Cabinet1!` | MEDECIN | Patients · RDV · Ordonnances |
| `secretaire1` | `Admin@Cabinet1!` | SECRETAIRE | Patients · Médecins · RDV |

> Les patients créent leur compte via `/register`
---
## 🔒 Sécurité — Mots de passe
### Problème résolu
Les mots de passe étaient **hardcodés** dans `SecurityConfig.java` — problème de sécurité majeur signalé par le professeur.
### Solution mise en place
#### 1. Annotation personnalisée `@ValidPassword`
validation/ ├── ValidPassword.java → Annotation personnalisée └── PasswordValidator.java → Logique de validation (Regex)
#### 2. Règles de validation
Le mot de passe doit respecter **toutes** ces règles :
| Règle | Description |
|-------|-------------|
| ✅ Longueur | Au moins **8 caractères** |
| ✅ Majuscule | Au moins **1 lettre majuscule** (A-Z) |
| ✅ Minuscule | Au moins **1 lettre minuscule** (a-z) |
| ✅ Chiffre | Au moins **1 chiffre** (0-9) |
| ✅ Spécial | Au moins **1 caractère spécial** (!@#$%...) |
| ✅ Username | Ne doit **pas contenir** le nom d'utilisateur |
| ✅ Email | Ne doit **pas contenir** la partie locale de l'email |
#### 3. Fichiers modifiés
- `Utilisateur.java` — Ajout du champ `@Transient rawPassword` pour séparer le mot de passe saisi du hash stocké
- `AuthController.java` — Vérification que le mot de passe ne contient pas le username ni la partie locale de l'email
- `SecurityConfig.java` — Suppression des users hardcodés, authentification via base de données avec `BCryptPasswordEncoder`
#### 4. Mots de passe en base de données
Les mots de passe sont stockés **chiffrés avec BCrypt** via le script `V3
-- Jamais en clair ! Toujours hashé avec BCrypt
---
## 🎨 Interface — Thème Rose
L'interface a été repensée avec une palette rose professionnelle :
| Couleur | Code | Usage |
|---------|------|-------|
| Rose principal | `#e8789a` | Boutons · Accents |
| Rose foncé | `#c95480` | Headers · Hover |
| Rose clair | `#fce8f0` | Backgrounds · Cards |
### Pages modifiées
- **`accueil.html`** — Architecture conservée, palette rose, typographie Playfair Display + DM Sans, images Unsplash
- **`login.html`** — Page 2 panneaux : gauche rose avec présentation du cabinet, droite blanche avec formulaire élégant + bouton œil + animations
---
## 📁 Structure du projet
src/ ├── main/ │ ├── java/com/fst/cabinet/ │ │ ├── config/ │ │ │ ├── SecurityConfig.java → Auth BDD + BCrypt (plus de hardcode) │ │ │ └── CustomUserDetailsService.java → Chargement utilisateurs depuis BDD │ │ ├── controller/ │ │ │ ├── AuthController.java → Login · Register · Accueil │ │ │ ├── AdminController.java → Création médecin/secrétaire (ADMIN) │ │ │ ├── DashboardController.java → Tableau de bord │ │ │ ├── PatientController.java → CRUD Patients │ │ │ ├── MedecinController.java → CRUD Médecins │ │ │ ├── RendezVousController.java → Gestion RDV │ │ │ ├── OrdonnanceController.java → Gestion Ordonnances │ │ │ ├── PatientEspaceController.java → Espace Patient │ │ │ └── RdvConfirmationController.java → Confirmation email │ │ ├── entity/ │ │ │ ├── Role.java → Enum : ADMIN·MEDECIN·SECRETAIRE·PATIENT │ │ │ ├── Utilisateur.java → Compte + rawPassword @Transient │ │ │ ├── Patient.java │ │ │ ├── Medecin.java │ │ │ ├── RendezVous.java → token + emailRappel + rappelEnvoye │ │ │ ├── Ordonnance.java │ │ │ └── LigneMedicament.java │ │ ├── repository/ │ │ │ ├── PatientRepository.java │ │ │ ├── MedecinRepository.java │ │ │ ├── RendezVousRepository.java → nativeQuery pour chevauchement │ │ │ ├── OrdonnanceRepository.java │ │ │ └── UtilisateurRepository.java │ │ ├── service/ │ │ │ ├── AuthenticationService.java → Logique register/login │ │ │ ├── PasswordValidationService.java → Règles mot de passe │ │ │ ├── PatientService.java │ │ │ ├── MedecinService.java │ │ │ ├── RendezVousService.java │ │ │ ├── OrdonnanceService.java │ │ │ ├── EmailService.java │ │ │ ├── PhotoService.java │ │ │ └── RappelScheduler.java │ │ └── validation/ │ │ ├── ValidPassword.java → Annotation @ValidPassword │ │ └── PasswordValidator.java → Regex validation │ └── resources/ │ ├── db/migration/ │ │ ├── V1__init.sql → Tables principales │ │ ├── V2__data.sql → Données de test │ │ ├── V3__utilisateurs.sql → Comptes par défaut │ │ ├── V4__token_rappel.sql → Colonnes email/token/rappel │ │ ├── V5__photo_patient.sql → Colonne photo │ │ └── V6__reset_passwords.sql → Passwords BCrypt en BDD │ ├── static/photos/ │ └── templates/ │ ├── accueil.html → Thème rose · Playfair Display │ ├── login.html → 2 panneaux · Bouton œil │ ├── register.html → Upload photo · Règles password │ ├── dashboard.html │ ├── espace-patient.html │ ├── rdv-reponse.html │ ├── admin/ │ │ └── form-medecin-user.html │ ├── patients/ │ ├── medecins/ │ ├── rendezvous/ │ ├── ordonnances/ │ └── patient/
---
## ✅ Fonctionnalités réalisées
### Setup & Modélisation
- [x] 6 Entités JPA + Role Enum
- [x] Scripts Flyway V1 & V2
- [x] Spring Security multi-rôles sans hardcode
- [x] Page d'accueil publique thème rose
- [x] CRUD complet Patients + recherche avancée
- [x] CRUD complet Médecins
- [x] Upload photo patient (JPG/PNG)
- [x] Validation CIN unique
### Rendez-vous & Ordonnances
- [x] Gestion RDV avec vérification non-chevauchement (nativeQuery)
- [x] Gestion Ordonnances + lignes médicaments
- [x] Dashboard avec statistiques temps réel
### Fonctionnalités Avancées
- [x] Espace patient autonome
- [x] Inscription avec photo + fiche patient automatique
- [x] Notifications email 15 min avant RDV
- [x] Confirmation/Annulation par token sécurisé
- [x] Annotation `@ValidPassword` personnalisée
- [x] Passwords BCrypt en BDD (V3)
- [x] AuthenticationService séparé
---

## 📧 Système de notification email
Patient prend RDV → saisit son email ↓ Scheduler vérifie chaque minute (@Scheduled) ↓ 15 min avant le RDV → email HTML envoyé ↓ Email contient 2 boutons avec token UUID : ✅ Confirmer → statut = CONFIRME ❌ Annuler → statut = ANNULE ↓ Mise à jour BDD automatique sans reconnexion
---
## 📝 Notes importantes

- Migrations BDD via **Flyway** — ne jamais modifier les anciens scripts
- Non-chevauchement RDV géré dans la **couche service** avec `nativeQuery`
- Mots de passe **BCrypt en BDD** — jamais en clair dans le code
- Role = **Enum Java** — pas de table séparée
- Auth = **AuthenticationService** — logique séparée du Controller
- Token **UUID unique** par RDV pour confirmation sans reconnexion
- Photos stockées dans `static/photos/`
---
*FST — Faculté des Sciences de Tunis — Master STR — 2025/2026*  
*Nawres .Faiza.Chaima · Encadrant : M. Nader Belhadj*
