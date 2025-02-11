package services;

import entities.SupportPermission;
import tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceSupportPermission implements IService<SupportPermission> {
    Connection cnx;

    // 📌 Constructeur : Initialisation de la connexion à la base de données
    public ServiceSupportPermission() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    // 📌 CRUD : Ajouter une permission d'accès à un support
    @Override
    public void ajouter(SupportPermission supportPermission) throws SQLException {
        String sql = "INSERT INTO support_permissions (support_id, role, startDate, endDate) VALUES (?, ?, ?, ?)";

        // Préparation de la requête SQL
        PreparedStatement st = cnx.prepareStatement(sql);
        st.setInt(1, supportPermission.getSupportId());  // ID du support associé
        st.setString(2, supportPermission.getRole());    // Rôle de l'utilisateur (ex: "organisateur", "participant")
        st.setTimestamp(3, new Timestamp(supportPermission.getStartDate().getTime()));  // Date de début d'accès
        st.setTimestamp(4, new Timestamp(supportPermission.getEndDate().getTime()));    // Date de fin d'accès

        // Exécution de la requête
        st.executeUpdate();
        System.out.println("✅ Permission ajoutée avec succès !");
    }

    // 📌 CRUD : Supprimer une permission selon son ID
    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM support_permissions WHERE id = ?";

        // Préparation de la requête SQL
        PreparedStatement st = cnx.prepareStatement(sql);
        st.setInt(1, id);  // ID de la permission à supprimer

        // Exécution de la requête
        st.executeUpdate();
        System.out.println("❌ Permission supprimée avec succès !");
    }

    // 📌 CRUD : Modifier la date de fin d'une permission
    @Override
    public void modifier(int id, String newEndDate) throws SQLException {
        String sql = "UPDATE support_permissions SET end_date = ? WHERE id = ?";

        // Préparation de la requête SQL
        PreparedStatement st = cnx.prepareStatement(sql);
        st.setTimestamp(1, Timestamp.valueOf(newEndDate));  // Nouvelle date de fin
        st.setInt(2, id);  // ID de la permission à modifier

        // Exécution de la requête
        st.executeUpdate();
        System.out.println("✏️ Permission mise à jour avec succès !");
    }

    // 📌 CRUD : Récupérer toutes les permissions
    @Override
    public List<SupportPermission> recuperer() throws SQLException {
        String sql = "SELECT * FROM support_permissions";
        Statement ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery(sql);

        List<SupportPermission> list = new ArrayList<>();

        while (rs.next()) {
            SupportPermission supportPermission = new SupportPermission(); // Déclaration à l'intérieur de la boucle !

            supportPermission.setId(rs.getInt("id"));
            supportPermission.setSupportId(rs.getInt("support_id"));
            supportPermission.setRole(rs.getString("role"));
            supportPermission.setStartDate(rs.getTimestamp("startDate"));
            supportPermission.setEndDate(rs.getTimestamp("endDate"));

            list.add(supportPermission);
        }

        return list;
    }
}
