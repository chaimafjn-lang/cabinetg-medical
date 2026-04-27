package com.fst.cabinet.controller;

import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.repository.RendezVousRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RdvConfirmationController {

    private final RendezVousRepository
        rendezVousRepository;

    public RdvConfirmationController(
            RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    // ===== CONFIRMER VIA EMAIL =====
    @GetMapping("/rdv/confirmer")
    public String confirmer(
            @RequestParam String token,
            Model model) {

        RendezVous rdv = rendezVousRepository
            .findByToken(token);

        if (rdv == null) {
            model.addAttribute("message",
                "❌ Lien invalide ou expiré !");
            model.addAttribute("succes", false);
            return "rdv-reponse";
        }

        rdv.setStatut(RendezVous.StatutRDV.CONFIRME);
        rendezVousRepository.save(rdv);

        model.addAttribute("message",
            "✅ Merci ! Votre présence est confirmée. " +
            "À tout à l'heure !");
        model.addAttribute("succes", true);
        model.addAttribute("rdv", rdv);
        return "rdv-reponse";
    }

    // ===== ANNULER VIA EMAIL =====
    @GetMapping("/rdv/annuler-email")
    public String annulerEmail(
            @RequestParam String token,
            Model model) {

        RendezVous rdv = rendezVousRepository
            .findByToken(token);

        if (rdv == null) {
            model.addAttribute("message",
                "❌ Lien invalide ou expiré !");
            model.addAttribute("succes", false);
            return "rdv-reponse";
        }

        rdv.setStatut(RendezVous.StatutRDV.ANNULE);
        rendezVousRepository.save(rdv);

        model.addAttribute("message",
            "❌ Votre RDV a été annulé. " +
            "N'hésitez pas à en reprendre un !");
        model.addAttribute("succes", false);
        model.addAttribute("rdv", rdv);
        return "rdv-reponse";
    }
}
