package com.fst.cabinet.repository;

import com.fst.cabinet.entity.Ordonnance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdonnanceRepository 
    extends JpaRepository<Ordonnance, Long> {

    // Récupérer l'ordonnance d'un rendez-vous
    Ordonnance findByRendezVousId(Long rendezVousId);

    // Récupérer les ordonnances d'un patient
    List<Ordonnance> findByRendezVousPatientId(
        Long patientId);
}