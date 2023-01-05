package com.ingsw.ratatouille;

public class Menu {
	
	private String nome, descrizione, allergeni, nomeSecondaLingua, descrizioneSecondaLingua;
	private int idElemento, idRistorante, idCategoria;
	private float prezzo;
	private CategoriaMenu categoria = new CategoriaMenu();

	public Menu(int idElemento, int idRistorante, int idCategoria, String nome, float prezzo, String descrizione, String allergeni, String nomeSecondaLingua, String descrizioneSecondaLingua) {
		this.idElemento = idElemento;
		this.idRistorante = idRistorante;
		this.idCategoria = idCategoria;
		this.nome = nome;
		this.prezzo = prezzo;
		this.descrizione = descrizione;
		this.allergeni = allergeni;
		this.nomeSecondaLingua = nomeSecondaLingua;
		this.descrizioneSecondaLingua = descrizioneSecondaLingua;
	}

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getAllergeni() {
		return allergeni;
	}

	public String getNomeSecondaLingua() {
		return nomeSecondaLingua;
	}

	public String getDescrizioneSecondaLingua() {
		return descrizioneSecondaLingua;
	}

	public int getIdElemento() {
		return idElemento;
	}

	public int getIdRistorante() {
		return idRistorante;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public float getPrezzo() {
		return prezzo;
	}

	
}
