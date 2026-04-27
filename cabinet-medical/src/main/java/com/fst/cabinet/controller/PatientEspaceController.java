package com.fst.cabinet.controller;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.entity.Utilisateur;
import com.fst.cabinet.repository.PatientRepository;
import com.fst.cabinet.repository.UtilisateurRepository;
import com.fst.cabinet.service.OrdonnanceService;
import com.fst.cabinet.service.RendezVousService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientEspaceController {

    private final UtilisateurRepository utilisateurRepository;
    private final RendezVousService rendezVousService;
    private final OrdonnanceService ordonnanceService;
    private final PatientRepository patientRepository;

    public PatientEspaceController(
            UtilisateurRepository utilisateurRepository,
            RendezVousService rendezVousService,
            OrdonnanceService ordonnanceService,
            PatientRepository patientRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.rendezVousService = rendezVousService;
        this.ordonnanceService = ordonnanceService;
        this.patientRepository = patientRepository;
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
        // Trouver l'utilisateur connecté
        Utilisateur user = utilisateurRepository
            .findByUsername(auth.getName());
        // Trouver sa fiche patient par email
        Patient patient = patientRepository
            .findByEmail(user.getEmail());

        if (patient != null) {
            // Afficher SEULEMENT ses RDV
            model.addAttribute("rendezvous",
                rendezVousService
                    .getRDVParPatient(patient.getId()));
        } else {
            // Aucun RDV
            model.addAttribute("rendezvous",
                new java.util.ArrayList<>());
        }
        return "patient/mes-rendezvous";
    }

    // ===== MES ORDONNANCES =====
    @GetMapping("/mes-ordonnances")
    public String mesOrdonnances(Model model,
            Authentication auth) {
        // Trouver l'utilisateur connecté
        Utilisateur user = utilisateurRepository
            .findByUsername(auth.getName());
        // Trouver sa fiche patient par email
        Patient patient = patientRepository
            .findByEmail(user.getEmail());

        if (patient != null) {
            // Afficher SEULEMENT ses ordonnances
            model.addAttribute("ordonnances",
                ordonnanceService
                    .getOrdonnancesParPatient(
                        patient.getId()));
        } else {
            model.addAttribute("ordonnances",
                new java.util.ArrayList<>());
        }
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
        user.setNomComplet(userForm.getNomComplet());
        user.setEmail(userForm.getEmail());
        utilisateurRepository.save(user);
        return "redirect:/mon-profil?success";
    }
}