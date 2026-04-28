package com.fst.cabinet.controller;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.entity.Utilisateur;
import com.fst.cabinet.repository.PatientRepository;
import com.fst.cabinet.repository.UtilisateurRepository;
import com.fst.cabinet.service.PhotoService;
import org.springframework.security.crypto.password
    .PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Controller
public class AuthController {

    private final UtilisateurRepository
        utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final PhotoService photoService;

    public AuthController(
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder,
            PatientRepository patientRepository,
            PhotoService photoService) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.patientRepository = patientRepository;
        this.photoService = photoService;
    }

    // ===== PAGE ACCUEIL =====
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
    // multipart/form-data pour la photo
    @PostMapping("/register")
    public String register(
            @ModelAttribute Utilisateur utilisateur,
            @RequestParam String cin,
            @RequestParam String telephone,
            @RequestParam(required = false)
                MultipartFile photo,
            Model model) {

        // Vérifier username unique
        if (utilisateurRepository
                .findByUsername(
                    utilisateur.getUsername()) != null) {
            model.addAttribute("erreur",
                "Ce nom d'utilisateur existe déjà !");
            return "register";
        }

        // Vérifier CIN unique
        if (patientRepository.findByCin(cin) != null) {
            model.addAttribute("erreur",
                "Ce CIN existe déjà !");
            return "register";
        }

        // Crypter le mot de passe
        utilisateur.setPassword(
            passwordEncoder.encode(
                utilisateur.getPassword()));
        utilisateur.setRole("PATIENT");
        utilisateur.setActif(true);
        utilisateurRepository.save(utilisateur);

        // Créer fiche Patient
        Patient patient = new Patient();
        String nomComplet = utilisateur.getNomComplet();
        if (nomComplet != null &&
                nomComplet.contains(" ")) {
            String[] parts = nomComplet.split(" ", 2);
            patient.setPrenom(parts[0]);
            patient.setNom(parts[1]);
        } else {
            patient.setNom(nomComplet != null ?
                nomComplet : "");
            patient.setPrenom("");
        }
        patient.setEmail(utilisateur.getEmail());
        patient.setCin(cin);
        patient.setTelephone(telephone);
        patient.setDateCreation(LocalDateTime.now());

        // Sauvegarder la photo
        try {
            String nomPhoto = photoService
                .sauvegarderPhoto(photo);
            patient.setPhoto(nomPhoto);
        } catch (Exception e) {
            // Si erreur photo → photo par défaut
            patient.setPhoto("default.jpg");
            model.addAttribute("avertissement",
                "Photo non sauvegardée : " 
                + e.getMessage());
        }

        patientRepository.save(patient);

        return "redirect:/login?registered";
    }
}

