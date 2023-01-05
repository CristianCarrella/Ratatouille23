package com.ingsw.ratatouille;

import java.util.ArrayList;

import com.ingsw.ratatouille.Prodotto.categoriaProdotto;
import com.ingsw.ratatouille.Prodotto.unitaMisura;

public class Prodotto {
	
	private int idProdotto,	idRistorante, stato;
	private String nome, descrizione, unitaMisura, categoria;
	private float prezzo, quantita;
	
	public enum categoriaProdotto {Frututa, Verdura, Carne, Pesce, Uova, LatteDerivati, CerealiDerivati, Legumi, Altro};
	public enum unitaMisura {Kg, Litri};

	public Prodotto(int idProdotto, int idRistorante, String nome, int stato, String descrizione, float prezzo, float quantita, String unitaMisura, String categoria) {
		this.idProdotto = idProdotto;
		this.idRistorante = idRistorante;
		this.nome = nome;
		this.stato = stato;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.quantita = quantita;
		this.unitaMisura = unitaMisura;
		this.categoria = categoria;
		
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> categorie = new ArrayList<String>();
		
		for(categoriaProdotto categoria : categoriaProdotto.values()) {
			categorie.add(categoria.toString());
		}
		return categorie;
	}
}
