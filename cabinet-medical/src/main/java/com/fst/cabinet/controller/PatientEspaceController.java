package com.fst.cabinet.controller;

import com.fst.cabinet.entity.Utilisateur;
import com.fst.cabinet.repository.UtilisateurRepository;
import com.fst.cabinet.service.RendezVousService;
import com.fst.cabinet.service.OrdonnanceService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientEspaceController {

    private final UtilisateurRepository utilisateurRepository;
    private final RendezVousService rendezVousService;
    private final OrdonnanceService ordonnanceService;

    public PatientEspaceController(
            UtilisateurRepository utilisateurRepository,
            RendezVousService rendezVousService,
            OrdonnanceService ordonnanceService) {
        this.utilisateurRepository = utilisateurRepository;
        this.rendezVousService = rendezVousService;
        this.ordonnanceService = ordonnanceService;
    }

    // ===== ESPACE PATIENT =====
    @GetMapping("/espace-patient")
    public String espacePatient() {
        return "espace-patient";
    }

    // ===== MES RENDEZ-VOUS =====
    @GetMapping("/mes-rendezvous")
    public String mesRendezVous(Model model,
            Authentication auth) {
        // Récupérer l'utilisateur connecté
        Utilisateur user = utilisateurRepository
            .findByUsername(auth.getName());
        // Afficher tous les RDV pour l'instant
        model.addAttribute("rendezvous",
            rendezVousService.getTousLesRDV());
        return "patient/mes-rendezvous";
    }

    // ===== MES ORDONNANCES =====
    @GetMapping("/mes-ordonnances")
    public String mesOrdonnances(Model model,
            Authentication auth) {
        model.addAttribute("ordonnances",
            ordonnanceService.getToutesLesOrdonnances());
        return "patient/mes-ordonnances";
    }

    // ===== MON PROFIL =====
    @GetMapping("/mon-profil")
    public String monProfil(Model model,
            Authentication auth) {
        Utilisateur user = utilisateurRepository
            .findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "patient/mon-profil";
    }

    // ===== MODIFIER PROFIL =====
    @PostMapping("/mon-profil")
    public String modifierProfil(
            @ModelAttribute Utilisateur userForm,
            Authentication auth) {
        Utilisateur user = utilisateurRepository
            .findByUsername(auth.getName());
        // Mettre à jour seulement nom et email
        user.setNomComplet(userForm.getNomComplet());
        user.setEmail(userForm.getEmail());
        utilisateurRepository.save(user);
        return "redirect:/mon-profil?success";
    }
}
