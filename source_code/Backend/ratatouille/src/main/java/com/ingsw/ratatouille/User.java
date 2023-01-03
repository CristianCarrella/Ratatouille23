package com.ingsw.ratatouille;

public class User {
	// same order of database columns
	private int id_utente;
	private String nome, cognome, data_nascita, email, password, ruolo; 
	private boolean isFirstAccess;
	private int aggiunto_da;
	private String data_aggiunta;
	private int id_ristorante;
		
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



	public int getId_utente() {
		return id_utente;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getData_nascita() {
		return data_nascita;
	}

	public void setData_nascita(String data_nascita) {
		this.data_nascita = data_nascita;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public boolean isFirstAccess() {
		return isFirstAccess;
	}

	public void setFirstAccess(boolean isFirstAccess) {
		this.isFirstAccess = isFirstAccess;
	}

	public int getAggiunto_da() {
		return aggiunto_da;
	}

	public void setAggiunto_da(int aggiunto_da) {
		this.aggiunto_da = aggiunto_da;
	}

	public String getData_aggiunta() {
		return data_aggiunta;
	}

	public void setData_aggiunta(String data_aggiunta) {
		this.data_aggiunta = data_aggiunta;
	}

	public int getId_ristorante() {
		return id_ristorante;
	}

	public void setId_ristorante(int id_ristorante) {
		this.id_ristorante = id_ristorante;
	}



}

