package entities;

import java.util.Date;

public class SupportPermission {

    // Cette classe devrait être en dehors de SupportPermission, pas à l'intérieur.
    private int id;
    private int supportId;  // Référence à la table support
    private String role;    // Rôle (organisateur, participant)
    private Date startDate; // Date de début d'accès
    private Date endDate;   // Date de fin d'accès

    public SupportPermission() {
    }

    // Constructeur
    public SupportPermission(String role, Date startDate, Date endDate) {
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SupportPermission(int id, int supportId, String role, Date startDate, Date endDate) {
        this.id = id;
        this.supportId = supportId;
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public SupportPermission(int supportId, String role, Date startDate, Date endDate) {
        this.supportId = supportId;
        this.role = role;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    // Getters et setters...
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSupportId() {
        return supportId;
    }

    public void setSupportId(int supportId) {
        this.supportId = supportId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SupportPermission{" +
                "id=" + id +
                ", supportId=" + supportId +
                ", role='" + role + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
