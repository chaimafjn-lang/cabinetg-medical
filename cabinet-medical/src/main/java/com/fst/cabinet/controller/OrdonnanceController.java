package com.fst.cabinet.controller;

import com.fst.cabinet.entity.Ordonnance;
import com.fst.cabinet.entity.LigneMedicament;
import com.fst.cabinet.service.OrdonnanceService;
import com.fst.cabinet.service.RendezVousService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@Controller
@RequestMapping("/ordonnances")
public class OrdonnanceController {

    private final OrdonnanceService ordonnanceService;
    private final RendezVousService rendezVousService;

    public OrdonnanceController(
            OrdonnanceService ordonnanceService,
            RendezVousService rendezVousService) {
        this.ordonnanceService = ordonnanceService;
        this.rendezVousService = rendezVousService;
    }

    // ===== LISTE DES ORDONNANCES =====
    @GetMapping
    public String liste(Model model) {
        model.addAttribute("ordonnances",
            ordonnanceService.getToutesLesOrdonnances());
        return "ordonnances/liste";
    }

    // ===== FORMULAIRE NOUVELLE ORDONNANCE =====
    @GetMapping("/nouvelle/{rdvId}")
    public String nouveauFormulaire(
            @PathVariable Long rdvId, Model model) {
        Ordonnance ordonnance = new Ordonnance();
        // Lier l'ordonnance au rendez-vous
        ordonnance.setRendezVous(
            rendezVousService.getRDVParId(rdvId));
        // Ajouter une ligne médicament vide par défaut
        ordonnance.setMedicaments(new ArrayList<>());
        ordonnance.getMedicaments().add(new LigneMedicament());
        model.addAttribute("ordonnance", ordonnance);
        return "ordonnances/form";
    }

    // ===== AFFICHER UNE ORDONNANCE =====
    @GetMapping("/{id}")
    public String afficher(@PathVariable Long id, Model model) {
        model.addAttribute("ordonnance",
            ordonnanceService.getOrdonnanceParId(id));
        return "ordonnances/detail";
    }

    // ===== SAUVEGARDER ORDONNANCE =====
    @PostMapping("/sauvegarder")
    public String sauvegarder(
            @ModelAttribute Ordonnance ordonnance) {
        ordonnanceService.sauvegarder(ordonnance);
        return "redirect:/ordonnances";
    }

    // ===== SUPPRIMER ORDONNANCE =====
    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        ordonnanceService.supprimer(id);
        return "redirect:/ordonnances";
    }
}
