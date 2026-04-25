package com.fst.cabinet.service;

import com.fst.cabinet.entity.RendezVous;
import com.fst.cabinet.repository.RendezVousRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;

    public RendezVousService(
            RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    public List<RendezVous> getTousLesRDV() {
        return rendezVousRepository.findAll();
    }

    public RendezVous getRDVParId(Long id) {
        return rendezVousRepository.findById(id)
                .orElseThrow(() -> 
                    new RuntimeException("RDV non trouvé"));
    }

    public List<RendezVous> getRDVAujourdhui() {
        return rendezVousRepository.findRDVAujourdhui();
    }

    public List<RendezVous> getRDVParPatient(Long patientId) {
        return rendezVousRepository
            .findByPatientId(patientId);
    }

    public RendezVous sauvegarder(RendezVous rdv) {
        LocalDateTime debut = rdv.getDateHeure();
        LocalDateTime fin = debut.plusMinutes(
            rdv.getDureeMinutes());
        Long rdvId = rdv.getId() != null ? rdv.getId() : 0L;

        List<RendezVous> chevauchements = 
            rendezVousRepository.findChevauchements(
                rdv.getMedecin().getId(),
                debut, fin, rdvId);

        if (!chevauchements.isEmpty()) {
            throw new RuntimeException(
                "⚠️ Ce médecin a déjà un RDV à cette heure !");
        }
        return rendezVousRepository.save(rdv);
    }

    public void annuler(Long id) {
        RendezVous rdv = getRDVParId(id);
        rdv.setStatut(RendezVous.StatutRDV.ANNULE);
        rendezVousRepository.save(rdv);
    }

    public void supprimer(Long id) {
        rendezVousRepository.deleteById(id);
    }
}