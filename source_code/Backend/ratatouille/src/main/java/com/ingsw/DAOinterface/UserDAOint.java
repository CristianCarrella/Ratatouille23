package com.ingsw.DAOinterface;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ingsw.ratatouille.User;

public interface UserDAOint {
	public ArrayList<User> getUser() throws SQLException;
	public User createAdmin(String nome, String cognome, String email, String password, String dataNascita, int idRistorante) throws SQLException;
	public void createEmployee(String nome, String cognome, String email, String dataNascita, String ruolo, int idRistorante);
}
