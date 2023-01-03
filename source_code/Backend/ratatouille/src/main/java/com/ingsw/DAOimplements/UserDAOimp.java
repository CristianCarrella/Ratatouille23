package com.ingsw.DAOimplements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.ingsw.DAOinterface.UserDAOint;
import com.ingsw.ratatouille.DatabaseConnection;
import com.ingsw.ratatouille.User;

public class UserDAOimp implements UserDAOint {
	DatabaseConnection db;
	
	public UserDAOimp(DatabaseConnection db){
		this.db = db;
	}
	
 
	@Override
	public ArrayList<User> getUser() throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String query = "SELECT * FROM utente";
		ResultSet rs = db.getStatement().executeQuery(query);
		while(rs.next()) {
			User u = new User(rs.getInt("id_utente"), rs.getString("nome"), rs.getString("data_nascita"));
			users.add(u);
		}
		return users;
	}
	
	@Override
	public User createAdmin(String nome, String cognome, String email, String password, String dataNascita, int idRistorante) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();
		
		String query = "";
		
		try {
			query = "INSERT INTO utente(id_utente, nome, cognome, data_nascita, email, password, ruolo, isFirstAccess, aggiunto_da, data_aggiunta, id_ristorante) VALUES (default, '" + nome + "', '" + cognome + "', '" + dataNascita + "', '" + password + "', '" + email + "' ,'admin' ," + "false, -1, '" + now + "', " + idRistorante  + ");";
			System.out.println(query);
			db.getStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		User newUser = new User(nome, cognome, email, password, dataNascita);
		return newUser;
	}
	
	public User createEmployee() {
		return null;
	}


	@Override
	public void createEmployee(String nome, String cognome, String email, String dataNascita, String ruolo,
			int idRistorante) {
		// TODO Auto-generated method stub
		
	}
}

