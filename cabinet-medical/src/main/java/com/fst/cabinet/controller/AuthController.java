package com.fst.cabinet.controller;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.entity.Utilisateur;
import com.fst.cabinet.repository.PatientRepository;
import com.fst.cabinet.repository.UtilisateurRepository;
import com.fst.cabinet.service.PhotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final PhotoService photoService;

    // ✅ AJOUT — gère / et /accueil
    @GetMapping({"/", "/accueil"})
    public String accueil() {
        return "accueil";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
            BindingResult bindingResult,
            @RequestParam String cin,
            @RequestParam String telephone,
            @RequestParam(required = false) MultipartFile photo,
            Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (utilisateurRepository.findByUsername(utilisateur.getUsername()) != null) {
            model.addAttribute("erreurUsername", "Ce nom d'utilisateur est déjà utilisé");
            return "register";
        }

        String motDePasse = utilisateur.getRawPassword();
        String username   = utilisateur.getUsername().toLowerCase();

        if (motDePasse.toLowerCase().contains(username)) {
            model.addAttribute("erreurPassword",
                "Le mot de passe ne doit pas contenir votre nom d'utilisateur");
            return "register";
        }

        if (utilisateur.getEmail() != null && utilisateur.getEmail().contains("@")) {
            String emailLocal = utilisateur.getEmail().split("@")[0].toLowerCase();
            if (motDePasse.toLowerCase().contains(emailLocal)) {
                model.addAttribute("erreurPassword",
                    "Le mot de passe ne doit pas contenir votre nom écrit dans l'email");
                return "register";
            }
        }

        utilisateur.setPassword(passwordEncoder.encode(motDePasse));
        utilisateur.setRole("PATIENT");
        utilisateurRepository.save(utilisateur);

        Patient patient = new Patient();
        String[] parts = utilisateur.getNomComplet() != null
            ? utilisateur.getNomComplet().split(" ", 2)
            : new String[]{"", ""};
        patient.setPrenom(parts[0]);
        patient.setNom(parts.length > 1 ? parts[1] : "");
        patient.setEmail(utilisateur.getEmail());
        patient.setCin(cin);
        patient.setTelephone(telephone);

        String nomPhoto = photoService.sauvegarderPhoto(photo);
        patient.setPhoto(nomPhoto);
        patientRepository.save(patient);

        return "redirect:/login?registered";
    }
}