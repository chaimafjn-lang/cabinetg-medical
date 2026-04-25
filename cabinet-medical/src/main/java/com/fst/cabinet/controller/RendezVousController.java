package com.fst.cabinet.controller;

import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.service.RendezVousService;
import com.fst.cabinet.service.PatientService;
import com.fst.cabinet.service.MedecinService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rendezvous")
public class RendezVousController {

    private final RendezVousService rendezVousService;
    private final PatientService patientService;
    private final MedecinService medecinService;

    public RendezVousController(
            RendezVousService rendezVousService,
            PatientService patientService,
            MedecinService medecinService) {
        this.rendezVousService = rendezVousService;
        this.patientService = patientService;
        this.medecinService = medecinService;
    }

    // LISTE
    @GetMapping
    public String liste(Model model) {
        model.addAttribute("rendezvous",
            rendezVousService.getTousLesRDV());
        return "rendezvous/liste";
    }

    // NOUVEAU RDV
    @GetMapping("/nouveau")
    public String nouveauFormulaire(Model model) {
        model.addAttribute("rdv", new RendezVous());
        model.addAttribute("patients",
            patientService.getTousLesPatients());
        model.addAttribute("medecins",
            medecinService.getMedecinsActifs());
        model.addAttribute("titre", "Nouveau Rendez-vous");
        return "rendezvous/form";
    }

    // MODIFIER RDV
    @GetMapping("/modifier/{id}")
    public String modifierFormulaire(
            @PathVariable Long id, Model model) {
        model.addAttribute("rdv",
            rendezVousService.getRDVParId(id));
        model.addAttribute("patients",
            patientService.getTousLesPatients());
        model.addAttribute("medecins",
            medecinService.getMedecinsActifs());
        model.addAttribute("titre", "Modifier Rendez-vous");
        return "rendezvous/form";
    }

    // SAUVEGARDER
    @PostMapping("/sauvegarder")
    public String sauvegarder(
            @ModelAttribute RendezVous rdv, Model model) {
        try {
            rendezVousService.sauvegarder(rdv);
            return "redirect:/rendezvous";
        } catch (RuntimeException e) {
            model.addAttribute("erreur", e.getMessage());
            model.addAttribute("rdv", rdv);
            model.addAttribute("patients",
                patientService.getTousLesPatients());
            model.addAttribute("medecins",
                medecinService.getMedecinsActifs());
            model.addAttribute("titre", "Nouveau Rendez-vous");
            return "rendezvous/form";
        }
    }

    // ANNULER
    @GetMapping("/annuler/{id}")
    public String annuler(@PathVariable Long id) {
        rendezVousService.annuler(id);
        return "redirect:/rendezvous";
    }

    // SUPPRIMER
    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        rendezVousService.supprimer(id);
        return "redirect:/rendezvous";
    }
}