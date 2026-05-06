package com.fst.cabinet.entity;

import com.fst.cabinet.validation.ValidPassword;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "utilisateurs")
@Data
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    // ✅ Champ BD — stocke le hash BCrypt — pas de validation ici
    @Column(nullable = false)
    private String password;

    // ✅ Champ temporaire hors BD — pour le formulaire register uniquement
    @Transient
    @ValidPassword
    private String rawPassword;

    private String nomComplet;
    private String email;

    @Column(nullable = false)
    private String role;

    private boolean actif = true;
}