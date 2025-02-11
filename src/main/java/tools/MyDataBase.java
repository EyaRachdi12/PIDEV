package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {
    private final String url = "jdbc:mysql://localhost:3306/pidev";
    private final String user = "root";
    private final String password = "";



    private Connection cnx;
    public static MyDataBase myDataBase;

    public MyDataBase() {
        try {
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static MyDataBase getInstance() {
        if (myDataBase == null) {
            myDataBase = new MyDataBase();

        }
        return myDataBase;
    }
    public Connection getCnx() {
        return cnx;
    }
}