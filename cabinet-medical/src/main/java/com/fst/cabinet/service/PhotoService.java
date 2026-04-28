package com.fst.cabinet.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PhotoService {

    // Dossier où stocker les photos
    // dans src/main/resources/static/photos/
    private final String uploadDir = 
        "src/main/resources/static/photos/";

    public String sauvegarderPhoto(
            MultipartFile photo) throws IOException {

        // Vérifier que le fichier n'est pas vide
        if (photo == null || photo.isEmpty()) {
            return "default.jpg";
        }

        // Vérifier le format JPG/PNG
        String contentType = photo.getContentType();
        if (contentType == null ||
                (!contentType.equals("image/jpeg") &&
                 !contentType.equals("image/png"))) {
            throw new RuntimeException(
                "Format non accepté ! " +
                "Utilisez JPG ou PNG uniquement.");
        }

        // Créer le dossier si il n'existe pas
        File dossier = new File(uploadDir);
        if (!dossier.exists()) {
            dossier.mkdirs();
        }

        // Générer un nom unique pour la photo
        String extension = contentType
            .equals("image/jpeg") ? ".jpg" : ".png";
        String nomFichier = UUID.randomUUID()
            .toString() + extension;

        // Sauvegarder le fichier
        Path chemin = Paths.get(
            uploadDir + nomFichier);
        Files.write(chemin, photo.getBytes());

        return nomFichier;
    }
}
