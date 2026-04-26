package com.fst.cabinet.controller;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.entity.Utilisateur;
import com.fst.cabinet.repository.PatientRepository;
import com.fst.cabinet.repository.UtilisateurRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    public AuthController(
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder,
            PatientRepository patientRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.patientRepository = patientRepository;
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
        model.addAttribute("utilisateur", 
            new Utilisateur());
        return "register";
    }

    // ===== SAUVEGARDER INSCRIPTION =====
    @PostMapping("/register")
    public String register(
            @ModelAttribute Utilisateur utilisateur,
            @RequestParam String cin,
            @RequestParam String telephone,
            Model model) {

        // Vérifier si le username existe déjà
        if (utilisateurRepository
                .findByUsername(
                    utilisateur.getUsername()) != null) {
            model.addAttribute("erreur",
                "Ce nom d'utilisateur existe déjà !");
            return "register";
        }

        // Vérifier si le CIN existe déjà
        if (patientRepository.findByCin(cin) != null) {
            model.addAttribute("erreur",
                "Ce CIN existe déjà !");
            return "register";
        }

        // Crypter le mot de passe
        utilisateur.setPassword(
            passwordEncoder.encode(
                utilisateur.getPassword()));

        // Rôle PATIENT
        utilisateur.setRole("PATIENT");
        utilisateur.setActif(true);

        // Sauvegarder le compte
        utilisateurRepository.save(utilisateur);

        // Créer automatiquement la fiche Patient
        Patient patient = new Patient();

        // Séparer nom et prénom depuis nomComplet
        String nomComplet = utilisateur.getNomComplet();
        if (nomComplet != null && 
                nomComplet.contains(" ")) {
            String[] parts = nomComplet.split(" ", 2);
            patient.setPrenom(parts[0]);
            patient.setNom(parts[1]);
        } else {
            patient.setNom(
                nomComplet != null ? nomComplet : "");
            patient.setPrenom("");
        }

        patient.setEmail(utilisateur.getEmail());
        patient.setCin(cin);
        patient.setTelephone(telephone);
        patient.setDateCreation(LocalDateTime.now());

        // Sauvegarder la fiche patient
        patientRepository.save(patient);

        return "redirect:/login?registered";
    }
}

