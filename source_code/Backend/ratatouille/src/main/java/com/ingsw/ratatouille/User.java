package com.ingsw.ratatouille;

public class User {
	private String name, surname, email, password, mobileNumber, role, dataNascita, aggiuntoDa, dataAggiunta;
	private int id;
	private boolean isFirstAccess;
		
	public User(){
		this.id = 0;
		this.name = "";
		this.surname = "";
		this.email = "";
		this.password = "";
		this.dataNascita = "";
		
	}
	
	public User(int id, String name, String surname){
		this.id = id;
		this.surname = surname;
		this.name = name;
	}

	public User(String nome, String cognome, String email2, String password2, String dataNascita2) {
		this.setName(nome);
		this.setSurname(cognome);
		this.setEmail(email2);
		this.setPassword(password2);
		this.setDataNascita(dataNascita2);		
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	public String getPassword() {
		return password;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getEmail() {
<<<<<<< Updated upstream
		return email;
=======
		return email;		
>>>>>>> Stashed changes
	}
	
	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getRole() {
		return role;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public String getAggiuntoDa() {
		return aggiuntoDa;
	}

	public String getDataAggiunta() {
		return dataAggiunta;
	}

	public boolean isFirstAccess() {
		return isFirstAccess;
	}
}

