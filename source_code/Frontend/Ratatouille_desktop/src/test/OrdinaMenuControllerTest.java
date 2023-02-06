package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import application.controller.OrdinaMenuController;
import application.model.CategoriaMenu;

public class OrdinaMenuControllerTest {

	String [] listaSenzaDuplicati = {"Primi", "Secondi", "Contorni", "Bevande"};
	String [] listaVuota = null;
	String [] listaConDuplicati = {"Primi", "Secondi", "Primi", "Bevande"};
	String [] listaConNull = {"Primi", null, "Primi", "Bevande"};
	OrdinaMenuController controller;

	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Before
	public void init() {
		controller = new OrdinaMenuController();
	}
	
	@Test
	public void parolaPresenteInListaCategoriaSenzaDuplicati() {
		CategoriaMenu categoriaMenu = controller.removeFromListCategoria("Primi", listaSenzaDuplicati);
	}
	

}
