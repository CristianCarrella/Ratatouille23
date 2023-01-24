package com.ingsw.DAOimplements;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ingsw.DAOinterface.BusinessDAOint;
import com.ingsw.ratatouille.Business;
import com.ingsw.ratatouille.DatabaseConnection;
import com.ingsw.ratatouille.LoggedUser;

public class BusinessDAOimp implements BusinessDAOint {
	DatabaseConnection db;
	
	public BusinessDAOimp(DatabaseConnection db) {
		this.db = db;
	}
	
	public Business getBusinessFromBusinessId(Integer idRistorante) {
		String query = "SELECT * FROM ristorante WHERE id_ristorante = " + idRistorante;
		ResultSet rs = null;
		Business b;
		try {
			rs = db.getStatement().executeQuery(query);	
			while(rs.next()) {
				b = new Business(rs.getInt("id_ristorante"), rs.getString("nome"), rs.getString("telefono"), rs.getString("indirizzo"), rs.getString("nome_immagine"), rs.getInt("id_proprietario"));
				return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;		
	}

	@Override
	public Business modifyBusinessInfo(Integer idRistorante, String nome, String indirizzo, String telefono) {
		String query = "UPDATE ristorante SET nome = '" + nome + "', telefono = '" + telefono + "', indirizzo = '" + indirizzo + "' WHERE id_ristorante = " + idRistorante;
		ResultSet rs = null;
		Business b;
		try {
			db.getStatement().executeUpdate(query);
			return getBusinessFromBusinessId(idRistorante);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}


}
