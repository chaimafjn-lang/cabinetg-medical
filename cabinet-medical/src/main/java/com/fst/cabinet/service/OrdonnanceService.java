package com.fst.cabinet.service;

import com.fst.cabinet.entity.Ordonnance;
import com.fst.cabinet.entity.LigneMedicament;
import com.fst.cabinet.repository.OrdonnanceRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrdonnanceService {

    private final OrdonnanceRepository ordonnanceRepository;

    public OrdonnanceService(
            OrdonnanceRepository ordonnanceRepository) {
        this.ordonnanceRepository = ordonnanceRepository;
    }

    public List<Ordonnance> getToutesLesOrdonnances() {
        return ordonnanceRepository.findAll();
    }

    public Ordonnance getOrdonnanceParId(Long id) {
        return ordonnanceRepository.findById(id)
                .orElseThrow(() -> 
                    new RuntimeException(
                        "Ordonnance non trouvée"));
    }

    public List<Ordonnance> getOrdonnancesParPatient(
            Long patientId) {
        return ordonnanceRepository
            .findByRendezVousPatientId(patientId);
    }

    public Ordonnance sauvegarder(Ordonnance ordonnance) {
        if (ordonnance.getMedicaments() != null) {
            for (LigneMedicament ligne : 
                    ordonnance.getMedicaments()) {
                ligne.setOrdonnance(ordonnance);
            }
        }
        return ordonnanceRepository.save(ordonnance);
    }

    public void supprimer(Long id) {
        ordonnanceRepository.deleteById(id);
    }
}