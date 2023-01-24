package com.ingsw.DAOinterface;

import com.ingsw.ratatouille.Business;

public interface BusinessDAOint {
	
	public  Business getBusinessFromBusinessId(Integer idRistorante);
	public Business modifyBusinessInfo(Integer id_ristorante, String nome, String indirizzo, String telefono);
}
