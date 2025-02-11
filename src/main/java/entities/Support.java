package entities;

import services.ServiceSupportPermission;

import java.sql.SQLException;
import java.util.List;

public class Support {
    private int id;
    private String url;
    private TypeSupport type;
    private int evenementAssocie;
    private String titre;
    private List<SupportPermission> permissions;  // Liste des permissions d'accès



    public Support() {
    }
//sans id : pour l'ajout//
    public Support(TypeSupport type, String url, int evenementAssocie, String titre) {
        this.type = type;
        this.url = url;
        this.evenementAssocie=evenementAssocie;
        this.titre = titre;
    }



    //getters et setters//

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TypeSupport getType() {
        return type;
    }

    public void setType(TypeSupport type) {
        this.type = type;
    }

    public int getEvenementAssocie() {
        return evenementAssocie;
    }

    public void setEvenementAssocie(int evenementAssocie) {
        this.evenementAssocie = evenementAssocie;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    // méthode toString//

    @Override
    public String toString() {
        return "Support{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", evenementAssocie='" + evenementAssocie + '\'' +
                ", titre='" + titre + '\'' +
        ", permissions=" + permissions +
                '}';

    }

    // Constructeurs, getters et setters...

    public List<SupportPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SupportPermission> permissions) {
        this.permissions = permissions;
    }


    }



