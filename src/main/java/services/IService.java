package services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    public void ajouter(T t) throws SQLException;

    public void supprimer(int id) throws SQLException;

    public void modifier(int id, String titre) throws SQLException;

    List<T> recuperer() throws SQLException;
}