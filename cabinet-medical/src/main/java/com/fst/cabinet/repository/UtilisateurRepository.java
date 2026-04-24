package com.fst.cabinet.repository;

import com.fst.cabinet.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository 
    extends JpaRepository<Utilisateur, Long> {

    // Trouver un utilisateur par son username
    // Spring Security utilise cette méthode pour le login
    Utilisateur findByUsername(String username);
}
