package com.ingsw.DAOinterface;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ingsw.ratatouille.User;

public interface UserDAOint {
	public ArrayList<User> getUser() throws SQLException;
}
