package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD{
    
    private static final String URL = "jdbc:mysql://localhost:3306/rapidexpress?serverTimezone=UTC";
    private static final String USER = "campus2023";
    private static final String PASSWORD = "campus2023";
    
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
    
        } catch (SQLException e) {
            System.err.println("Error conectando a la base de datos: " + e.getMessage());
            return null;
        }
    }
}