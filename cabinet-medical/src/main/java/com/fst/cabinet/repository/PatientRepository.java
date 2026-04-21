package com.fst.cabinet.repository;

import com.fst.cabinet.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

// JpaRepository nous donne automatiquement les méthodes
// save(), findAll(), findById(), deleteById() etc.
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Chercher un patient par son CIN
    Patient findByCin(String cin);

    // Recherche par nom OU prénom OU CIN
    // %:mot% signifie "contient ce mot"
    @Query("SELECT p FROM Patient p WHERE " +
           "LOWER(p.nom) LIKE LOWER(CONCAT('%', :mot, '%')) OR " +
           "LOWER(p.prenom) LIKE LOWER(CONCAT('%', :mot, '%')) OR " +
           "p.cin LIKE CONCAT('%', :mot, '%') OR " +
           "p.telephone LIKE CONCAT('%', :mot, '%')")
    List<Patient> rechercher(@Param("mot") String mot);
}
