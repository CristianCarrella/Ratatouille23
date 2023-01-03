package com.ingsw.ratatouille;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ingsw.ratatouille.DatabaseConnection;
import com.ingsw.DAOimplements.UserDAOimp;
import com.ingsw.DAOimplements.UserDAOimp2;
import com.ingsw.DAOinterface.UserDAOint;


@RestController
public class Controller {
	DatabaseConnection db = new DatabaseConnection();
	UserDAOint userDao = new UserDAOimp2();

	@GetMapping("/user")
	public ArrayList<User> getUser(){
		try {
			return userDao.getUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}