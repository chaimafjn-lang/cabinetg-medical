package com.fst.cabinet.controller;

import com.fst.cabinet.service.PatientService;
import com.fst.cabinet.service.MedecinService;
import com.fst.cabinet.service.RendezVousService;
import com.fst.cabinet.service.OrdonnanceService;
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
    public String dashboard(Model model) {

        // Nombre total de patients
        model.addAttribute("totalPatients",
            patientService.getTousLesPatients().size());

        // Nombre total de médecins actifs
        model.addAttribute("totalMedecins",
            medecinService.getMedecinsActifs().size());

        // RDV d'aujourd'hui
        model.addAttribute("rdvAujourdhui",
            rendezVousService.getRDVAujourdhui());

        // Nombre total de RDV
        model.addAttribute("totalRDV",
            rendezVousService.getTousLesRDV().size());

        // Nombre total ordonnances
        model.addAttribute("totalOrdonnances",
            ordonnanceService.getToutesLesOrdonnances().size());

        return "dashboard";
    }
}
