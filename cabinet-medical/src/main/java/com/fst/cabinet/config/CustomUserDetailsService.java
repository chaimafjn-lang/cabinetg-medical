package com.fst.cabinet.config;

import com.fst.cabinet.entity.Utilisateur;
import com.fst.cabinet.repository.UtilisateurRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

// Cette classe dit à Spring Security comment
// chercher un utilisateur dans la base de données
@Service
public class CustomUserDetailsService 
    implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    public CustomUserDetailsService(
            UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Chercher l'utilisateur dans la base
        Utilisateur user = utilisateurRepository
            .findByUsername(username);

        // Si pas trouvé → erreur
        if (user == null) {
            throw new UsernameNotFoundException(
                "Utilisateur non trouvé : " + username);
        }

        // Retourner l'utilisateur avec son rôle
        return new org.springframework.security.core
            .userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(
                    "ROLE_" + user.getRole()))
            );
    }
}
