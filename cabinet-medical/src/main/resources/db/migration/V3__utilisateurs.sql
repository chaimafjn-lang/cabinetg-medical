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
    '$2a$10$kSal8QzKqmtiKVlpHvQR2ezIIpm2j7YXdre5T8OHZ/NAK9IR3l3n6',
    'Administrateur',
    'admin@cabinet.tn',
    'ADMIN',
    true
    
),
(
    'medecin',
    '$2a$10$9673WMdmCJraXL.RajClM.s8yKUgGFDqXsz7NFo2R1VKIX1qLQ4Mi',
    'Dr Ben Ali Mohamed',
    'benali@cabinet.tn',
    'MEDECIN',
    true
),
(
    'secretaire',
    '$2a$10$wagiWGMke4EP0lcE3htohubmVWulwCwa7FPg7ZtgGYuHf6xtYXrYu',
    'Secrétaire',
    'secretaire@cabinet.tn',
    'SECRETAIRE',
    true
);