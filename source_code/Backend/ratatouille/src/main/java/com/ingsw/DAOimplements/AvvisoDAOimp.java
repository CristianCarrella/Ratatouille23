package com.ingsw.DAOimplements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.ingsw.DAOinterface.AvvisoDAOint;
import com.ingsw.ratatouille.Avviso;
import com.ingsw.ratatouille.AvvisoNascostoVisto;
import com.ingsw.ratatouille.DatabaseConnection;
import com.ingsw.ratatouille.User;


public class AvvisoDAOimp implements AvvisoDAOint{
	DatabaseConnection db;
	
	public AvvisoDAOimp(DatabaseConnection db) {
		this.db = db;
	}


	public ArrayList<Avviso> getAvvisi() {
		ArrayList<Avviso> avvisi = new ArrayList<Avviso>();
		String query = "SELECT * FROM avviso AS a INNER JOIN utente AS u on u.id_utente = a.id_utente";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Avviso a = new Avviso(rs.getInt("id_avviso"), rs.getInt("id_utente"), rs.getInt("id_ristorante"), rs.getString("testo"), rs.getString("data_ora"), rs.getString("nome"));
				avvisi.add(a);
			}
			return avvisi;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Avviso> getAvvisiOfResturant(Integer id_ristorante) {
		ArrayList<Avviso> avvisi = new ArrayList<Avviso>();
		String query = "SELECT * FROM avviso AS a INNER JOIN utente AS u on u.id_utente = a.id_utente WHERE a.id_ristorante = " + id_ristorante;
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Avviso a = new Avviso(rs.getInt("id_avviso"), rs.getInt("id_utente"), rs.getInt("id_ristorante"), rs.getString("testo"), rs.getString("data_ora"), rs.getString("nome"));
				avvisi.add(a);
			}
			return avvisi;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<AvvisoNascostoVisto> getAvvisiHiddenOf(Integer id_user) {
		ArrayList<AvvisoNascostoVisto> avvisi = new ArrayList<AvvisoNascostoVisto>();
		String query = "SELECT * FROM cronologia_nascosti_avviso JOIN avviso ON cronologia_nascosti_avviso.id_avviso = avviso.id_avviso WHERE cronologia_nascosti_avviso.id_utente = " + id_user;
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				AvvisoNascostoVisto a = new AvvisoNascostoVisto(rs.getInt("id_avviso"), rs.getInt("id_utente"), rs.getInt("id_ristorante"), rs.getString("testo"), rs.getString("data_ora"), rs.getString("data_nascosto"));
				avvisi.add(a);
			}
			return avvisi;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<AvvisoNascostoVisto> getAvvisiViewedOf(Integer id_user) {
		ArrayList<AvvisoNascostoVisto> avvisi = new ArrayList<AvvisoNascostoVisto>();
		String query = "SELECT * FROM cronologia_lettura_avviso JOIN avviso ON cronologia_lettura_avviso.id_avviso = avviso.id_avviso WHERE cronologia_lettura_avviso.id_utente = " + id_user;
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				AvvisoNascostoVisto a = new AvvisoNascostoVisto(rs.getInt("id_avviso"), rs.getInt("id_utente"), rs.getInt("id_ristorante"), rs.getString("testo"), rs.getString("data_ora"), rs.getString("data_lettura"));
				avvisi.add(a);
			}
			return avvisi;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public AvvisoNascostoVisto setAvvisoViewed(Integer id_avviso, User loggedUser, Integer id_ristorante) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();
		ResultSet rs;
		String query = null;		
		
		try {
			query = "SELECT * FROM avviso WHERE id_ristorante = " + id_ristorante + " AND id_avviso = " + id_avviso;
			rs = db.getStatement().executeQuery(query);
			if(rs.isBeforeFirst()) {
				query = "INSERT INTO cronologia_lettura_avviso (id_utente, id_avviso, data_lettura) VALUES (" + loggedUser.getIdUtente() + ", " + id_avviso + ", '" + now + "')";
				db.getStatement().executeUpdate(query);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public AvvisoNascostoVisto setAvvisoHidden(Integer id_avviso, User loggedUser, Integer id_ristorante) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();
		ResultSet rs;
		String query = null;		
		
		try {
			query = "SELECT * FROM avviso WHERE id_ristorante = " + id_ristorante + " AND id_avviso = " + id_avviso;
			rs = db.getStatement().executeQuery(query);
			if(rs.isBeforeFirst()) {
				query = "INSERT INTO cronologia_nascosti_avviso (id_utente, id_avviso, data_lettura) VALUES (" + loggedUser.getIdUtente() + ", " + id_avviso + ", '" + now + "')";
				db.getStatement().executeUpdate(query);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public Avviso createNewAvviso(Integer id_ristorante, String testo, User loggedUser) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();
		ResultSet rs;
		String query = null;
		boolean isSupervisoreOrAdmin = false;
		
		try {
			query = "SELECT ruolo FROM utente WHERE id_utente = " + loggedUser.getIdUtente() + " AND id_ristorante = " + id_ristorante;
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				String ruolo = rs.getString("ruolo");
				if(ruolo.equals("admin") || ruolo.equals("supervisore"))
					isSupervisoreOrAdmin = true;
			}
			
			if(isSupervisoreOrAdmin) {
				query = "INSERT INTO avviso (id_avviso, id_utente, id_ristorante, testo, data_ora) VALUES (default, " + loggedUser.getIdUtente() + ", " + id_ristorante + ", '" + testo + "', '" + now + "') ";
				db.getStatement().executeUpdate(query);
				
				query = "SELECT id_avviso FROM avviso WHERE id_utente = " + loggedUser.getIdUtente() + " AND id_ristorante = " + id_ristorante + " AND testo = '" + testo + "' AND  data_ora = '" + now + "'";
				rs = db.getStatement().executeQuery(query);
				while(rs.next()) {
					return new Avviso(rs.getInt("id_avviso"), loggedUser.getIdUtente(), id_ristorante, testo, now.toString());
				}
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	

}
