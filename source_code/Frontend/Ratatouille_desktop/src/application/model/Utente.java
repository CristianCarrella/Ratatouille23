package application.model;

import java.util.Observable;


@SuppressWarnings("deprecation")
public class Utente extends Observable{
	private String token;
	private String tk_expiration_timestamp;
	private int id_utente;
	private String nome, cognome, data_nascita, email, ruolo; 
	private boolean isFirstAccess;
	private int aggiunto_da;
	private String data_aggiunta;
	private int id_ristorante;

	public Utente(int id, String nome, String cognome, String dataNascita, String email, String ruolo, boolean isFirstAccess, int aggiuntoDa, String dataAggiunta, int idRistorante, String token, String expirationTime) {
		this.id_utente = id;
		this.cognome = cognome;
		this.nome = nome;
		this.data_nascita = dataNascita;
		this.email = email;
		this.ruolo = ruolo;
		this.isFirstAccess = isFirstAccess;
		this.aggiunto_da = aggiuntoDa;
		this.data_aggiunta = dataAggiunta;
		this.id_ristorante = idRistorante;
		this.token = token;
		this.tk_expiration_timestamp = expirationTime;
	}

	public Utente() {}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTk_expiration_timestamp() {
		return tk_expiration_timestamp;
	}

	public void setTk_expiration_timestamp(String tk_expiration_timestamp) {
		this.tk_expiration_timestamp = tk_expiration_timestamp;
	}
	
}
