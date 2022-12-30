package com.ingsw.DAOimplements;


import java.util.ArrayList;

import com.ingsw.DAOinterface.UserDAOint;
import com.ingsw.ratatouille.User;

public class UserDAOimp2 implements UserDAOint {

	public UserDAOimp2(){
	}
	
	@Override
	public ArrayList<User> getUser() {
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User(1, "a","asd"));
		return users;
	}
}