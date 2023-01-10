package com.ingsw.ratatouille;

import org.springframework.stereotype.Component;

@Component
public class User {
	// same order of database columns
	private int id_utente;
	private String nome, cognome, data_nascita, email, password, ruolo; 
	private boolean isFirstAccess;
	private int aggiunto_da;
	private String data_aggiunta;
	private int id_ristorante;
	private String token;
	private String tk_expiration_timestamp;
		
	public User(){}
	
	public User(int id, String nome, String cognome, String dataNascita, String email, String password, String ruolo, boolean isFirstAccess, int aggiuntoDa, String dataAggiunta, int idRistorante){
		this.id_utente = id;
		this.cognome = cognome;
		this.nome = nome;
		this.data_nascita = dataNascita;
		this.email = email;
		this.password = password;
		this.ruolo = ruolo;
		this.isFirstAccess = isFirstAccess;
		this.aggiunto_da = aggiuntoDa;
		this.data_aggiunta = dataAggiunta;
		this.id_ristorante = idRistorante;
	}

	public User(int id, String nome, String cognome, String dataNascita, String email, String password, String ruolo, boolean isFirstAccess, int aggiuntoDa, String dataAggiunta, int idRistorante, String token, String expirationTime){
		this.id_utente = id;
		this.cognome = cognome;
		this.nome = nome;
		this.data_nascita = dataNascita;
		this.email = email;
		this.password = password;
		this.ruolo = ruolo;
		this.isFirstAccess = isFirstAccess;
		this.aggiunto_da = aggiuntoDa;
		this.data_aggiunta = dataAggiunta;
		this.id_ristorante = idRistorante;
		this.token = token;
		this.tk_expiration_timestamp = expirationTime;
	}

	public int getIdUtente() {
		return id_utente;
	}
	
	
	public String getNome() {
		return nome;
	}

	
	public String getCognome() {
		return cognome;
	}


	public String getDataNascita() {
		return data_nascita;
	}

	
	public String getEmail() {
		return email;
	}

	
	public String getPassword() {
		return password;
	}

	
	public String getRuolo() {
		return ruolo;
	}

	
	public boolean isFirstAccess() {
		return isFirstAccess;
	}

	
	public int getAggiuntoDa() {
		return aggiunto_da;
	}

	
	public String getDataAggiunta() {
		return data_aggiunta;
	}


	public int getIdRistorante() {
		return id_ristorante;
	}

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

