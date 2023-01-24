package application.controller;

import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import application.driver.UtenteDriver;
import application.model.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GestisciPersonaleController {
	
	private Stage stage;
	private Scene scene;
	private Parent parent;
	private Utente loggedUser = LoginController.loggedUser;
	private UtenteDriver utenteDriver = new UtenteDriver();
	private ArrayList<Utente> utenti = utenteDriver.showEmplyees();
	
	@FXML
	Label nomeCognomeLabel, emailLabel, ruoloLabel;
	@FXML
	ComboBox utentiComboBox;

	public GestisciPersonaleController() {}
	
	@FXML
	public void initialize() {
		for (Utente utente : utenti) {
			utentiComboBox.getItems().add("ID: " + utente.getIdUtente() + " - Nome: " + utente.getNome() + " " + utente.getCognome());
		}
	}
	
	public void mostraDatiUtente(){
		String datiUtente = utentiComboBox.getSelectionModel().getSelectedItem().toString();
		String id = datiUtente.substring(4, 6);
		id.replace(" ", "");
		System.out.println(id);
		
		for(Utente u : utenti) {
			if(id.equals(String.valueOf(u.getIdUtente()))) {
				String nomeCognome = u.getNome() + " " + u.getCognome();
				String email = u.getEmail();
				String ruolo = u.getRuolo().toString();
				mostraInformazioniUtente(nomeCognome, email, ruolo);
			}
		}
	}	
	
	public void goToRisorseUmaneScene(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/RisorseUmaneScene.fxml"));
		stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void mostraInformazioniUtente(String nomeCognome, String email, String ruolo) {
		nomeCognomeLabel.setText(nomeCognome);
		emailLabel.setText(email);
		ruoloLabel.setText(ruolo);
	}
	
	public void declassa(ActionEvent actionEvent) {
		//servono altri parametri
		if(loggedUser.getRuolo().equals("admin")) {
			if(ruoloLabel.getText().equals("supervisore")) {
				//chiede conferma e poi ruolo diventa addetto alla cucina
			}
		}
		if(ruoloLabel.getText().equals("addetto_cucina")) {
			//chiede conferma e poi ruolo diventa addetto alla sala
		}
		if(ruoloLabel.getText().equals("addetto_sala")) {
			//errorLabel da creare "Non puoi declassare un addetto alla sala, forse volevi sollevarlo dal suo incarico"
		}
		if(ruoloLabel.getText().equals("admin")) {
			//errorLabel da creare "Non puoi declassare un amministratore"
		}
	}
	
	public void promuovi(ActionEvent actionEvent) {
		//servono altri parametri
		if(ruoloLabel.getText().equals("addetto_cucina")) {
			//chiede conferma e poi ruolo diventa supervisore
		}
		if(ruoloLabel.getText().equals("addetto_sala")) {
			//chiede conferma e poi ruolo diventa addetto alla cucina
		}
		if(ruoloLabel.getText().equals("admin")) {
			//errorLabel da creare "Non puoi promuovere un amministratore, è già al vertice"
		}
		if(ruoloLabel.getText().equals("supervisore")) {
			//errorLabel da creare "Non puoi promuovere un supervisore, ci può essere un solo amministratore per attività"
		}
	}
	
	public void licenzia(ActionEvent actionEvent) {
		//servono altri parametri
		if(loggedUser.getRuolo().equals("admin")) {
			if(ruoloLabel.getText().equals("supervisore")) {
				//chiede conferma e poi licenzia
			}
		}
		if(ruoloLabel.getText().equals("addetto_cucina")) {
			//chiede conferma e poi licenzia
		}
		if(ruoloLabel.getText().equals("addetto_sala")) {
			//chiede conferma e poi licenzia
		}
		if(ruoloLabel.getText().equals("admin")) {
			//errorLabel da creare "Non puoi licenziare un amministratore"
		}
	}
	
}
