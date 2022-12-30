package com.ingsw.DAOimplements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ingsw.DAOinterface.UserDAOint;
import com.ingsw.ratatouille.DatabaseConnection;
import com.ingsw.ratatouille.User;

public class UserDAOimp implements UserDAOint {
	DatabaseConnection db;
	
	public UserDAOimp(DatabaseConnection db){
		this.db = db;
	}
	
	@Override
	public ArrayList<User> getUser() throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String query = "SELECT * FROM users";
		ResultSet rs = db.getStatement().executeQuery(query);
		while(rs.next()) {
			User u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
			users.add(u);
		}
		return users;
	}
}
