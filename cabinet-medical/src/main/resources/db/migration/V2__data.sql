-- Médecins
INSERT INTO medecins (nom, prenom, specialite, numero_ordre, telephone, email, actif)
VALUES
('Ben Ali', 'Mohamed', 'Cardiologie', 'ORD001', '71000001', 'benali@cabinet.tn', true),
('Trabelsi', 'Sonia', 'Pédiatrie', 'ORD002', '71000002', 'trabelsi@cabinet.tn', true),
('Mansouri', 'Karim', 'Généraliste', 'ORD003', '71000003', 'mansouri@cabinet.tn', true);

-- Patients
INSERT INTO patients (cin, nom, prenom, date_naissance, telephone, email, antecedents)
VALUES
('12345678', 'Hammami', 'Leila', '1990-05-15', '22000001', 'leila@mail.tn', 'Diabète type 2'),
('87654321', 'Gharbi', 'Youssef', '1985-03-20', '22000002', 'youssef@mail.tn', 'Hypertension'),
('11223344', 'Bouazizi', 'Amira', '2000-11-10', '22000003', 'amira@mail.tn', 'Aucun');