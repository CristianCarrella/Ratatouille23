package com.ingsw.DAOinterface;

import java.util.ArrayList;

import com.ingsw.ratatouille.CategoriaMenu;

public interface CategoriaMenuDAOint {


	public ArrayList<CategoriaMenu> getMenuCategories();
	public ArrayList<CategoriaMenu> getMenuCategoriesFromRestaurant(int idRistorante);
}
