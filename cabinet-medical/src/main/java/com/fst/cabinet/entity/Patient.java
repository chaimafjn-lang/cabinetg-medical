package com.fst.cabinet.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity // bech spring yarf li class hedha hewa table fi bd mtena
@Table(name = "patients") // @Table dit le nom exact de la table dans MySQL
@Data // taml wahadha les getters w setters grace a lombok
@NoArgsConstructor // cration d'un constructeur vide new Patient()
@AllArgsConstructor // cree constructeur kemel 
public class Patient {

    @Id // cle primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id incremante automatiquement
    private Long id;

    @Column(unique = true, nullable = false) // zouz abed lezmch ykoun andhom nfs Cin  et cin oblg
    private String cin;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private LocalDate dateNaissance;
    private String telephone;
    private String email;
 // Photo du patient
 // Nom du fichier stocké sur le serveur
 private String photo = "default.jpg";

    @Column(columnDefinition = "TEXT")  // TEXT = peut contenir un long texte dans MySQL
    private String antecedents;

    private LocalDateTime dateCreation = LocalDateTime.now(); // LocalDateTime = date + heure
                                                             // On met automatiquement la date/heure de création
}