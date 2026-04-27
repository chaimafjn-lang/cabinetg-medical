package com.fst.cabinet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

// Table des rendez-vous
@Entity
@Table(name = "rendez_vous")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lien vers le patient
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Lien vers le médecin
    @ManyToOne
    @JoinColumn(name = "medecin_id", nullable = false)
    private Medecin medecin;

    // Date et heure du RDV
    private LocalDateTime dateHeure;

    // Durée en minutes
    private int dureeMinutes;

    // Motif du RDV
    private String motif;

    // Statut du RDV
    @Enumerated(EnumType.STRING)
    private StatutRDV statut = StatutRDV.PLANIFIE;

    // Email du patient pour rappel
    // Récupéré automatiquement depuis Patient.email
    private String emailRappel;

    // Token unique pour confirmation sans login
    @Column(unique = true)
    private String token;

    // Rappel déjà envoyé ?
    private boolean rappelEnvoye = false;

    // Statuts possibles
    public enum StatutRDV {
        PLANIFIE,
        CONFIRME,
        ANNULE,
        TERMINE
    }
}
