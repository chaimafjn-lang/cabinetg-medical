package com.fst.cabinet.repository;

import com.fst.cabinet.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository
        extends JpaRepository<RendezVous, Long> {

    List<RendezVous> findByMedecinId(Long medecinId);

    List<RendezVous> findByPatientId(Long patientId);

    @Query("SELECT r FROM RendezVous r WHERE " +
           "DATE(r.dateHeure) = CURRENT_DATE " +
           "AND r.statut != 'ANNULE' " +
           "ORDER BY r.dateHeure")
    List<RendezVous> findRDVAujourdhui();

    // ✅ VERSION CORRIGÉE — SQL natif MySQL
    @Query(value =
        "SELECT * FROM rendez_vous r WHERE " +
        "r.medecin_id = :medecinId AND " +
        "r.statut != 'ANNULE' AND " +
        "r.id != :rdvId AND " +
        ":debut < DATE_ADD(r.date_heure, INTERVAL r.duree_minutes MINUTE) AND " +
        ":fin > r.date_heure",
        nativeQuery = true)
    List<RendezVous> findChevauchements(
        @Param("medecinId") Long medecinId,
        @Param("debut") LocalDateTime debut,
        @Param("fin") LocalDateTime fin,
        @Param("rdvId") Long rdvId
    );

    @Query("SELECT r FROM RendezVous r WHERE " +
           "r.dateHeure BETWEEN :debut AND :fin AND " +
           "r.rappelEnvoye = false AND " +
           "r.statut != 'ANNULE' AND " +
           "r.emailRappel IS NOT NULL")
    List<RendezVous> findRDVPourRappel(
        @Param("debut") LocalDateTime debut,
        @Param("fin") LocalDateTime fin
    );

    RendezVous findByToken(String token);

    void deleteByPatientId(Long patientId);
}