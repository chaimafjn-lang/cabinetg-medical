-- 1. Tables de base (Indépendantes)
CREATE TABLE patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cin VARCHAR(20) UNIQUE NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE,
    telephone VARCHAR(20),
    email VARCHAR(100),
    antecedents TEXT,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE medecins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    specialite VARCHAR(100),
    numero_ordre VARCHAR(50) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    email VARCHAR(100),
    actif BOOLEAN DEFAULT TRUE
);

-- 2. Table Rendez-vous (Dépend de patients et medecins)
CREATE TABLE rendez_vous (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    medecin_id BIGINT NOT NULL,
    date_heure DATETIME NOT NULL,
    duree_minutes INT DEFAULT 30,
    motif VARCHAR(255),
    statut VARCHAR(20) DEFAULT 'PLANIFIE',
    FOREIGN KEY (patient_id) REFERENCES patients (id),
    FOREIGN KEY (medecin_id) REFERENCES medecins (id)
);

-- 3. Table Ordonnances (Dépend de rendez_vous)
CREATE TABLE ordonnances (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rendez_vous_id BIGINT,
    date_emission DATE DEFAULT (CURRENT_DATE),
    observations TEXT,
    FOREIGN KEY (rendez_vous_id) REFERENCES rendez_vous (id)
);

-- 4. Table Lignes Médicament (Dépend de ordonnances)
CREATE TABLE lignes_medicament (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ordonnance_id BIGINT,
    nom_medicament VARCHAR(200),
    posologie VARCHAR(200),
    duree VARCHAR(100),
    FOREIGN KEY (ordonnance_id) REFERENCES ordonnances (id)
);