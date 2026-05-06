🏥 Cabinet Médical — Spring Boot

FST Master STR 2025/2026
Équipe : Chaima · Nawres · Faiza
Encadrant : M. Nader Belhadj


📋 Description
Application web de gestion d'un cabinet médical développée avec Spring Boot, Thymeleaf, Spring Security et MySQL. Elle permet la gestion des patients, médecins, rendez-vous et ordonnances avec un système d'authentification sécurisé par rôles.

🛠️ Technologies utilisées
TechnologieVersionJava17+Spring Boot3.xSpring Security6.xThymeleaf3.xMySQL8.xFlywaymigrations SQLLombokannotationsMavenbuild

⚙️ Configuration
application.properties
propertiesspring.application.name=cabinet-medical
spring.datasource.url=jdbc:mysql://localhost:3306/cabinet_medical?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

spring.thymeleaf.cache=false
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.jdbc.time_zone=Africa/Tunis

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=TON_EMAIL@gmail.com
spring.mail.password=TON_MOT_DE_PASSE_APPLICATION
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

app.url=http://localhost:8080

🗄️ Structure du projet
src/
├── main/
│   ├── java/com/fst/cabinet/
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   └── CustomUserDetailsService.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── DashboardController.java
│   │   │   ├── PatientController.java
│   │   │   ├── MedecinController.java
│   │   │   ├── RendezVousController.java
│   │   │   ├── OrdonnanceController.java
│   │   │   ├── PatientEspaceController.java
│   │   │   └── RdvConfirmationController.java
│   │   ├── entity/
│   │   │   ├── Patient.java
│   │   │   ├── Medecin.java
│   │   │   ├── RendezVous.java
│   │   │   ├── Ordonnance.java
│   │   │   ├── LigneMedicament.java
│   │   │   └── Utilisateur.java
│   │   ├── repository/
│   │   │   ├── PatientRepository.java
│   │   │   ├── MedecinRepository.java
│   │   │   ├── RendezVousRepository.java
│   │   │   ├── OrdonnanceRepository.java
│   │   │   └── UtilisateurRepository.java
│   │   ├── service/
│   │   │   ├── PatientService.java
│   │   │   ├── MedecinService.java
│   │   │   ├── RendezVousService.java
│   │   │   ├── OrdonnanceService.java
│   │   │   ├── EmailService.java
│   │   │   ├── PhotoService.java
│   │   │   └── RappelScheduler.java
│   │   └── validation/                  ← NOUVEAU
│   │       ├── ValidPassword.java
│   │       └── PasswordValidator.java
│   └── resources/
│       ├── db/migration/
│       │   ├── V1__init.sql
│       │   ├── V2__data.sql
│       │   ├── V3__utilisateurs.sql
│       │   ├── V4__token_rappel.sql
│       │   ├── V5__photo_patient.sql
│       │   └── V6__reset_passwords.sql  ← NOUVEAU
│       ├── static/photos/
│       └── templates/

🔐 Sécurité — Modifications apportées
Objectif
Conformément aux recommandations de l'encadrant, les mots de passe sont désormais :

Stockés en base de données (encodés BCrypt) et non plus dans SecurityConfig.java
Soumis à des règles de complexité obligatoires lors de l'inscription

Règles de complexité du mot de passe
RègleDétailLongueur minimale8 caractèresMajusculeAu moins 1 lettre majuscule (A-Z)MinusculeAu moins 1 lettre minuscule (a-z)ChiffreAu moins 1 chiffre (0-9)Caractère spécialAu moins 1 parmi @$!%*?&#+_-Pas le nom d'utilisateurLe mot de passe ne doit pas contenir le usernamePas le nom de l'emailLe mot de passe ne doit pas contenir la partie locale de l'email
Exemple valide : Cabinet@2024
Exemples invalides : password, admin123, Admin2024 (pas de spécial)

Fichiers modifiés / créés
1. validation/ValidPassword.java ← NOUVEAU
Annotation personnalisée @ValidPassword utilisée sur le champ rawPassword de l'entité Utilisateur.
2. validation/PasswordValidator.java ← NOUVEAU
Logique de validation par expression régulière :
^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&#+\-_]).{8,}$
3. entity/Utilisateur.java ← MODIFIÉ

Champ password : stocke le hash BCrypt en base — aucune validation dessus
Champ rawPassword (@Transient) : mot de passe en clair saisi dans le formulaire — annoté @ValidPassword

java@Column(nullable = false)
private String password;        // hash BCrypt en base

@Transient
@ValidPassword
private String rawPassword;     // mot de passe en clair (formulaire uniquement)
4. controller/AuthController.java ← MODIFIÉ

Gère /, /accueil, /login, /register
Valide rawPassword avant encodage
Vérifie que le mot de passe ne contient pas le username ni la partie locale de l'email
Encode rawPassword → stocke dans password via BCryptPasswordEncoder

5. config/SecurityConfig.java ← MODIFIÉ

Suppression des utilisateurs hardcodés
Authentification via CustomUserDetailsService (lecture depuis la base)
Bean DaoAuthenticationProvider configuré avec BCryptPasswordEncoder

6. db/migration/V6__reset_passwords.sql ← NOUVEAU
Script Flyway pour mettre à jour les hash des comptes existants avec un mot de passe respectant les nouvelles règles.

👥 Comptes de test
UsernameMot de passeRôleadminAdmin@2024ADMINmedecinAdmin@2024MEDECINsecretaireAdmin@2024SECRETAIRE

Les patients créent leur compte via /register avec un mot de passe respectant les règles de complexité.


🔑 Génération de hash BCrypt
Pour générer un hash BCrypt pour un nouveau mot de passe :
java// Lancer cette classe main une seule fois puis la supprimer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePasswords {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("VotreMotDePasse@1"));
    }
}
Puis mettre à jour en base :
sqlUPDATE utilisateurs SET password = 'HASH_GENERE' WHERE username = 'nom_utilisateur';

🗂️ Scripts Flyway
ScriptDescriptionV1__init.sqlCréation des tables principalesV2__data.sqlDonnées de test (médecins, patients)V3__utilisateurs.sqlTable utilisateurs + comptes par défautV4__token_rappel.sqlColonnes token et rappel emailV5__photo_patient.sqlColonne photo dans patientsV6__reset_passwords.sqlMise à jour des hash BCrypt (nouveaux mots de passe complexes)

🚀 Lancement
bash# Cloner le projet
git clone https://github.com/votre-repo/cabinet-medical.git
cd cabinet-medical

# Lancer avec Maven
mvn spring-boot:run
Accéder à l'application : http://localhost:8080
localhost
