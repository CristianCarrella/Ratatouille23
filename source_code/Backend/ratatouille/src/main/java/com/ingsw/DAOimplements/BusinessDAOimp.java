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

	
	public String getBusinessFromBusinessId(Integer idRistorante) {
		String query = "SELECT * FROM ristorante WHERE id_ristorante = " + idRistorante;
		System.out.println(query);
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);			
			return rs.getString("nome");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;		
	}


}
