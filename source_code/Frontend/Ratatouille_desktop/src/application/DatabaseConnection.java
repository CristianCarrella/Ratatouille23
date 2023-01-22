package application;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DatabaseConnection {
	
    public void dbConnect() {
        String url = "jdbc:postgresql://localhost:5050/ratatouille";
        String username = "user";
        String password = "password";
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connesso al database!");
        } catch (SQLException e) {
            System.err.format("Errore di connessione: %s%n", e);
        }
    }

}
