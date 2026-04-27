package com.fst.cabinet.service;

import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.repository.RendezVousRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class RappelScheduler {

    private final RendezVousRepository
        rendezVousRepository;
    private final EmailService emailService;

    public RappelScheduler(
            RendezVousRepository rendezVousRepository,
            EmailService emailService) {
        this.rendezVousRepository = rendezVousRepository;
        this.emailService = emailService;
    }

    // Vérifier chaque minute
    @Scheduled(fixedRate = 60000)
    public void verifierRappels() {
        LocalDateTime maintenant = LocalDateTime.now();
        LocalDateTime dans15min =
            maintenant.plusMinutes(15);
        LocalDateTime dans16min =
            maintenant.plusMinutes(16);

        // Chercher RDV entre 15 et 16 min
        List<RendezVous> rdvAVenir =
            rendezVousRepository.findRDVPourRappel(
                dans15min, dans16min);

        for (RendezVous rdv : rdvAVenir) {
            // Générer token si pas encore fait
            if (rdv.getToken() == null) {
                rdv.setToken(
                    UUID.randomUUID().toString());
            }

            // Envoyer email
            emailService.envoyerRappelRDV(rdv);

            // Marquer comme envoyé
            rdv.setRappelEnvoye(true);
            rendezVousRepository.save(rdv);

            System.out.println(
                "📧 Rappel envoyé pour RDV #"
                + rdv.getId() + " à "
                + rdv.getEmailRappel());
        }
    }
}
