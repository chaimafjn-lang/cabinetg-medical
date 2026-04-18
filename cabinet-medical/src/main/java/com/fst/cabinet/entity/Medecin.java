package com.fst.cabinet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medecins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medecin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private String specialite;

    @Column(unique = true, nullable = false)
    private String numeroOrdre;

    private String telephone;
    private String email;
    private boolean actif = true;
}