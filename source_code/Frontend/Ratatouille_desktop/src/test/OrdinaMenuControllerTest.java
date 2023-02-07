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
    List<CategoriaMenu> listaNull = null;
    List<CategoriaMenu> listaVuota = new ArrayList<CategoriaMenu>();;
    List<CategoriaMenu> listaConDuplicati = new ArrayList<CategoriaMenu>();
    List<CategoriaMenu> listaConNull = new ArrayList<CategoriaMenu>();
    List<CategoriaMenu> listaConSoloNull = new ArrayList<CategoriaMenu>();
    List<CategoriaMenu> listaConNullEPrimi = new ArrayList<CategoriaMenu>();
    OrdinaMenuController controller;

    @Before
    public void init() {
        controller = new OrdinaMenuController();        
    }
    
    public OrdinaMenuControllerTest() {
    	CategoriaMenu primi = new CategoriaMenu();
    	primi.setNome("Primi");
    	CategoriaMenu secondi = new CategoriaMenu();
    	secondi.setNome("Secondi");
    	CategoriaMenu contorni = new CategoriaMenu();
    	contorni.setNome("Contorni");
    	CategoriaMenu bevande = new CategoriaMenu();
    	bevande.setNome("Bevande");
    	riempiLista1(primi, secondi, contorni, bevande);
    	riempiLista3(primi, secondi, bevande);
    	riempiLista4(primi, contorni, bevande);
    }
    
    private void riempiLista1(CategoriaMenu primi, CategoriaMenu secondi, CategoriaMenu contorni, CategoriaMenu bevande) {
    	listaSenzaDuplicati.add(0, primi);
    	listaSenzaDuplicati.add(1, secondi);
    	listaSenzaDuplicati.add(2, contorni);
    	listaSenzaDuplicati.add(3, bevande);
    }
    
    private void riempiLista3(CategoriaMenu primi, CategoriaMenu secondi, CategoriaMenu bevande) {
    	listaConDuplicati.add(0, primi);
    	listaConDuplicati.add(1, secondi);
    	listaConDuplicati.add(2, primi);
    	listaConDuplicati.add(3, bevande);
    }
    
    private void riempiLista4(CategoriaMenu primi, CategoriaMenu contorni, CategoriaMenu bevande) {
    	listaConNull.add(0, primi);
    	listaConNull.add(1, null);
    	listaConNull.add(2, contorni);
    	listaConNull.add(3, bevande);
    }
    
    private void riempiLista5() {
    	listaConSoloNull.add(0, null);
    	listaConSoloNull.add(1, null);
    }
    
    private void riempiLista6(CategoriaMenu primi) {
    	listaConNullEPrimi.add(0, null);
    	listaConNullEPrimi.add(1, primi);
    	
    }

    //BlackBox
    @Test
    public void parolaPresenteInListaSenzaDuplicati() {
    	assertEquals("Primi", controller.removeFromListCategoria("Primi", listaSenzaDuplicati).getNome());
    }
    
    @Test
    public void parolaNullInListaSenzaDuplicati() {
        assertNull(controller.removeFromListCategoria(null, listaSenzaDuplicati));
    }
    
    @Test
    public void parolaNonPresenteInListaSenzaDuplicati() {
        assertNull(controller.removeFromListCategoria("Dessert", listaSenzaDuplicati));
    }
    
    @Test
    public void parolaNullInListaNull() {
        assertNull(controller.removeFromListCategoria(null, listaNull));
    }
    
    @Test
    public void parolaPresenteInListaConDuplicati() {
        assertEquals("Primi", controller.removeFromListCategoria("Primi", listaConDuplicati).getNome());
    }
    
    @Test
    public void parolaNonPresenteInListaConNull() {
        assertNull(controller.removeFromListCategoria("Dessert", listaConNull));
    }
    
    @Test
    public void parolaInListaVuota() {
        assertNull(controller.removeFromListCategoria("Dessert", listaVuota));
    }
    
    //Node Coverage
    @Test
    public void parolaInListaNull() {
    	assertNull(controller.removeFromListCategoria("Parola", listaNull));
    }
    
    //BranchCoverage
    @Test
	public void parolaInSecondaPosizioneInListaSenzaDuplicati() {
		assertEquals("Secondi", controller.removeFromListCategoria("Secondi", listaSenzaDuplicati).getNome());
    }
    
    @Test
	public void parolaDopoUnNullInListaConNull() {
		assertEquals("Contorni", controller.removeFromListCategoria("Contorni", listaConNull).getNome());
    }
    
    @Test
	public void parolaDopoUnNullInListaConNullEPrimi() {
		assertNull(controller.removeFromListCategoria("Primi", listaConNullEPrimi));
    }
    
    @Test
	public void parolaInListaConSoloNull() {
		assertNull(controller.removeFromListCategoria("Primi", listaConSoloNull));
    }

}