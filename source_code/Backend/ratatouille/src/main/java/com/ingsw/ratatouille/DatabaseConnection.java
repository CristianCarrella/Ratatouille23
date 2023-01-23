package com.ingsw.ratatouille;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.stereotype.Component;
@Component
public class DatabaseConnection {
	Connection connection;
	//da fare singleton 
	DatabaseConnection(){
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  
		String dbName = "ratatouille";
		String username = "user";
		String password = "admin";
		String url = "jdbc:postgresql://host.docker.internal:54320/" + dbName;
		Properties props = new Properties();
		props.setProperty("user", username);
		props.setProperty("password", password);
		
		try {
			connection = DriverManager.getConnection(url, props);
			System.out.print("connected");
		}catch(SQLException e){
			System.out.print("not connected");
		}	
		
    
	}
	
    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
    	Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return statement;
    }
}