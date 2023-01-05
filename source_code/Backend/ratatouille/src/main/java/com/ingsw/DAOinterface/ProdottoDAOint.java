package com.ingsw.DAOinterface;

import java.util.ArrayList;

import com.ingsw.ratatouille.Menu;
import com.ingsw.ratatouille.Prodotto;

public interface ProdottoDAOint {

	ArrayList<Prodotto> getDispensa();
	ArrayList<Prodotto> getDispensaFromRestaurant(Integer id_ristorante);
	ArrayList<Prodotto> getDispensaProduct(Integer id_ristorante, String categoria);
	ArrayList<Prodotto> getDispensaProductByName(String nomeProdotto);
	ArrayList<Prodotto> getAvaiableDispensaProduct(Integer id_ristorante);
	Prodotto createProduct(Integer idRistorante, String nome, Integer stato, String descrizione, float prezzo, float quantita, String unitaMisura, String categoria);

}
