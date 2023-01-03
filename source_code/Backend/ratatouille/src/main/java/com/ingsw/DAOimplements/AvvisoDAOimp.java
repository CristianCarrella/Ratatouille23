package com.ingsw.DAOimplements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ingsw.DAOinterface.AvvisoDAOint;
import com.ingsw.ratatouille.Avviso;
import com.ingsw.ratatouille.AvvisoNascostoVisto;
import com.ingsw.ratatouille.DatabaseConnection;


public class AvvisoDAOimp implements AvvisoDAOint{
	DatabaseConnection db;
	
	public AvvisoDAOimp(DatabaseConnection db) {
		this.db = db;
	}


	@Override
	public ArrayList<Avviso> getAvvisi() throws SQLException {
		ArrayList<Avviso> avvisi = new ArrayList<Avviso>();
		String query = "SELECT * FROM avviso";
		ResultSet rs = db.getStatement().executeQuery(query);
		while(rs.next()) {
			Avviso a = new Avviso(rs.getInt("id_avviso"), rs.getInt("id_utente"), rs.getInt("id_ristorante"), rs.getString("testo"), rs.getString("data_ora"));
			avvisi.add(a);
		}
		return avvisi;
	}

	@Override
	public ArrayList<Avviso> getAvvisiOfResturant(Integer id_ristorante) throws SQLException {
		ArrayList<Avviso> avvisi = new ArrayList<Avviso>();
		String query = "SELECT * FROM avviso WHERE id_ristorante = " + id_ristorante;
		ResultSet rs = db.getStatement().executeQuery(query);
		while(rs.next()) {
			Avviso a = new Avviso(rs.getInt("id_avviso"), rs.getInt("id_utente"), rs.getInt("id_ristorante"), rs.getString("testo"), rs.getString("data_ora"));
			avvisi.add(a);
		}
		return avvisi;
	}

	@Override
	public ArrayList<AvvisoNascostoVisto> getAvvisiHiddenOf(Integer id_user) throws SQLException {
		ArrayList<AvvisoNascostoVisto> avvisi = new ArrayList<AvvisoNascostoVisto>();
		String query = "SELECT * FROM cronologia_nascosti_avviso JOIN avviso ON cronologia_nascosti_avviso.id_avviso = avviso.id_avviso WHERE cronologia_nascosti_avviso.id_utente = " + id_user;
		ResultSet rs = db.getStatement().executeQuery(query);
		while(rs.next()) {
			AvvisoNascostoVisto a = new AvvisoNascostoVisto(rs.getInt("id_avviso"), rs.getInt("id_utente"), rs.getInt("id_ristorante"), rs.getString("testo"), rs.getString("data_ora"), rs.getString("data_nascosto"));
			avvisi.add(a);
		}
		return avvisi;
	}



	@Override
	public ArrayList<AvvisoNascostoVisto> getAvvisiViewedOf(Integer id_user) throws SQLException {
		ArrayList<AvvisoNascostoVisto> avvisi = new ArrayList<AvvisoNascostoVisto>();
		String query = "SELECT * FROM cronologia_lettura_avviso JOIN avviso ON cronologia_lettura_avviso.id_avviso = avviso.id_avviso WHERE cronologia_lettura_avviso.id_utente = " + id_user;
		ResultSet rs = db.getStatement().executeQuery(query);
		while(rs.next()) {
			AvvisoNascostoVisto a = new AvvisoNascostoVisto(rs.getInt("id_avviso"), rs.getInt("id_utente"), rs.getInt("id_ristorante"), rs.getString("testo"), rs.getString("data_ora"), rs.getString("data_lettura"));
			avvisi.add(a);
		}
		return avvisi;
	}



	@Override
	public AvvisoNascostoVisto setAvvisoViewed(Integer id_avviso) throws SQLException {
		String query = "INSERT INTO cronologia_lettura_avviso (id_utente, id_avviso, data_lettura) VALUES (";
		return null;
	}
	

	
	

}
