package com.fst.cabinet.service;

import com.fst.cabinet.entity.Medecin;
import com.fst.cabinet.repository.MedecinRepository;
import org.springframework.stereotype.Service;
import java.util.List;

// @Service contient la logique métier
@Service
public class MedecinService {

    // On injecte le repository
    private final MedecinRepository medecinRepository;

    // Constructeur
    public MedecinService(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    // Récupérer tous les médecins
    public List<Medecin> getTousLesMedecins() {
        return medecinRepository.findAll();
    }

    // Récupérer un médecin par son id
    public Medecin getMedecinParId(Long id) {
        return medecinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médecin non trouvé"));
    }

    // Sauvegarder un médecin (ajout ou modification)
    public Medecin sauvegarder(Medecin medecin) {
        // Vérifier que le numéro d'ordre n'existe pas déjà
        Medecin existant = medecinRepository.findByNumeroOrdre(medecin.getNumeroOrdre());
        if (existant != null && !existant.getId().equals(medecin.getId())) {
            throw new RuntimeException("Un médecin avec ce numéro d'ordre existe déjà !");
        }
        return medecinRepository.save(medecin);
    }

    // Supprimer un médecin
    public void supprimer(Long id) {
        medecinRepository.deleteById(id);
    }

    // Rechercher des médecins
    public List<Medecin> rechercher(String mot) {
        return medecinRepository.rechercher(mot);
    }

    // Récupérer les médecins actifs seulement
    public List<Medecin> getMedecinsActifs() {
        return medecinRepository.findByActifTrue();
    }
}
