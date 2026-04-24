-- Table des utilisateurs
CREATE TABLE utilisateurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nom_complet VARCHAR(100),
    email VARCHAR(100),
    role VARCHAR(20) NOT NULL,
    actif BOOLEAN DEFAULT TRUE
);

-- Insertion des utilisateurs par défaut
-- Les mots de passe sont cryptés avec BCrypt
-- admin123
INSERT INTO utilisateurs 
    (username, password, nom_complet, email, role, actif)
VALUES
(
    'admin',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH',
    'Administrateur',
    'admin@cabinet.tn',
    'ADMIN',
    true
),
(
    'medecin1',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH',
    'Dr Ben Ali Mohamed',
    'benali@cabinet.tn',
    'MEDECIN',
    true
),
(
    'secretaire1',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH',
    'Secrétaire',
    'secretaire@cabinet.tn',
    'SECRETAIRE',
    true
);
