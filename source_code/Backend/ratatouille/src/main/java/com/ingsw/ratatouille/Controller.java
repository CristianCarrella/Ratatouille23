package com.ingsw.ratatouille;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ingsw.ratatouille.DatabaseConnection;

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
	DatabaseConnection db = new DatabaseConnection();
	UserDAOint userDao = new UserDAOimp(db);
	LoggedUser loggedUser = null;
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
	public User modifyUserAccount(@RequestParam(required = true) String nome, String cognome, String dataNascita){
		return userDao.modifyUserNameSurnameDate(loggedUser, nome, cognome, dataNascita);		
	}

	@PostMapping("/signup/admin")
    public User createAdmin(@RequestParam (required = true) String nome, String cognome, String email, String password, String dataNascita, int idRistorante) {
		return userDao.createAdmin(nome, cognome, email, password, dataNascita, idRistorante);
	}
	
	//senza aver prima aver effettuato un /login non si potrà fare un signup/employee
	@PostMapping("/signup/newEmployee")
    public User createUser(@RequestParam (required = true) String nome, String cognome, String passwordTemporanea, String email, String dataNascita, String ruolo) {
		return userDao.createEmployee(nome, cognome, passwordTemporanea, email, dataNascita, ruolo, loggedUser);
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
		loggedUser = (LoggedUser)request.getSession().getAttribute("attributeToPass");
		System.out.print(loggedUser.getNome());
		return loggedUser;
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
	
	@PostMapping("/avviso/crea")
	public Avviso createNewAvviso(@RequestParam(required = true) Integer id_ristorante, String testo) {
		return avvisoDao.createNewAvviso(id_ristorante, testo, loggedUser);
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
	
	
	@GetMapping("/menu/ristorante/piatto")
	public ArrayList<Menu> getMenuPlateFromRestaurant(@RequestParam(required = true) Integer id_ristorante, String nomePiatto){
		return menuDao.getMenuPlateInRestaurant(id_ristorante, nomePiatto);
	}
	
	
	@GetMapping("/menu/piatto")
	public ArrayList<Menu> getMenuPlateByName(@RequestParam(required = true) String nomePiatto){
		return menuDao.getMenuPlate(nomePiatto);
	}
	
	
	@PostMapping("/menu/newPlate")
    public Menu createPlate(@RequestParam (required = true) Integer idRistorante, String categoria, String nome, float prezzo, String descrizione, String allergeni) {
		return menuDao.createPlate(idRistorante, categoria, nome, prezzo, descrizione, allergeni);
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
	
	@GetMapping("/business/nomeAttivita")
	public Business getBusinessFromBusinessId(@RequestParam(required = true) Integer id_ristorante){
		return businessDao.getBusinessFromBusinessId(id_ristorante);
	}
	
}