package com.fst.cabinet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ordonnances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ordonnance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne // @OneToOne : une ordonnance est liée à UN SEUL rendez-vous 
    // noksdou biha cardinalité
    @JoinColumn(name = "rendez_vous_id") 
    private RendezVous rendezVous;

    private LocalDate dateEmission = LocalDate.now(); // Date d'aujourd'hui par défaut

    @Column(columnDefinition = "TEXT") // notes de medecin
    private String observations;

    @OneToMany(mappedBy = "ordonnance", cascade = CascadeType.ALL)
    // @OneToMany : une ordonnance peut avoir plusieurs médicaments
    // mappedBy : dit que c'est LigneMedicament qui gère la relation
    // CascadeType.ALL : si on supprime l'ordonnance, les médicaments aussi
    private List<LigneMedicament> medicaments;
}