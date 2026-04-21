package com.fst.cabinet.repository;

import com.fst.cabinet.entity.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

// JpaRepository nous donne save(), findAll(), findById(), deleteById()
public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    // Chercher un médecin par son numéro d'ordre
    Medecin findByNumeroOrdre(String numeroOrdre);

    // Recherche par nom, prénom ou spécialité
    @Query("SELECT m FROM Medecin m WHERE " +
           "LOWER(m.nom) LIKE LOWER(CONCAT('%', :mot, '%')) OR " +
           "LOWER(m.prenom) LIKE LOWER(CONCAT('%', :mot, '%')) OR " +
           "LOWER(m.specialite) LIKE LOWER(CONCAT('%', :mot, '%'))")
    List<Medecin> rechercher(@Param("mot") String mot);

    // Récupérer seulement les médecins actifs
    List<Medecin> findByActifTrue();
}
