package com.fst.cabinet.entity;

import jakarta.persistence.*;
import lombok.*;

// Cette classe représente un compte utilisateur
@Entity
@Table(name = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom d'utilisateur unique pour la connexion
    @Column(unique = true, nullable = false)
    private String username;

    // Mot de passe crypté
    @Column(nullable = false)
    private String password;

    // Nom complet affiché
    private String nomComplet;

    private String email;

    // Rôle : ADMIN, MEDECIN, SECRETAIRE, PATIENT
    @Column(nullable = false)
    private String role;

    // Compte actif ou non
    private boolean actif = true;
}
