package com.fst.cabinet.repository;

import com.fst.cabinet.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PatientRepository 
    extends JpaRepository<Patient, Long> {

    // Chercher par CIN
    Patient findByCin(String cin);

    // Chercher par email
    Patient findByEmail(String email):

    // Recherche globale
    @Query("SELECT p FROM Patient p WHERE " +
           "LOWER(p.nom) LIKE LOWER(CONCAT('%',:mot,'%')) OR " +
           "LOWER(p.prenom) LIKE LOWER(CONCAT('%',:mot,'%')) OR " +
           "p.cin LIKE CONCAT('%',:mot,'%') OR " +
           "p.telephone LIKE CONCAT('%',:mot,'%')")
    List<Patient> rechercher(@Param("mot") String mot);
}