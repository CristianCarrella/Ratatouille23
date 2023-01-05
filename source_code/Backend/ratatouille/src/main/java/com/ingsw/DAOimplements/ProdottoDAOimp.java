package com.ingsw.DAOimplements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ingsw.DAOinterface.ProdottoDAOint;
import com.ingsw.ratatouille.DatabaseConnection;
import com.ingsw.ratatouille.Menu;
import com.ingsw.ratatouille.Prodotto;
import com.ingsw.ratatouille.Prodotto.categoriaProdotto;

public class ProdottoDAOimp implements ProdottoDAOint {
	DatabaseConnection db;
	Prodotto prodotto;
	
	public ProdottoDAOimp(DatabaseConnection db){
		this.db = db;
	}

	
	public ArrayList<Prodotto> getDispensa() {
		ArrayList<Prodotto> prodotto = new ArrayList<Prodotto>();
		String query = "SELECT * FROM prodotto";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Prodotto prodottoTmp = new Prodotto(rs.getInt("id_prodotto"), rs.getInt("id_ristorante"), rs.getString("nome"), rs.getInt("stato"), rs.getString("descrizione"), rs.getFloat("prezzo"), rs.getFloat("quantita"), rs.getString("unita_misura"), rs.getString("categoria_prodotto"));
				prodotto.add(prodottoTmp);
			}
			return prodotto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public ArrayList<Prodotto> getDispensaFromRestaurant(Integer idRistorante) {
		ArrayList<Prodotto> prodotto = new ArrayList<Prodotto>();
		String query = "SELECT * FROM prodotto WHERE id_ristorante = " + idRistorante;
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Prodotto prodottoTmp = new Prodotto(rs.getInt("id_prodotto"), rs.getInt("id_ristorante"), rs.getString("nome"), rs.getInt("stato"), rs.getString("descrizione"), rs.getFloat("prezzo"), rs.getFloat("quantita"), rs.getString("unita_misura"), rs.getString("categoria_prodotto"));
				prodotto.add(prodottoTmp);
			}
			return prodotto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	public ArrayList<Prodotto> getDispensaProduct(Integer idRistorante, String categoria) {
		ArrayList<Prodotto> prodotto = new ArrayList<Prodotto>();
		String query = "SELECT * FROM prodotto WHERE id_ristorante = " + idRistorante + " AND categoria_prodotto LIKE '%" + categoria + "%'";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Prodotto prodottoTmp = new Prodotto(rs.getInt("id_prodotto"), rs.getInt("id_ristorante"), rs.getString("nome"), rs.getInt("stato"), rs.getString("descrizione"), rs.getFloat("prezzo"), rs.getFloat("quantita"), rs.getString("unita_misura"), rs.getString("categoria_prodotto"));
				prodotto.add(prodottoTmp);
			}
			return prodotto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}


	
	public ArrayList<Prodotto> getDispensaProductByName(String nomeProdotto) {
		ArrayList<Prodotto> prodotto = new ArrayList<Prodotto>();
		String query = "SELECT * FROM prodotto WHERE nome LIKE '%" + nomeProdotto + "%'";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Prodotto prodottoTmp = new Prodotto(rs.getInt("id_prodotto"), rs.getInt("id_ristorante"), rs.getString("nome"), rs.getInt("stato"), rs.getString("descrizione"), rs.getFloat("prezzo"), rs.getFloat("quantita"), rs.getString("unita_misura"), rs.getString("categoria_prodotto"));
				prodotto.add(prodottoTmp);
			}
			return prodotto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}



	public ArrayList<Prodotto> getAvaiableDispensaProduct(Integer idRistorante) {
		ArrayList<Prodotto> prodotto = new ArrayList<Prodotto>();
		String query = "SELECT * FROM prodotto WHERE stato > 0 AND id_ristorante = " + idRistorante;
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				Prodotto prodottoTmp = new Prodotto(rs.getInt("id_prodotto"), rs.getInt("id_ristorante"), rs.getString("nome"), rs.getInt("stato"), rs.getString("descrizione"), rs.getFloat("prezzo"), rs.getFloat("quantita"), rs.getString("unita_misura"), rs.getString("categoria_prodotto"));
				prodotto.add(prodottoTmp);
			}
			return prodotto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}


	public Prodotto createProduct(Integer idRistorante, String nome, Integer stato, String descrizione, float prezzo, float quantita, String unitaMisura, String categoria) {
		String query = null;
		
		for (int i = 0; i < prodotto.getCategories().size(); i++) {
			if (categoria.equals(prodotto.getCategories().get(i))) {
				query = "INSERT INTO prodotto (id_prodotto, id_ristorante, nome, stato, descrizione, prezzo, quantita, unita_misura, categoria_prodotto) VALUES (default, " + idRistorante + ", '" + nome + "', " + stato + ", '" + descrizione + "', " + prezzo + ", " + quantita + ", '" + unitaMisura + "', '" + categoria + "')";
				try {
					db.getStatement().executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
				
		return null;
	}

}
