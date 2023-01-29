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
import com.ingsw.ratatouille.LoggedUser;
import com.ingsw.ratatouille.User;


public class AvvisoDAOimp implements AvvisoDAOint{
	DatabaseConnection db;
	
	public AvvisoDAOimp(DatabaseConnection db) {
		this.db = db;
	}


	public ArrayList<Avviso> getAvvisi() {
		ArrayList<Avviso> avvisi = new ArrayList<Avviso>();
		String query = "SELECT a.id_avviso, u.id_utente, u.id_ristorante, a.testo, a.data_ora, u.nome FROM avviso AS a INNER JOIN utente AS u on u.id_utente = a.id_utente";
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
		String query = "SELECT * FROM avviso AS a INNER JOIN utente AS u on u.id_utente = a.id_utente WHERE a.id_ristorante = " + id_ristorante + " ORDER BY data_ora ASC";
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

	
	public AvvisoNascostoVisto setAvvisoViewed(Integer id_avviso, User loggedUser) {
		LocalDateTime now = LocalDateTime.now();
		try {
			String query = "INSERT INTO cronologia_lettura_avviso (id_utente, id_avviso, data_lettura) VALUES (" + loggedUser.getIdUtente() + ", " + id_avviso + ", '" + now + "')";
			db.getStatement().executeUpdate(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public AvvisoNascostoVisto setAvvisoNotViewed(Integer id_avviso, User loggedUser) {
		ResultSet rs;
		String query = "DELETE FROM cronologia_lettura_avviso WHERE id_utente = " + loggedUser.getIdUtente() + " AND id_avviso = " + id_avviso;
		try {
			db.getStatement().executeUpdate(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public AvvisoNascostoVisto setAvvisoNotHidden(Integer id_avviso, LoggedUser loggedUser) {
		String query = "DELETE FROM cronologia_nascosti_avviso WHERE id_utente = " + loggedUser.getIdUtente() + " AND id_avviso = " + id_avviso;
		try {
			db.getStatement().executeUpdate(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteAvviso(Integer idAvviso) {
		String query = "DELETE FROM avviso WHERE id_avviso = " + idAvviso;
		try {
			db.getStatement().executeUpdate(query);
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Integer getNumberOfAvvisiToRead(Integer idUtente, Integer idRistorante) {
		String query1 = "SELECT COUNT(*) AS num_messaggi_letti FROM cronologia_lettura_avviso WHERE id_utente = " + idUtente;
		String query2 = "SELECT COUNT(*) AS num_messaggi_ristorante FROM avviso WHERE id_ristorante = " + idRistorante;
		ResultSet rs;
		System.out.println("\n" + query1 +"\n " + query2 + "\n");
		Integer num_messaggi_letti = 0, num_messaggi_ristorante = 0;
		try {
			rs = db.getStatement().executeQuery(query1);
			while(rs.next())
				num_messaggi_letti = rs.getInt("num_messaggi_letti");
			rs = db.getStatement().executeQuery(query2);
			while(rs.next())
				num_messaggi_ristorante = rs.getInt("num_messaggi_ristorante");
			System.out.println(num_messaggi_ristorante - num_messaggi_letti);
			return  num_messaggi_ristorante - num_messaggi_letti;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}

	public AvvisoNascostoVisto getAvvisoNascosto(Integer id_avviso) {
		ResultSet rs;
		String query = "SELECT * FROM avviso JOIN cronologia_nascosti_avviso ON avviso.id_avviso = cronologia_nascosti_avviso.id_avviso WHERE avviso.id_avviso = " + id_avviso;
		try {
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				AvvisoNascostoVisto a = new AvvisoNascostoVisto(rs.getInt("id_avviso"), rs.getInt("id_utente") , rs.getInt("id_ristorante"),rs.getString("testo") , rs.getString("data_ora"), rs.getString("data_nascosto"));
				return a;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public AvvisoNascostoVisto setAvvisoHidden(Integer id_avviso, User loggedUser) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();
		ResultSet rs;
		String query = null;		
		try {
			query = "INSERT INTO cronologia_nascosti_avviso (id_utente, id_avviso, data_nascosto) VALUES (" + loggedUser.getIdUtente() + ", " + id_avviso + ", '" + now + "')";
			db.getStatement().executeUpdate(query);
			return getAvvisoNascosto(id_avviso);
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
		String autore = "";
		boolean isSupervisoreOrAdmin = false;
		
		try {
			query = "SELECT ruolo, nome FROM utente WHERE id_utente = " + loggedUser.getIdUtente() + " AND id_ristorante = " + id_ristorante;
			rs = db.getStatement().executeQuery(query);
			while(rs.next()) {
				String ruolo = rs.getString("ruolo");
				if(ruolo.equals("admin") || ruolo.equals("supervisore")) {
					isSupervisoreOrAdmin = true;
					autore = rs.getString("nome");
				}

			}
			
			if(isSupervisoreOrAdmin) {
				query = "INSERT INTO avviso (id_avviso, id_utente, id_ristorante, testo, data_ora) VALUES (default, " + loggedUser.getIdUtente() + ", " + id_ristorante + ", '" + testo + "', '" + now + "') ";
				db.getStatement().executeUpdate(query);
				
				query = "SELECT id_avviso FROM avviso WHERE id_utente = " + loggedUser.getIdUtente() + " AND id_ristorante = " + id_ristorante + " AND testo = '" + testo + "' AND  data_ora = '" + now + "'";
				rs = db.getStatement().executeQuery(query);
				while(rs.next()) {
					return new Avviso(rs.getInt("id_avviso"), loggedUser.getIdUtente(), id_ristorante, testo, now.toString(), autore);
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}






	

	
	

}
