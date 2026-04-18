package com.fst.cabinet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rendez_vous")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne               // @ManyToOne : plusieurs RDV peuvent appartenir à un seul patient
                        
    @JoinColumn(name = "patient_id", nullable = false)    // @JoinColumn : nom de la colonne dans la table MySQL
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id", nullable = false)
    private Medecin medecin;

    private LocalDateTime dateHeure;     // Date ET heure du rendez-vous
    private int dureeMinutes;            // durée rdv
    private String motif;                // aleh jey yadi (consultation general)

    @Enumerated(EnumType.STRING) // pour stocker le texte dans MySQL (PLANIFIE, CONFIRME...)
    private StatutRDV statut = StatutRDV.PLANIFIE;  // Par défaut un nouveau RDV est PLANIFIE

    public enum StatutRDV {
        PLANIFIE, // RDV créé mais pas encore confirmé
        CONFIRME, // RDV confirmé
        ANNULE, //annulé
        TERMINE // terminé
    }
}