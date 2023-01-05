package com.ingsw.DAOimplements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ingsw.DAOinterface.CategoriaMenuDAOint;
import com.ingsw.ratatouille.CategoriaMenu;
import com.ingsw.ratatouille.DatabaseConnection;
import com.ingsw.ratatouille.Menu;

public class CategoriaMenuDAOimp implements CategoriaMenuDAOint {
	DatabaseConnection db;
	
	public CategoriaMenuDAOimp(DatabaseConnection db){
		this.db = db;
	}

	
	public ArrayList<CategoriaMenu> getMenuCategories() {
		ArrayList<CategoriaMenu> categoria = new ArrayList<CategoriaMenu>();
		String query = "SELECT * FROM categorie_menu";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				CategoriaMenu menuTmp = new CategoriaMenu(rs.getInt("id_categoria"), rs.getInt("id_ristorante"), rs.getString("nome"));
				categoria.add(menuTmp);
			}
			return categoria;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public ArrayList<CategoriaMenu> getMenuCategoriesFromRestaurant(int idRistorante){
		ArrayList<CategoriaMenu> categoria = new ArrayList<CategoriaMenu>();
		String query = "SELECT * FROM categorie_menu WHERE id_ristorante = " + idRistorante;
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				CategoriaMenu menuTmp = new CategoriaMenu(rs.getInt("id_categoria"), rs.getInt("id_ristorante"), rs.getString("nome"));
				categoria.add(menuTmp);
			}
			return categoria;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}


}
