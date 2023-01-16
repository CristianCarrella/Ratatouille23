package com.ingsw.DAOimplements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.ingsw.DAOinterface.UserDAOint;
import com.ingsw.ratatouille.DatabaseConnection;
import com.ingsw.ratatouille.LoggedUser;
import com.ingsw.ratatouille.User;
@Component
public class UserDAOimp implements UserDAOint {
	DatabaseConnection db;
	
	public UserDAOimp(DatabaseConnection db){
		this.db = db;
	}
	
 
	public ArrayList<User> getUser() {
		ArrayList<User> users = new ArrayList<User>();
		String query = "SELECT * FROM utente";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				User u = new User(rs.getInt("id_utente"), rs.getString("nome"), rs.getString("cognome"), rs.getString("data_nascita"), rs.getString("email"), rs.getString("password"), rs.getString("ruolo"), rs.getBoolean("isFirstAccess"), rs.getInt("aggiunto_da"), rs.getString("data_aggiunta"), rs.getInt("id_ristorante"));
				users.add(u);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public User createAdmin(String nome, String cognome, String email, String password, String dataNascita, int idRistorante) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();
		
		String query = null;
		
		try {
			query = "INSERT INTO utente(id_utente, nome, cognome, data_nascita, email, password, ruolo, isFirstAccess, aggiunto_da, data_aggiunta, id_ristorante) VALUES (default, '" + nome + "', '" + cognome + "', '" + dataNascita + "', '" + email + "', '" + password + "' ,'admin' ," + "false, -1, '" + now + "', " + idRistorante  + ");";
			db.getStatement().executeUpdate(query);
		} catch (SQLException e) {
			return null;
		}
		
		User newUser = new User();
		return newUser;
	}
	
	public User createEmployee(String nome, String cognome, String passwordTemporanea, String email, String dataNascita, String ruolo, User loggedUser) {
				
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();
		int idUtenteCreato = 0;
		String query = "";
		
		if (loggedUser==null) {
			return null;
		}
		
		try {
			query = "INSERT INTO utente(id_utente, nome, cognome, password, data_nascita, email, ruolo, isFirstAccess, aggiunto_da, data_aggiunta, id_ristorante) VALUES (default, '" + nome + "', '" + cognome + "', '" + passwordTemporanea + "', '" + dataNascita + "', '" + email + "' ,'" + ruolo + "' ," + "false, " + loggedUser.getIdUtente() + ", '" + now + "', " + loggedUser.getIdRistorante() + ");";
			db.getStatement().executeUpdate(query);
			
			query = "SELECT id_utente FROM utente WHERE email = '" + email + "';";
			db.getStatement().executeQuery(query);
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next()) {
				idUtenteCreato = rs.getInt("id_utente");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
		
		User newUser = new User(idUtenteCreato, nome, cognome, dataNascita, email, passwordTemporanea, ruolo, false, loggedUser.getIdUtente(), now.toString(), loggedUser.getIdRistorante());
		return newUser;
	}

	
	public ArrayList<User> getUserOfResturant(int id_ristorante) {
		ArrayList<User> users = new ArrayList<User>();
		String query = "SELECT * FROM utente WHERE id_ristorante = " + id_ristorante;
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				User u = new User(rs.getInt("id_utente"), rs.getString("nome"), rs.getString("cognome"), rs.getString("data_nascita"), rs.getString("email"), rs.getString("password"), rs.getString("ruolo"), rs.getBoolean("isFirstAccess"), rs.getInt("aggiunto_da"), rs.getString("data_aggiunta"), rs.getInt("id_ristorante"));
				users.add(u);
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User getUserById(Integer id_utente) {
		User u = null;
		String query = "SELECT * FROM utente WHERE id_utente = " + id_utente;
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				u = new User(rs.getInt("id_utente"), rs.getString("nome"), rs.getString("cognome"), rs.getString("data_nascita"), rs.getString("email"), rs.getString("password"), rs.getString("ruolo"), rs.getBoolean("isFirstAccess"), rs.getInt("aggiunto_da"), rs.getString("data_aggiunta"), rs.getInt("id_ristorante"));
			}
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public LoggedUser login(String email, String password, String token, String expirationTime) {
		LoggedUser u = null;
		String query = "SELECT * FROM utente WHERE email = '" + email + "' AND password = '" + password + "'";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				u = new LoggedUser(rs.getInt("id_utente"), rs.getString("nome"), rs.getString("cognome"), rs.getString("data_nascita"), rs.getString("email"), rs.getString("password"), rs.getString("ruolo"), rs.getBoolean("isFirstAccess"), rs.getInt("aggiunto_da"), rs.getString("data_aggiunta"), rs.getInt("id_ristorante"), token, expirationTime);
			}
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public User verifyEmployee(String nome, String cognome, String email, String dataNascita) {
		String query = null;
		try {
			query = "SELECT * FROM utente WHERE email = '" + email + "' AND nome = '" + nome + "' AND cognome = '" + cognome + "' AND data_nascita = '" + dataNascita + "';";
			db.getStatement().executeQuery(query);						 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public User modifyUserNameSurnameDate(User loggedUser, String nome, String cognome, String dataNascita) {
		int idUtente = loggedUser.getIdUtente();
		String query = "UPDATE utente SET nome = '" + nome + "', cognome = '" + cognome + "', data_nascita = '" + dataNascita + "' WHERE id_utente = " + idUtente;
		try {
			db.getStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

