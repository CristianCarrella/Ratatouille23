package com.ingsw.ratatouille;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

import com.ingsw.DAOimplements.AvvisoDAOimp;
import com.ingsw.DAOimplements.BusinessDAOimp;
import com.ingsw.DAOimplements.MenuDAOimp;
import com.ingsw.DAOimplements.ProdottoDAOimp;
import com.ingsw.DAOimplements.UserDAOimp;
import com.ingsw.DAOimplements.CategoriaMenuDAOimp;
import com.ingsw.DAOinterface.AvvisoDAOint;
import com.ingsw.DAOinterface.BusinessDAOint;
import com.ingsw.DAOinterface.CategoriaMenuDAOint;
import com.ingsw.DAOinterface.MenuDAOint;
import com.ingsw.DAOinterface.ProdottoDAOint;
import com.ingsw.DAOinterface.UserDAOint;


@RestController
public class Controller {
	DatabaseConnection db = DatabaseConnection.getInstance();;
	UserDAOint userDao = new UserDAOimp(db);
	AvvisoDAOint avvisoDao = new AvvisoDAOimp(db);
	MenuDAOint menuDao = new MenuDAOimp(db);
	CategoriaMenuDAOint categoriaMenuDao = new CategoriaMenuDAOimp(db);
	ProdottoDAOint prodottoDao = new ProdottoDAOimp(db);
	BusinessDAOint businessDao = new BusinessDAOimp(db);
	

	@GetMapping("/user")
	public ArrayList<User> getUser(@RequestParam(required = false) Integer id_ristorante){
		if(id_ristorante == null) 
			return userDao.getUser();
		else {
			return userDao.getUserOfResturant(id_ristorante);
		}
	}
	
	@PostMapping("/user/account")
	public User modifyUserAccount(@RequestParam(required = true) String nome, String cognome, String dataNascita, Integer idUtente){
		return userDao.modifyUserNameSurnameDate(idUtente, nome, cognome, dataNascita);
	}
	
	@PostMapping("/user/accountDesktop")
	public User modifyUserAccountDesktop(@RequestParam(required = true) String nome, String cognome, String email, Integer idUtente){
		return userDao.modifyUserNameSurnameEmail(idUtente, nome, cognome, email);
	}
	
	@PostMapping("/user/upgradeRole")
	public User upgradeUserRole(@RequestParam(required = true) Integer idUtente, String ruolo){
		return userDao.upgradeUserRole(idUtente, ruolo);		
	}
	
	@PostMapping("/user/downgradeRole")
	public User downgradeUserRole(@RequestParam(required = true) Integer idUtente, String ruolo, String ruoloLogged){
		return userDao.downgradeUserRole(ruoloLogged, idUtente, ruolo);
	}
	
	@PostMapping("/user/fire")
	public void fireUser(@RequestParam(required = true) int idUtente, String ruolo, String ruoloLogged){
		userDao.fireUser(ruoloLogged, idUtente, ruolo);
	}

	@PostMapping("/signup-admin")
    public User createAdmin(@RequestParam (required = true) String nome, String cognome, String email, String password, String dataNascita, String nomeAttivita) {
		return userDao.createAdmin(nome, cognome, email, password, dataNascita, nomeAttivita);
	}
	
	@PostMapping("/signup/newEmployee")
    public User createUser(@RequestParam (required = true) String nome, String cognome, String passwordTemporanea, String email, String dataNascita, String ruolo, Integer idUtente, Integer idRistorante) {
		return userDao.createEmployee(nome, cognome, passwordTemporanea, email, dataNascita, ruolo, idUtente, idRistorante);
	}
	
	@PostMapping("/verify")
    public User verifyUser(@RequestParam (required = true) String email, String nomeRistorante) {
		return userDao.verifyEmployee(email, nomeRistorante);
	}
	
	@PostMapping("/user/first-access")
    public User firstAccess(@RequestParam (required = true) Integer id_utente, String newPassword) {
		return userDao.firstAccess(id_utente, newPassword);
	}

	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable Integer id){
		return userDao.getUserById(id);
	}
	
	
	@PostMapping("/login")
	public User checkLogin(HttpServletRequest request, @RequestParam(required = true) String email, String password){
		return (LoggedUser)request.getSession().getAttribute("attributeToPass");
	}
	
	
	@GetMapping("/avvisi")
	public ArrayList<Avviso> getAvvisi(@RequestParam(required = false) Integer id_ristorante){
		if(id_ristorante == null) 
			return avvisoDao.getAvvisi();
		else {
			return avvisoDao.getAvvisiOfResturant(id_ristorante);
		}
	}		

	@GetMapping("/avvisi-hidden/{id_user}")
	public ArrayList<AvvisoNascostoVisto> getAvvisiHidden(@PathVariable Integer id_user){
		return avvisoDao.getAvvisiHiddenOf(id_user);
	}
	
	
	@GetMapping("/avvisi-viewed/{id_user}")
	public ArrayList<AvvisoNascostoVisto> getAvvisiViewed(@PathVariable Integer id_user){
		return avvisoDao.getAvvisiViewedOf(id_user);
	}
	
	@PostMapping("/avviso/segna-come-letto/{id_avviso}")
	public AvvisoNascostoVisto setAvvisoViewed(@PathVariable Integer id_avviso) {
		return avvisoDao.setAvvisoViewed(id_avviso, loggedUser);
	}
	
	@PostMapping("/avviso/segna-come-non-letto/{id_avviso}")
	public AvvisoNascostoVisto setAvvisoNotViewed(@PathVariable Integer id_avviso) {
		return avvisoDao.setAvvisoNotViewed(id_avviso, loggedUser);
	}
	
	@PostMapping("/avviso/segna-come-nascosto/{id_avviso}")
	public AvvisoNascostoVisto setAvvisoHidden(@PathVariable Integer id_avviso) {
		return avvisoDao.setAvvisoHidden(id_avviso, loggedUser);
	}
	
	@PostMapping("/avviso/segna-come-non-nascosto/{id_avviso}")
	public AvvisoNascostoVisto setAvvisoNotHidden(@PathVariable Integer id_avviso) {
		return avvisoDao.setAvvisoNotHidden(id_avviso, loggedUser);
	}

	@GetMapping("/avviso/numero-di-avvisi-da-leggere")
	public Integer getNumberOfAvvisiToRead(@RequestParam Integer id_utente, Integer id_ristorante) {
		return avvisoDao.getNumberOfAvvisiToRead(id_utente, id_ristorante);
	}
	
	@PostMapping("/avviso/crea")
	public Avviso createNewAvviso(@RequestParam(required = true) Integer id_ristorante, String testo) {
		return avvisoDao.createNewAvviso(id_ristorante, testo, loggedUser);
	}

	@PostMapping("/avviso/cancella")
	public boolean deleteAvviso(@RequestParam(required = true) Integer id_avviso) {
		return avvisoDao.deleteAvviso(id_avviso);
	}
	
	@GetMapping("/menu")
	public ArrayList<Menu> getMenu(@RequestParam(required = false) Integer id_ristorante){
		if(id_ristorante == null) 
			return menuDao.getMenu();
		else {
			return menuDao.getMenuFromRestaurant(id_ristorante);
		}
	}
	
	@GetMapping("/menu/categoria")
	public ArrayList<CategoriaMenu> getMenuCategories(@RequestParam(required = false) Integer id_ristorante){
		if(id_ristorante == null) 
			return categoriaMenuDao.getMenuCategories();
		else {
			return categoriaMenuDao.getMenuCategoriesFromRestaurant(id_ristorante);
		}
	}
	
	@GetMapping("/menu/categoria/piatti")
	public ArrayList<Menu> getMenuPlateByCategory(@RequestParam(required = true) Integer id_ristorante, String categoria){
		return menuDao.getMenuCategories(id_ristorante, categoria);
	}

	@PostMapping("/menu/piatto-update-posizione")
	public boolean updatePosizionePiatto(@RequestParam(required = true) Integer id_piatto, Integer posizione){
		return menuDao.updatePosizionePiatto(id_piatto, posizione);
	}

	@PostMapping("/menu/delete-ordine-menu-precedente")
	public boolean deleteSortingMenu(@RequestParam(required = true) Integer id_ristorante){
		return menuDao.deleteSortingMenu(id_ristorante) && categoriaMenuDao.deleteSortingMenu(id_ristorante);
	}

	@PostMapping("/categoria-update-posizione")
	public boolean updatePosizioneCategoria(@RequestParam(required = true) Integer id_categoria, Integer posizione){
		return categoriaMenuDao.updatePosizioneCategoria(id_categoria, posizione);
	}
	
	@GetMapping("/menu/ristorante/piatto")
	public ArrayList<Menu> getMenuPlateFromRestaurant(@RequestParam(required = true) Integer id_ristorante, String nomePiatto){
		return menuDao.getMenuPlateInRestaurant(id_ristorante, nomePiatto);
	}

	@GetMapping("/menu/ristorante/piatto/{id_piatto}")
	public Menu getPlateById(@PathVariable Integer id_piatto){
		return menuDao.getPiattoById(id_piatto);
	}

	@GetMapping("/menu/piatto")
	public ArrayList<Menu> getMenuPlateByName(@RequestParam(required = true) String nomePiatto){
		return menuDao.getMenuPlate(nomePiatto);
	}

	@PostMapping("/menu/newCategoria")
	public boolean createCategoria(@RequestParam(required = true) String nomeNuovaCategoria, Integer idRistorante){
		return categoriaMenuDao.createCategoria(idRistorante, nomeNuovaCategoria);
	}

	@PostMapping("/menu/newPlate")
    public boolean createPlate(@RequestParam (required = true) Integer idRistorante, String categoria, String nome, float prezzo, String descrizione, String allergeni, String nomeSecondaLingua, String descrizioneSecondaLingua) {
		return menuDao.createPlate(idRistorante, categoria, nome, prezzo, descrizione, allergeni, nomeSecondaLingua, descrizioneSecondaLingua);
	}

	@PostMapping("/menu/updatePlate/{id_piatto}")
	public boolean updatePlate(@PathVariable Integer id_piatto, @RequestParam (required = true) Integer idRistorante, String categoria, String nome, float prezzo, String descrizione, String allergeni, String nomeSecondaLingua, String descrizioneSecondaLingua) {
		return menuDao.updatePlate(id_piatto, idRistorante, categoria, nome, prezzo, descrizione, allergeni, nomeSecondaLingua, descrizioneSecondaLingua);
	}

	@PostMapping("/menu/deletePlate")
	public boolean deletePlate(@RequestParam (required = true) Integer idPiatto) {
		return menuDao.deletePlate(idPiatto);
	}
		
	
	@PostMapping("/menu/piatto/secondaLingua")
	public Menu addSecondLanguage (@RequestParam (required = true) Integer idRistorante, int idProdotto, String nomeSecondaLingua, String descrizoineSecondaLingua) {
		return menuDao.addSecondLanguage(idRistorante, idProdotto, nomeSecondaLingua, descrizoineSecondaLingua);
	}
	
	@GetMapping("/dispensa")
	public ArrayList<Prodotto> getDispensa(@RequestParam(required = false) Integer id_ristorante){
		if(id_ristorante == null) 
			return prodottoDao.getDispensa();
		else {
			return prodottoDao.getDispensaFromRestaurant(id_ristorante);
		}
	}
	
	@GetMapping("/dispensa/categoria")
	public ArrayList<Prodotto> getDispensaProductsByCategory(@RequestParam(required = true) Integer id_ristorante, String categoria){
		return prodottoDao.getDispensaProduct(id_ristorante, categoria);
	}
	
	@GetMapping("/dispensa/prodotto")
	public ArrayList<Prodotto> getDispensaProductByName(@RequestParam(required = true)Integer id_ristorante, String nomeProdotto){
		return prodottoDao.getDispensaProductByName(id_ristorante, nomeProdotto);
	}
	
	@GetMapping("/dispensa/prodotto/disponibili")
	public ArrayList<Prodotto> getAvaiableDispensaProduct(@RequestParam(required = true) Integer id_ristorante){
		return prodottoDao.getAvaiableDispensaProduct(id_ristorante);
	}
	
	@PostMapping("/dispensa/newProduct")
    public Prodotto createProduct(@RequestParam (required = true) Integer idRistorante, String nome, Integer stato, String descrizione, Double prezzo, Double quantita, String unitaMisura, String categoria) {
		return prodottoDao.createProduct(idRistorante, nome, stato, descrizione, prezzo, quantita, unitaMisura, categoria);
	}
	
	@PostMapping("/dispensa/modifyProduct")
    public Prodotto modifyProduct(@RequestParam (required = true) Integer idProdotto, String nome, Integer stato, String descrizione, Double prezzo, Double quantita, String unitaMisura, String categoria) {
		return prodottoDao.modifyProduct(idProdotto, nome, stato, descrizione, prezzo, quantita, unitaMisura, categoria);
	}
	
	@PostMapping("/dispensa/deleteProduct")
    public Prodotto deleteProduct(@RequestParam (required = true) Integer idProdotto) {
		return prodottoDao.deleteProduct(idProdotto);
	}
	
	@GetMapping("/error")
	public String getError() {
		return "errore";
	}
	
	@PostMapping("/logout")
	public String logout(@RequestParam (required = true) Integer idUtente) {
		return "logged out";
	}
	
	@GetMapping("/business/nomeAttivita")
	public Business getBusinessFromBusinessId(@RequestParam(required = true) Integer id_ristorante){
		return businessDao.getBusinessFromBusinessId(id_ristorante);
	}
	
	@PostMapping("/business")
	public Business modifyBusinessInfo(@RequestParam(required = true) Integer id_ristorante, String nome, String numeroTelefono, String indirizzo){
		return businessDao.modifyBusinessInfo(id_ristorante, nome, indirizzo, numeroTelefono);
	}
	
	@PostMapping("/business/image")
	public void saveBusinessImage(@RequestParam(required = true) MultipartFile image, String fileName){
		try {
			businessDao.saveBusinessImage(loggedUser, image, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping("/business/getImage")
	public String getBusinessImage(){
		return businessDao.getBusinessImage(loggedUser);
	}
	
}