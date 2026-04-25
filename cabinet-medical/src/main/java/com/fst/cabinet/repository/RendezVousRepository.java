package com.fst.cabinet.repository;

import com.fst.cabinet.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository 
    extends JpaRepository<RendezVous, Long> {

    // RDV d'un médecin
    List<RendezVous> findByMedecinId(Long medecinId);

    // RDV d'un patient
    List<RendezVous> findByPatientId(Long patientId);

    // RDV d'aujourd'hui
    @Query("SELECT r FROM RendezVous r WHERE " +
           "DATE(r.dateHeure) = CURRENT_DATE " +
           "AND r.statut != 'ANNULE' " +
           "ORDER BY r.dateHeure")
    List<RendezVous> findRDVAujourdhui();

    // Vérifier chevauchement
    @Query("SELECT r FROM RendezVous r WHERE " +
           "r.medecin.id = :medecinId AND " +
           "r.statut != 'ANNULE' AND " +
           "r.id != :rdvId AND (" +
           "(:debut < FUNCTION('ADDTIME', r.dateHeure, " +
           "FUNCTION('SEC_TO_TIME', r.dureeMinutes * 60))) AND " +
           "(:fin > r.dateHeure))")
    List<RendezVous> findChevauchements(
        @Param("medecinId") Long medecinId,
        @Param("debut") LocalDateTime debut,
        @Param("fin") LocalDateTime fin,
        @Param("rdvId") Long rdvId
    );
}