package com.fst.cabinet.service;

import com.fst.cabinet.entity.RendezVous;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // Récupère l'URL depuis application.properties
    @Value("${app.url}")
    private String appUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Envoyer email de rappel
    public void envoyerRappelRDV(RendezVous rdv) {
        try {
            MimeMessage message =
                mailSender.createMimeMessage();
            MimeMessageHelper helper =
                new MimeMessageHelper(message,
                    true, "UTF-8");

            // Envoyer à l'email du patient
            helper.setTo(rdv.getEmailRappel());
            helper.setSubject(
                "⏰ Rappel : Votre RDV dans 15 minutes !");

            // Corps HTML
            helper.setText(
                construireEmailHtml(rdv), true);

            mailSender.send(message);
            System.out.println("✅ Email envoyé à : "
                + rdv.getEmailRappel());

        } catch (Exception e) {
            System.err.println("❌ Erreur email : "
                + e.getMessage());
        }
    }

    // Template HTML
    private String construireEmailHtml(RendezVous rdv) {
        String nomPatient = rdv.getPatient().getNom()
            + " " + rdv.getPatient().getPrenom();
        String nomMedecin = "Dr. "
            + rdv.getMedecin().getNom()
            + " " + rdv.getMedecin().getPrenom();
        String heure = rdv.getDateHeure()
            .toLocalTime()
            .toString().substring(0, 5);
        String date = rdv.getDateHeure()
            .toLocalDate().toString();

        String lienConfirmer = appUrl
            + "/rdv/confirmer?token=" + rdv.getToken();
        String lienAnnuler = appUrl
            + "/rdv/annuler-email?token=" + rdv.getToken();

        return "<!DOCTYPE html><html><head>" +
            "<meta charset='UTF-8'><style>" +
            "body{font-family:Arial,sans-serif;" +
            "background:#f0f2f5;margin:0;padding:20px;}" +
            ".box{max-width:600px;margin:0 auto;" +
            "background:white;border-radius:15px;" +
            "overflow:hidden;" +
            "box-shadow:0 5px 20px rgba(0,0,0,0.1);}" +
            ".top{background:linear-gradient(" +
            "135deg,#1a73e8,#0d47a1);" +
            "color:white;padding:30px;text-align:center;}" +
            ".body{padding:30px;}" +
            ".info{background:#f8f9fa;" +
            "border-radius:10px;padding:20px;" +
            "margin:20px 0;" +
            "border-left:4px solid #1a73e8;}" +
            ".btn{display:inline-block;" +
            "padding:15px 30px;border-radius:50px;" +
            "text-decoration:none;font-weight:bold;" +
            "font-size:1rem;margin:10px;color:white;}" +
            ".ok{background:#28a745;}" +
            ".ko{background:#dc3545;}" +
            ".foot{background:#f8f9fa;padding:20px;" +
            "text-align:center;color:#666;" +
            "font-size:0.85rem;}" +
            "</style></head><body>" +
            "<div class='box'>" +
            "<div class='top'>" +
            "<h2 style='margin:0'>🏥 Cabinet Médical</h2>" +
            "<p style='margin:5px 0;opacity:0.9;'>" +
            "Rappel de rendez-vous</p></div>" +
            "<div class='body'>" +
            "<p style='font-size:1.1rem;'>Bonjour " +
            "<strong>" + nomPatient + "</strong>,</p>" +
            "<p>Votre rendez-vous est prévu dans " +
            "<strong style='color:#1a73e8;'>" +
            "15 minutes</strong>.</p>" +
            "<div class='info'>" +
            "<p>📅 <strong>Date :</strong> " + date + "</p>" +
            "<p>⏰ <strong>Heure :</strong> " + heure + "</p>" +
            "<p>👨‍⚕️ <strong>Médecin :</strong> " +
            nomMedecin + "</p>" +
            "<p style='margin:0'>🏥 <strong>Spécialité :</strong> " +
            rdv.getMedecin().getSpecialite() + "</p>" +
            "</div>" +
            "<p><strong>Confirmez-vous votre présence ?</strong></p>" +
            "<div style='text-align:center;margin:25px 0;'>" +
            "<a href='" + lienConfirmer +
            "' class='btn ok'>" +
            "✅ Je confirme ma présence</a><br><br>" +
            "<a href='" + lienAnnuler +
            "' class='btn ko'>" +
            "❌ Annuler / Reporter</a></div>" +
            "<p style='color:#666;font-size:0.85rem;'>" +
            "⚠️ Sans réponse de votre part, " +
            "votre place pourra être donnée " +
            "à un patient en salle d'attente.</p>" +
            "</div>" +
            "<div class='foot'>" +
            "Cabinet Médical — FST Master STR 2025/2026" +
            "</div></div></body></html>";
    }
}
