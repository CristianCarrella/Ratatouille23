package com.ingsw.ratatouille;

public class CategoriaMenu {
	
	private int idCategoria, idRistorante;
	private String nome;
	
	public CategoriaMenu() {
		this.getIdCategoria();
		this.getIdRistorante();
		this.getNome();
	}
	
	public CategoriaMenu(int idCategoria, int idRistorante, String nome){
		this.nome = nome;
		this.idCategoria = idCategoria;
		this.idRistorante = idRistorante;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public int getIdRistorante() {
		return idRistorante;
	}

	public String getNome() {
		return nome;
	}
	
	
}
