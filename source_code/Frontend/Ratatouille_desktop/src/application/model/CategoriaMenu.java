package application.model;

import java.util.Observable;

import application.controller.MenuController;

@SuppressWarnings("deprecation")
public class CategoriaMenu extends Observable{
	
	private int idCategoria, idRistorante;
	private String nome;
	
	public CategoriaMenu() {}
	
	public CategoriaMenu(MenuController menuController, int idCategoria, int idRistorante, String nome){
		this.nome = nome;
		this.idCategoria = idCategoria;
		this.idRistorante = idRistorante;
		addObserver(menuController);
		setChanged();
		notifyObservers();
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
