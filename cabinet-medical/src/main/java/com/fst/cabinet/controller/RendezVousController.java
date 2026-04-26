package com.fst.cabinet.controller;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.entity.Utilisateur;
import com.fst.cabinet.repository.PatientRepository;
import com.fst.cabinet.repository.UtilisateurRepository;
import com.fst.cabinet.service.MedecinService;
import com.fst.cabinet.service.PatientService;
import com.fst.cabinet.service.RendezVousService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rendezvous")
public class RendezVousController {

    private final RendezVousService rendezVousService;
    private final PatientService patientService;
    private final MedecinService medecinService;
    private final PatientRepository patientRepository;
    private final UtilisateurRepository utilisateurRepository;

    public RendezVousController(
            RendezVousService rendezVousService,
            PatientService patientService,
            MedecinService medecinService,
            PatientRepository patientRepository,
            UtilisateurRepository utilisateurRepository) {
        this.rendezVousService = rendezVousService;
        this.patientService = patientService;
        this.medecinService = medecinService;
        this.patientRepository = patientRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    // ===== LISTE DES RDV =====
    @GetMapping
    public String liste(Model model) {
        model.addAttribute("rendezvous",
            rendezVousService.getTousLesRDV());
        return "rendezvous/liste";
    }

    // ===== FORMULAIRE NOUVEAU RDV =====
    @GetMapping("/nouveau")
    public String nouveauFormulaire(
            Model model, Authentication auth) {

        model.addAttribute("rdv", new RendezVous());
        model.addAttribute("medecins",
            medecinService.getMedecinsActifs());
        model.addAttribute("titre", "Nouveau Rendez-vous");

        // Récupérer le rôle
        String role = auth.getAuthorities()
            .iterator().next().getAuthority();

        if (role.equals("ROLE_PATIENT")) {
            // Trouver l'utilisateur connecté
            Utilisateur user = utilisateurRepository
                .findByUsername(auth.getName());
            // Trouver sa fiche patient par email
            Patient patient = patientRepository
                .findByEmail(user.getEmail());
            model.addAttribute("estPatient", true);
            model.addAttribute("patientId",
                patient != null ? patient.getId() : null);
            model.addAttribute("patientNom",
                patient != null ?
                patient.getNom() + " " + patient.getPrenom()
                : auth.getName());
        } else {
            // ADMIN/MEDECIN/SECRETAIRE
            model.addAttribute("estPatient", false);
            model.addAttribute("patients",
                patientService.getTousLesPatients());
        }

        return "rendezvous/form";
    }

    // ===== FORMULAIRE MODIFIER RDV =====
    @GetMapping("/modifier/{id}")
    public String modifierFormulaire(
            @PathVariable Long id,
            Model model,
            Authentication auth) {

        model.addAttribute("rdv",
            rendezVousService.getRDVParId(id));
        model.addAttribute("medecins",
            medecinService.getMedecinsActifs());
        model.addAttribute("titre", "Modifier Rendez-vous");

        String role = auth.getAuthorities()
            .iterator().next().getAuthority();

        if (role.equals("ROLE_PATIENT")) {
            Utilisateur user = utilisateurRepository
                .findByUsername(auth.getName());
            Patient patient = patientRepository
                .findByEmail(user.getEmail());
            model.addAttribute("estPatient", true);
            model.addAttribute("patientId",
                patient != null ? patient.getId() : null);
            model.addAttribute("patientNom",
                patient != null ?
                patient.getNom() + " " + patient.getPrenom()
                : auth.getName());
        } else {
            model.addAttribute("estPatient", false);
            model.addAttribute("patients",
                patientService.getTousLesPatients());
        }

        return "rendezvous/form";
    }

    // ===== SAUVEGARDER RDV =====
    @PostMapping("/sauvegarder")
    public String sauvegarder(
            @ModelAttribute RendezVous rdv,
            Model model,
            Authentication auth) {
        try {
            rendezVousService.sauvegarder(rdv);
            // Si patient → retour espace patient
            String role = auth.getAuthorities()
                .iterator().next().getAuthority();
            if (role.equals("ROLE_PATIENT")) {
                return "redirect:/mes-rendezvous";
            }
            return "redirect:/rendezvous";
        } catch (RuntimeException e) {
            model.addAttribute("erreur", e.getMessage());
            model.addAttribute("rdv", rdv);
            model.addAttribute("medecins",
                medecinService.getMedecinsActifs());
            model.addAttribute("titre", "Nouveau Rendez-vous");

            String role = auth.getAuthorities()
                .iterator().next().getAuthority();
            if (role.equals("ROLE_PATIENT")) {
                model.addAttribute("estPatient", true);
            } else {
                model.addAttribute("estPatient", false);
                model.addAttribute("patients",
                    patientService.getTousLesPatients());
            }
            return "rendezvous/form";
        }
    }

    // ===== ANNULER RDV =====
    @GetMapping("/annuler/{id}")
    public String annuler(@PathVariable Long id) {
        rendezVousService.annuler(id);
        return "redirect:/rendezvous";
    }

    // ===== SUPPRIMER RDV =====
    @GetMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        rendezVousService.supprimer(id);
        return "redirect:/rendezvous";
    }
}