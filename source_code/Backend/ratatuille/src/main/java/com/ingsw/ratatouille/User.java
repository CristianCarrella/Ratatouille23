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
		
	}
	
	public User(int id, String name, String surname){
		this.id = id;
		this.surname = surname;
		this.name = name;
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
		return email;
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

