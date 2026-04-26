package com.fst.cabinet.controller;

import com.fst.cabinet.service.PatientService;
import com.fst.cabinet.service.MedecinService;
import com.fst.cabinet.service.RendezVousService;
import com.fst.cabinet.service.OrdonnanceService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final PatientService patientService;
    private final MedecinService medecinService;
    private final RendezVousService rendezVousService;
    private final OrdonnanceService ordonnanceService;

    public DashboardController(
            PatientService patientService,
            MedecinService medecinService,
            RendezVousService rendezVousService,
            OrdonnanceService ordonnanceService) {
        this.patientService = patientService;
        this.medecinService = medecinService;
        this.rendezVousService = rendezVousService;
        this.ordonnanceService = ordonnanceService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, 
                           Authentication auth) {

        // Récupérer le rôle de l'utilisateur connecté
        String role = auth.getAuthorities()
            .iterator().next().getAuthority();

        // Si c'est un PATIENT → rediriger vers espace patient
        if (role.equals("ROLE_PATIENT")) {
            return "redirect:/espace-patient";
        }

        // Sinon afficher le dashboard normal
        model.addAttribute("totalPatients",
            patientService.getTousLesPatients().size());
        model.addAttribute("totalMedecins",
            medecinService.getMedecinsActifs().size());
        model.addAttribute("rdvAujourdhui",
            rendezVousService.getRDVAujourdhui());
        model.addAttribute("totalRDV",
            rendezVousService.getTousLesRDV().size());
        model.addAttribute("totalOrdonnances",
            ordonnanceService.getToutesLesOrdonnances().size());

        return "dashboard";
    }
}
