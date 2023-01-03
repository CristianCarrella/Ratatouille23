package com.ingsw.ratatouille;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {
	Connection connection;
	Statement statement;
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
			statement = connection.createStatement();
			System.out.print("connected");
		}catch(SQLException e){
			System.out.print("not connected");
		}	
		

        try {
            connection = DriverManager.getConnection(url, props);
            statement = connection.createStatement();
            System.out.print("connected");
        }catch(SQLException e){
        	e.printStackTrace();
            System.out.print("not connected/n");
        }
    
	}
	
    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}