package com.ingsw.DAOinterface;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ingsw.ratatouille.Avviso;
import com.ingsw.ratatouille.AvvisoNascostoVisto;

public interface AvvisoDAOint {

	public ArrayList<Avviso> getAvvisi() throws SQLException;
	public ArrayList<Avviso> getAvvisiOfResturant(Integer id_ristorante) throws SQLException;
	public ArrayList<AvvisoNascostoVisto> getAvvisiHiddenOf(Integer id_user) throws SQLException;
	public ArrayList<AvvisoNascostoVisto> getAvvisiViewedOf(Integer id_user) throws SQLException;
	public AvvisoNascostoVisto setAvvisoViewed(Integer id_avviso) throws SQLException;

}
