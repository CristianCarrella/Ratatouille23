package com.ingsw.ratatuille;

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
		String dbName = "postgres";
		String username = "postgres";
		String password = "postgrespw";
		String url = "jdbc:postgresql://host.docker.internal:49153/" + dbName;
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
		
	}

	public Connection getConnection() {
		return connection;
	}
	
	public Statement getStatement() {
		return statement;
	}
}
