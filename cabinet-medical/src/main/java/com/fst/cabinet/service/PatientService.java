package com.fst.cabinet.service;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.repository.PatientRepository;
import org.springframework.stereotype.Service;
import java.util.List;

// @Service dit à Spring que cette classe contient la logique métier
@Service
public class PatientService {

    // Spring injecte automatiquement le repository
    private final PatientRepository patientRepository;

    // Constructeur — Spring injecte PatientRepository ici
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Récupérer tous les patients
    public List<Patient> getTousLesPatients() {
        return patientRepository.findAll();
    }

    // Récupérer un patient par son id
    public Patient getPatientParId(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));
    }

    // Sauvegarder un patient (ajout ou modification)
    public Patient sauvegarder(Patient patient) {
        // Vérifier que le CIN n'existe pas déjà
        Patient existant = patientRepository.findByCin(patient.getCin());
        if (existant != null && !existant.getId().equals(patient.getId())) {
            throw new RuntimeException("Un patient avec ce CIN existe déjà !");
        }
        return patientRepository.save(patient);
    }

    // Supprimer un patient par son id
    public void supprimer(Long id) {
        patientRepository.deleteById(id);
    }

    // Rechercher des patients par mot clé
    public List<Patient> rechercher(String mot) {
        return patientRepository.rechercher(mot);
    }
}
