package test;

import entities.Support;
import entities.SupportPermission;
import entities.TypeSupport;
import services.ServiceSupport;
import services.ServiceSupportPermission;
import tools.MyDataBase;
import java.util.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Initialisation de la base de données (Singleton)
        MyDataBase db = MyDataBase.getInstance();

        // Création des services pour la gestion des supports et des permissions
        ServiceSupport sv = new ServiceSupport();
        ServiceSupportPermission permissionService = new ServiceSupportPermission();

        // Création d'un support de type Vidéo
        Support supportVideo = new Support(
                TypeSupport.VIDEO,  // Type de support : Vidéo
                "https://www.youtube.com/watch?v=examplevideoid",  // URL du support
                1,  // ID de l'organisateur qui a ajouté ce support
                "Présentation de l'événement"  // Description du support
        );

        Support supportPDFPresentation = new Support(
                TypeSupport.PDF,  // Type de support : PDF
                "https://example.com/path/to/presentation.pdf",  // URL du support
                34,  // ID de l'organisateur qui a ajouté ce support
                "Présentation détaillée de l'événement"  // Description du support
        );

        try {
            // Afficher la liste des supports disponibles (avant ajout)
            System.out.println(sv.recuperer());

            // Ajouter le support vidéo dans la base de données
            sv.ajouter(supportVideo);

            // Récupérer l'ID du support après son ajout
            int supportId = supportVideo.getId();  // Supposons que getId() retourne l'ID généré par la base de données

            // Création des dates pour la permission
            Date startDate = new Date();  // Date actuelle (début d'accès au support)
            Date endDate = new Date(startDate.getTime() + 3600000L);  // Date de fin (1 heure après)

            // Création d'une permission associée au support
            SupportPermission permission = new SupportPermission(
                    supportId,       // ID du support associé
                    "organisateur",  // Rôle de l'utilisateur ayant l'accès
                    startDate,       // Date de début d'accès
                    endDate          // Date de fin d'accès
            );

            // Ajouter la permission dans la base de données
            permissionService.ajouter(permission);

            // Afficher tous les supports après l'ajout
            System.out.println("Liste des supports : " + sv.recuperer());

            // Modifier un support (changer son type, par exemple)
            sv.modifier(supportId, "document");

            // Afficher la liste après modification
            System.out.println("Liste des supports après modification : " + sv.recuperer());

            // Récupérer et afficher toutes les permissions existantes
            System.out.println("Liste des permissions : ");
            for (SupportPermission p : permissionService.recuperer()) {
                System.out.println(p);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage()); // Gestion des erreurs SQL
        }
    }
}