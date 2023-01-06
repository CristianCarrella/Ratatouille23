package com.ingsw.DAOimplements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ingsw.DAOinterface.MenuDAOint;
import com.ingsw.ratatouille.CategoriaMenu;
import com.ingsw.ratatouille.DatabaseConnection;
import com.ingsw.ratatouille.Menu;
import com.ingsw.ratatouille.User;

public class MenuDAOimp implements MenuDAOint {
	
	DatabaseConnection db;
	
	public MenuDAOimp(DatabaseConnection db){
		this.db = db;
	}
	

	public ArrayList<Menu> getMenu() {
		ArrayList<Menu> menu = new ArrayList<Menu>();
		String query = "SELECT * FROM elementi_menu";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Menu menuTmp = new Menu(rs.getInt("id_elemento"), rs.getInt("id_ristorante"), rs.getInt("id_categoria"), rs.getString("nome"), rs.getFloat("prezzo"), rs.getString("descrizione"), rs.getString("allergeni"), rs.getString("nome_seconda_lingua"), rs.getString("descrizione_seconda_lingua"));
				menu.add(menuTmp);
			}
			return menu;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<Menu> getMenuFromRestaurant(int idRistorante) {
		ArrayList<Menu> menu = new ArrayList<Menu>();
		String query = "SELECT * FROM elementi_menu WHERE id_ristorante = " + idRistorante;
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Menu menuTmp = new Menu(rs.getInt("id_elemento"), rs.getInt("id_ristorante"), rs.getInt("id_categoria"), rs.getString("nome"), rs.getFloat("prezzo"), rs.getString("descrizione"), rs.getString("allergeni"), rs.getString("nome_seconda_lingua"), rs.getString("descrizione_seconda_lingua"));
				menu.add(menuTmp);
			}
			return menu;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<Menu> getMenuCategories(Integer idRistorante, String categoria){
		ArrayList<Menu> menu = new ArrayList<Menu>();
		
		String query = "SELECT * FROM elementi_menu JOIN categorie_menu ON elementi_menu.id_categoria = categorie_menu.id_categoria WHERE elementi_menu.id_ristorante = " + idRistorante + " AND  categorie_menu.nome LIKE '%" + categoria + "%'";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Menu menuTmp = new Menu(rs.getInt("id_elemento"), rs.getInt("id_ristorante"), rs.getInt("id_categoria"), rs.getString("nome"), rs.getFloat("prezzo"), rs.getString("descrizione"), rs.getString("allergeni"), rs.getString("nome_seconda_lingua"), rs.getString("descrizione_seconda_lingua"));
				menu.add(menuTmp);
			}
			return menu;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}


	public ArrayList<Menu> getMenuPlateInRestaurant(Integer idRistorante, String nomePiatto) {
		ArrayList<Menu> menu = new ArrayList<Menu>();
		String query = "SELECT * FROM elementi_menu WHERE id_ristorante = " + idRistorante + " AND nome LIKE '%" + nomePiatto + "%'";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Menu menuTmp = new Menu(rs.getInt("id_elemento"), rs.getInt("id_ristorante"), rs.getInt("id_categoria"), rs.getString("nome"), rs.getFloat("prezzo"), rs.getString("descrizione"), rs.getString("allergeni"), rs.getString("nome_seconda_lingua"), rs.getString("descrizione_seconda_lingua"));
				menu.add(menuTmp);
			}
			return menu;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}


	public ArrayList<Menu> getMenuPlate(String nomePiatto) {
		ArrayList<Menu> menu = new ArrayList<Menu>();
		String query = "SELECT * FROM elementi_menu WHERE nome LIKE '%" + nomePiatto + "%'";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Menu menuTmp = new Menu(rs.getInt("id_elemento"), rs.getInt("id_ristorante"), rs.getInt("id_categoria"), rs.getString("nome"), rs.getFloat("prezzo"), rs.getString("descrizione"), rs.getString("allergeni"), rs.getString("nome_seconda_lingua"), rs.getString("descrizione_seconda_lingua"));
				menu.add(menuTmp);
			}
			return menu;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	public int getIdFromCategoryName(String categoria) {
		String query = null;
		int idCategoria = 0;
		
		query = "SELECT id_categoria FROM categorie_menu WHERE nome LIKE '%" + categoria + "%'";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next())
				idCategoria = rs.getInt("id_categoria");
			return idCategoria;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	
	public Menu createPlate(Integer idRistorante, String categoria, String nome, float prezzo, String descrizione, String allergeni) {
		String query = null;
		int idCategoria = 0;
		
		idCategoria = this.getIdFromCategoryName(categoria);
						
		query = "INSERT INTO elementi_menu (id_elemento, id_ristorante,	id_categoria, nome, prezzo, descrizione, allergeni, nome_seconda_lingua, descrizione_seconda_lingua) VALUES (default, " + idRistorante + ", " + idCategoria + ", " + prezzo + ", '" + descrizione + "', '" + allergeni + "', " + "NULL, NULL";
		try {
			db.getStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public Menu addSecondLanguage(Integer idRistorante, int idProdotto, String nomeSecondaLingua, String descrizoineSecondaLingua) {
		String query = null;
		
						
		query = "UPDATE elementi_menu SET nome_seconda_lingua = '" + nomeSecondaLingua + "', descrizione_seconda_lingua = '" + descrizoineSecondaLingua + "' WHERE id_prodotto = " + idProdotto;
		try {
			db.getStatement().executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
