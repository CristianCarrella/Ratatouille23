package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import application.controller.OrdinaMenuController;
import application.model.CategoriaMenu;

public class OrdinaMenuControllerTest {

    List<CategoriaMenu> listaSenzaDuplicati = new ArrayList<CategoriaMenu>();
    List<CategoriaMenu> listaVuota = null;
    List<CategoriaMenu> listaConDuplicati = new ArrayList<CategoriaMenu>();
    List<CategoriaMenu> listaConNull = new ArrayList<CategoriaMenu>();
    OrdinaMenuController controller;

    @Before
    public void init() {
        controller = new OrdinaMenuController();        
    }
    
    public OrdinaMenuControllerTest() {
    	CategoriaMenu primi = new CategoriaMenu();
    	primi.setNome("Primi");
    	CategoriaMenu secondi = new CategoriaMenu();
    	primi.setNome("Secondi");
    	CategoriaMenu contorni = new CategoriaMenu();
    	primi.setNome("Contorni");
    	CategoriaMenu bevande = new CategoriaMenu();
    	primi.setNome("Bevande");
    	riempiLista1(primi, secondi, contorni, bevande);
    	riempiLista3(primi, secondi, bevande);
    	riempiLista4(primi, contorni, bevande);
    }
    
    private void riempiLista1(CategoriaMenu primi, CategoriaMenu secondi, CategoriaMenu contorni, CategoriaMenu bevande) {
    	listaConDuplicati.add(primi);
    	listaConDuplicati.add(secondi);
    	listaConDuplicati.add(primi);
    	listaConDuplicati.add(bevande);
    }
    
    private void riempiLista3(CategoriaMenu primi, CategoriaMenu secondi, CategoriaMenu bevande) {
    	listaConDuplicati.add(primi);
    	listaConDuplicati.add(secondi);
    	listaConDuplicati.add(primi);
    	listaConDuplicati.add(bevande);
    }
    
    private void riempiLista4(CategoriaMenu primi, CategoriaMenu contorni, CategoriaMenu bevande) {
    	listaConDuplicati.add(primi);
    	listaConDuplicati.add(null);
    	listaConDuplicati.add(primi);
    	listaConDuplicati.add(bevande);
    }

    @Test
    public void parolaPresenteInListaCategoriaSenzaDuplicati() {
        CategoriaMenu categoriaMenu = controller.removeFromListCategoria("Primi", listaSenzaDuplicati);
        assertEquals("Primi", categoriaMenu.getNome());
    }
    
    @Test
    public void parolaNullInListaCategoriaSenzaDuplicati() {
        CategoriaMenu categoriaMenu = controller.removeFromListCategoria(null, listaSenzaDuplicati);
        assertEquals(null, categoriaMenu.getNome());
    }
    
    @Test
    public void parolaNonPresenteInListaCategoriaSenzaDuplicati() {
        CategoriaMenu categoriaMenu = controller.removeFromListCategoria("Dessert", listaSenzaDuplicati);
        assertEquals(null, categoriaMenu.getNome());
    }
    
    @Test
    public void parolaNullInListaCategoriaVuota() {
        CategoriaMenu categoriaMenu = controller.removeFromListCategoria(null, listaVuota);
        assertEquals(null, categoriaMenu.getNome());
    }
    
    @Test
    public void parolaPresenteInListaCategoriaConDuplicati() {
        CategoriaMenu categoriaMenu = controller.removeFromListCategoria("Primi", listaConDuplicati);
        assertEquals("Primi", categoriaMenu.getNome());
    }
    
    @Test
    public void parolaNonPresenteInListaCategoriaConNull() {
        CategoriaMenu categoriaMenu = controller.removeFromListCategoria("Dessert", listaConNull);
        assertEquals(null, categoriaMenu.getNome());
    }


}