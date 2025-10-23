package org.eclipse.config;
 
import java.sql.Connection;
import java.sql.DriverManager;
 
public class MySqlConnection {
    private static Connection connection = null;
 
    static {
        String username = "db_admin_tp";
        String password = "db_admin_tp_mdp";
        String url = "jdbc:mysql://localhost:3306/immobilier";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.err.println("Problème de connexion avec MySQL");
        }
    }
 
    private MySqlConnection() {
    }
 
    public static Connection getConnection() {
        return connection;
    }
}