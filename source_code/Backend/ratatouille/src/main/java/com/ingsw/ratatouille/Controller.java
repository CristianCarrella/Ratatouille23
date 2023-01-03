package com.ingsw.ratatouille;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ingsw.ratatouille.DatabaseConnection;

import jakarta.servlet.http.HttpServletRequest;

import com.ingsw.DAOimplements.UserDAOimp;
import com.ingsw.DAOinterface.UserDAOint;


@RestController
public class Controller {
	DatabaseConnection db = new DatabaseConnection();
	UserDAOint userDao = new UserDAOimp(db);
	User loggedUser = null;

	@GetMapping("/user")
	public ArrayList<User> getUser(@RequestParam(required = false) Integer id_ristorante){
		try {
			if(id_ristorante == null) 
				return userDao.getUser();
			else {
				return userDao.getUserOfResturant(id_ristorante);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/signup/admin")
    public User createAdmin(@RequestParam (required = true) String nome, String cognome, String email, String password, String dataNascita, int idRistorante) {
		try {
			return userDao.createAdmin(nome, cognome, email, password, dataNascita, idRistorante);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/signup/newEmployee")
    public User createUser(@RequestParam (required = true) String nome, String cognome, String passwordTemporanea, String email, String dataNascita, String ruolo) {
		return userDao.createEmployee(nome, cognome, passwordTemporanea, email, dataNascita, ruolo, loggedUser);
	}
	
	@PostMapping("/signup/employee")
    public User verifyUser(@RequestParam (required = true) String nome, String cognome, String email, String dataNascita) {
		return userDao.verifyEmployee(nome, cognome, email, dataNascita);
	}

	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Integer id){
		try {
			return userDao.getUserById(id);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@PostMapping("/login")
	public User checkLogin(@RequestParam(required = true) String email, String password){
		try {
			loggedUser = userDao.login(email, password);
			return loggedUser;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}



	
}