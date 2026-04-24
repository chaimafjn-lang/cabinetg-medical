CREATE TABLE utilisateurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nom_complet VARCHAR(100),
    email VARCHAR(100),
    role VARCHAR(20) NOT NULL,
    actif BOOLEAN DEFAULT TRUE
);

INSERT INTO utilisateurs 
    (username, password, nom_complet, email, role, actif)
VALUES
(
    'admin',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    'Administrateur',
    'admin@cabinet.tn',
    'ADMIN',
    true
),
(
    'medecin',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    'Dr Ben Ali Mohamed',
    'benali@cabinet.tn',
    'MEDECIN',
    true
),
(
    'secretaire',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    'Secrétaire',
    'secretaire@cabinet.tn',
    'SECRETAIRE',
    true
);