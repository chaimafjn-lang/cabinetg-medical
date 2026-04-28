package com.fst.cabinet.service;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.repository.PatientRepository;
import com.fst.cabinet.repository.RendezVousRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PatientService {

    // ✅ Déclaration correcte des repositories
    private final PatientRepository patientRepository;
    private final RendezVousRepository rendezVousRepository;

    // ✅ Constructeur avec injection
    public PatientService(
            PatientRepository patientRepository,
            RendezVousRepository rendezVousRepository) {
        this.patientRepository = patientRepository;
        this.rendezVousRepository = rendezVousRepository;
    }

    // Récupérer tous les patients
    public List<Patient> getTousLesPatients() {
        return patientRepository.findAll();
    }

    // Récupérer un patient par id
    public Patient getPatientParId(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Patient non trouvé"));
    }

    // Sauvegarder un patient
    public Patient sauvegarder(Patient patient) {
        Patient existant = patientRepository
            .findByCin(patient.getCin());
        if (existant != null &&
                !existant.getId()
                    .equals(patient.getId())) {
            throw new RuntimeException(
                "Un patient avec ce CIN existe déjà !");
        }
        return patientRepository.save(patient);
    }

    // ✅ Supprimer patient + ses RDV
    @Transactional
    public void supprimer(Long id) {
        // 1. Supprimer d'abord les RDV du patient
        rendezVousRepository.deleteByPatientId(id);
        // 2. Ensuite supprimer le patient
        patientRepository.deleteById(id);
    }

    // Rechercher des patients
    public List<Patient> rechercher(String mot) {
        return patientRepository.rechercher(mot);
    }
}