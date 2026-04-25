package com.fst.cabinet.controller;

import com.fst.cabinet.entity.Utilisateur;
import com.fst.cabinet.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ===== PAGE ACCUEIL PUBLIQUE =====
    @GetMapping({"/", "/accueil"})
    public String accueil() {
        return "accueil";
    }

    // ===== PAGE LOGIN =====
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // ===== PAGE REGISTER =====
    @GetMapping("/register")
    public String registerFormulaire(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "register";
    }

    // ===== SAUVEGARDER INSCRIPTION =====
    @PostMapping("/register")
    public String register(
            @ModelAttribute Utilisateur utilisateur,
            Model model) {

        // Vérifier si le username existe déjà
        if (utilisateurRepository
                .findByUsername(utilisateur.getUsername()) 
                != null) {
            model.addAttribute("erreur",
                "Ce nom d'utilisateur existe déjà !");
            return "register";
        }

        // Crypter le mot de passe avant de sauvegarder
        utilisateur.setPassword(
            passwordEncoder.encode(
                utilisateur.getPassword()));

        // Le rôle est toujours PATIENT pour l'inscription
        utilisateur.setRole("PATIENT");
        utilisateur.setActif(true);

        utilisateurRepository.save(utilisateur);

        // Rediriger vers login après inscription
        return "redirect:/login?registered";
    }

    // ===== DASHBOARD =====
   // @GetMapping("/dashboard")
  //  public String dashboard() {
        //return "dashboard";
   // }
}
