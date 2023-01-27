package com.ingsw.DAOinterface;

import java.util.ArrayList;

import com.ingsw.ratatouille.CategoriaMenu;
import com.ingsw.ratatouille.Menu;

public interface MenuDAOint {
	
	public ArrayList<Menu> getMenu();
	public ArrayList<Menu> getMenuFromRestaurant(int id_ristorante);
	public ArrayList<Menu> getMenuCategories(Integer idRistorante, String categoria);
	public ArrayList<Menu> getMenuPlateInRestaurant(Integer id_ristorante, String nomePiatto);
	public ArrayList<Menu> getMenuPlate(String nomePiatto);
	public Menu createPlate(Integer idRistorante, String categoria, String nome, float prezzo, String descrizione, String allergeni);
	public int getIdFromCategoryName(String categoria);
	public Menu addSecondLanguage(Integer idRistorante, int idProdotto, String nomeSecondaLingua, String descrizoineSecondaLingua);

	public boolean deletePlate(Integer idPiatto);

	public boolean updatePosizionePiatto(Integer idPiatto, Integer posizione);

	public boolean deleteSortingMenu(Integer idRistorante);
}
