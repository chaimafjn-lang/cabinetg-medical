package com.fst.cabinet.controller;

import com.fst.cabinet.entity.Medecin;
import com.fst.cabinet.service.MedecinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// @Controller gère les pages web
@Controller
// Toutes les URLs commencent par /medecins
@RequestMapping("/medecins")
public class MedecinController {

    // On injecte le service
    private final MedecinService medecinService;

    // Constructeur
    public MedecinController(MedecinService medecinService) {
        this.medecinService = medecinService;
    }

    // ===== LISTE DES MEDECINS =====
    // URL : /medecins
    @GetMapping
    public String liste(Model model,
                       @RequestParam(required = false) String recherche) {
        if (recherche != null && !recherche.isEmpty()) {
            model.addAttribute("medecins", medecinService.rechercher(recherche));
            model.addAttribute("recherche", recherche);
        } else {
            model.addAttribute("medecins", medecinService.getTousLesMedecins());
        }
        return "medecins/liste";
    }

    // ===== FORMULAIRE AJOUT =====
    // URL : /medecins/nouveau
    @GetMapping("/nouveau")
    public String nouveauFormulaire(Model model) {
        model.addAttribute("medecin", new Medecin());
        model.addAttribute("titre", "Ajouter un médecin");
        return "medecins/form";
    }

    // ===== FORMULAIRE MODIFICATION =====
    // URL : /medecins/modifier/1
    @GetMapping("/modifier/{id}")
    public String modifierFormulaire(@PathVariable Long id, Model model) {
        model.addAttribute("medecin", medecinService.getMedecinParId(id));
        model.addAttribute("titre", "Modifier un médecin");
        return "medecins/form";
    }

    // ===== SAUVEGARDER =====
    // URL : /medecins/sauvegarder (POST)
    @PostMapping("/sauvegarder")
    public String sauvegarder(@ModelAttribute Medecin medecin, Model model) {
        try {
            medecinService.sauvegarder(medecin);
            return "redirect:/medecins";
        } catch (RuntimeException e) {
            model.addAttribute("erreur", e.getMessage());
            model.addAttribute("medecin", medecin);
            model.addAttribute("titre", "Ajouter un médecin");
            return "medecins/form";
        }
    }

    // ===== SUPPRIMER =====
    // URL : /medecins/supprimer/1
    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        medecinService.supprimer(id);
        return "redirect:/medecins";
    }
}
