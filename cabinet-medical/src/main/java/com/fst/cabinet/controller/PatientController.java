package com.fst.cabinet.controller;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// @Controller dit que cette classe gère les pages web
@Controller
// Toutes les URLs de cette classe commencent par /patients
@RequestMapping("/patients")
public class PatientController {

    // On injecte le service pour accéder aux données
    private final PatientService patientService;

    // Constructeur
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // ===== LISTE DES PATIENTS =====
    // URL : /patients
    @GetMapping
    public String liste(Model model, 
                       @RequestParam(required = false) String recherche) {
        // Si recherche est vide → afficher tous les patients
        // Sinon → afficher les résultats de la recherche
        if (recherche != null && !recherche.isEmpty()) {
            model.addAttribute("patients", patientService.rechercher(recherche));
            model.addAttribute("recherche", recherche);
        } else {
            model.addAttribute("patients", patientService.getTousLesPatients());
        }
        return "patients/liste";
    }

    // ===== AFFICHER FORMULAIRE AJOUT =====
    // URL : /patients/nouveau
    @GetMapping("/nouveau")
    public String nouveauFormulaire(Model model) {
        // On envoie un patient vide au formulaire
        model.addAttribute("patient", new Patient());
        model.addAttribute("titre", "Ajouter un patient");
        return "patients/form";
    }

    // ===== AFFICHER FORMULAIRE MODIFICATION =====
    // URL : /patients/modifier/1
    @GetMapping("/modifier/{id}")
    public String modifierFormulaire(@PathVariable Long id, Model model) {
        // On récupère le patient existant et on l'envoie au formulaire
        model.addAttribute("patient", patientService.getPatientParId(id));
        model.addAttribute("titre", "Modifier un patient");
        return "patients/form";
    }

    // ===== SAUVEGARDER (AJOUT OU MODIFICATION) =====
    // URL : /patients/sauvegarder (POST)
    @PostMapping("/sauvegarder")
    public String sauvegarder(@ModelAttribute Patient patient, Model model) {
        try {
            patientService.sauvegarder(patient);
            // Après sauvegarde → retourner à la liste
            return "redirect:/patients";
        } catch (RuntimeException e) {
            // Si erreur (ex: CIN déjà existant) → rester sur le formulaire
            model.addAttribute("erreur", e.getMessage());
            model.addAttribute("patient", patient);
            model.addAttribute("titre", "Ajouter un patient");
            return "patients/form";
        }
    }

    // ===== SUPPRIMER UN PATIENT =====
    // URL : /patients/supprimer/1
    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        patientService.supprimer(id);
        // Après suppression → retourner à la liste
        return "redirect:/patients";
    }

    // ===== FICHE PATIENT =====
    // URL : /patients/fiche/1
    @GetMapping("/fiche/{id}")
    public String fiche(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatientParId(id));
        return "patients/fiche";
    }
}
