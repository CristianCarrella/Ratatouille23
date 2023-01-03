package com.ingsw.DAOinterface;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ingsw.ratatouille.User;

public interface UserDAOint {
	public ArrayList<User> getUser() throws SQLException;
	public User createAdmin(String nome, String cognome, String email, String password, String dataNascita, int idRistorante) throws SQLException;
	public User createEmployee(String nome, String cognome, String passwordTemporanea, String email, String dataNascita, String ruolo, User loggedUser);
	public ArrayList<User> getUserOfResturant(int id_ristorante) throws SQLException;
	public User getUserById(Integer id) throws SQLException;
	public User login(String email, String password) throws SQLException;
	public User verifyEmployee(String nome, String cognome, String email, String dataNascita);
}
