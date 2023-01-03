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


	@Override
	public ArrayList<Avviso> getAvvisi() {
		ArrayList<Avviso> avvisi = new ArrayList<Avviso>();
		String query = "SELECT * FROM avviso";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Avviso a = new Avviso(rs.getInt("id_avviso"), rs.getInt("id_utente"), rs.getInt("id_ristorante"), rs.getString("testo"), rs.getString("data_ora"));
				avvisi.add(a);
			}
			return avvisi;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Avviso> getAvvisiOfResturant(Integer id_ristorante) {
		ArrayList<Avviso> avvisi = new ArrayList<Avviso>();
		String query = "SELECT * FROM avviso WHERE id_ristorante = " + id_ristorante;
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Avviso a = new Avviso(rs.getInt("id_avviso"), rs.getInt("id_utente"), rs.getInt("id_ristorante"), rs.getString("testo"), rs.getString("data_ora"));
				avvisi.add(a);
			}
			return avvisi;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
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



	@Override
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

	
	//Controllare che l'utente sia supervisore o admin
	@Override
	public AvvisoNascostoVisto setAvvisoViewed(Integer id_avviso, User loggedUser, Integer id_ristorante) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();
		String query = "SELECT * FROM avviso WHERE id_ristorante = " + id_ristorante + " AND id_avviso = " + id_avviso;
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			if(rs.isBeforeFirst()) {
				query = "INSERT INTO cronologia_lettura_avviso (id_utente, id_avviso, data_lettura) VALUES (" + loggedUser.getId_utente() + ", " + id_avviso + ", '" + now + "')";
				db.getStatement().executeUpdate(query);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	

}
