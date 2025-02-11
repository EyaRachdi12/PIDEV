package services;

import entities.Support;
import entities.SupportPermission;
import entities.TypeSupport;
import tools.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceSupport implements IService<Support> {

    private final Connection cnx;

    public ServiceSupport() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    // 📌 Vérifier si un support existe déjà avant insertion
    public boolean supportExiste(String url, int evenementAssocie) throws SQLException {
        String sql = "SELECT COUNT(*) FROM support WHERE url = ? AND evenementAssocie = ?";
        PreparedStatement st = cnx.prepareStatement(sql);
        st.setString(1, url);
        st.setInt(2, evenementAssocie);
        ResultSet rs = st.executeQuery();
        return rs.next() && rs.getInt(1) > 0;
    }

    @Override
    public void ajouter(Support support) throws SQLException {
        if (supportExiste(support.getUrl(), support.getEvenementAssocie())) {
            System.out.println("⚠️ Ce support existe déjà !");
            return;
        }

        String sql = "INSERT INTO support (type, url, evenementAssocie, titre) VALUES (?, ?, ?, ?)";
        PreparedStatement st = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        st.setString(1, support.getType().toString());
        st.setString(2, support.getUrl());
        st.setInt(3, support.getEvenementAssocie());
        st.setString(4, support.getTitre());

        st.executeUpdate();

        // Récupérer l'ID généré
        ResultSet rs = st.getGeneratedKeys();
        if (rs.next()) {
            support.setId(rs.getInt(1));  // ID du support inséré
        }

        // Vérifie que le support est bien inséré avant d'ajouter les permissions
        String checkSupportSql = "SELECT COUNT(*) FROM support WHERE id = ?";
        PreparedStatement checkSt = cnx.prepareStatement(checkSupportSql);
        checkSt.setInt(1, support.getId());
        ResultSet checkRs = checkSt.executeQuery();
        if (checkRs.next() && checkRs.getInt(1) > 0) {
            // Si le support existe bien, ajouter des permissions ici si nécessaire
            // Ajouter les permissions à ce support
            System.out.println("✅ Support ajouté avec succès !");
        } else {
            System.out.println("❌ Erreur : Le support n'a pas été inséré correctement.");
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM Support WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(sql);
        st.setInt(1, id);
        st.executeUpdate();
        System.out.println("❌ Support supprimé avec succès !");
    }

    @Override
    public void modifier(int id, String titre) throws SQLException {
        String sql = "UPDATE Support SET titre = ? WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(sql);
        st.setString(1, titre);
        st.setInt(2, id);
        st.executeUpdate();
        System.out.println("✏️ Support mis à jour avec succès !");
    }

    // 📌 Charger les permissions de chaque support
    private List<SupportPermission> getPermissionsBySupport(int supportId) throws SQLException {
        List<SupportPermission> permissions = new ArrayList<>();
        String sql = "SELECT * FROM support_permissions WHERE support_id = ?";
        PreparedStatement st = cnx.prepareStatement(sql);
        st.setInt(1, supportId);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            permissions.add(new SupportPermission(
                    rs.getInt("id"),
                    rs.getInt("support_id"),
                    rs.getString("role"),
                    rs.getTimestamp("startDate"),
                    rs.getTimestamp("endDate")
            ));
        }
        return permissions;
    }

    @Override
    public List<Support> recuperer() throws SQLException {
        String sql = "SELECT * FROM Support";
        PreparedStatement st = cnx.prepareStatement(sql);
        ResultSet rs = st.executeQuery();

        List<Support> list = new ArrayList<>();

        while (rs.next()) {
            Support support = new Support();
            support.setId(rs.getInt("id"));
            support.setUrl(rs.getString("url"));
            support.setType(TypeSupport.valueOf(rs.getString("type").toUpperCase()));
            support.setEvenementAssocie(rs.getInt("evenementAssocie"));
            support.setTitre(rs.getString("titre"));

            // Charger les permissions associées à ce support
            support.setPermissions(getPermissionsBySupport(support.getId()));

            list.add(support);
        }

        return list;
    }
}
