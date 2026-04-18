package com.fst.cabinet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lignes_medicament")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneMedicament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordonnance_id")
    private Ordonnance ordonnance;

    private String nomMedicament;
    private String posologie; // Comment prendre le médicament ex: "1 comprimé matin et soir"
    private String duree; // kadeh wkt tokod testaml fih dwe
}