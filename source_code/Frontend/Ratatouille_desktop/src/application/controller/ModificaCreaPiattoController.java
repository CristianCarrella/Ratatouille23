package application.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.driver.MenuDriver;
import application.model.CategoriaMenu;
import application.model.Piatto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ModificaCreaPiattoController {
	@FXML
	TextArea descrizioneInput, descrizioneSecondaLinguaInput;
	@FXML
	TextField nomePiattoInput, costoInput, allergeniInput, secondaLinguaInput, nomeSecondaLinguaInput, allergeniSecondaLinguaInput;
	@FXML
	ComboBox<String> categoriaInput;
	@FXML
	Label errorLabel;
	@FXML
	Button btnSalvaCambiamenti;
	
	Integer idPiatto = null;
	ArrayList<CategoriaMenu> categorie = new ArrayList<CategoriaMenu>();
	
	
	private Stage stage;
	private Scene scene;
	private Parent parent;
	
	
	private MenuDriver menuDriver = new MenuDriver();
	
	public ModificaCreaPiattoController() {	
		
	}
	
	public ModificaCreaPiattoController(Integer idPiatto) {
		this.idPiatto = idPiatto;
		System.out.print("ID PIATTO: " + idPiatto);
	}

	@FXML
	public void initialize() {
		categorie = menuDriver.getCategorieRistoranteLoggedUserWihoutChangeUI();
		fillComboBoxWithListCategorie(categoriaInput, categorie);
		categoriaInput.getSelectionModel().select(0);
		if(piattoIsUnderModification()) {
			Piatto piatto = menuDriver.getPiattoInfoFromServer(idPiatto);
			
			if(piatto != null) {
				nomePiattoInput.setText(piatto.getNome());
				costoInput.setText(String.valueOf(piatto.getPrezzo()));
				allergeniInput.setText(piatto.getAllergeni());
				descrizioneInput.setText(piatto.getDescrizione());
				descrizioneInput.setWrapText(true);
				
				nomeSecondaLinguaInput.setText(piatto.getNomeSecondaLingua());
				descrizioneSecondaLinguaInput.setText(piatto.getDescrizioneSecondaLingua());
				descrizioneSecondaLinguaInput.setWrapText(true);
				
				categoriaInput.getSelectionModel().select(findCategoriaOfPiatto(piatto.getIdCategoria()).getNome());
			}
		}
		
		btnSalvaCambiamenti.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
			salvaPiatto();
		});
	}
	
	public void backToMenu() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/MenuScene.fxml"));
		stage = (Stage) (errorLabel.getScene().getWindow());
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	private void fillComboBoxWithListCategorie(ComboBox<String> comboBox, List<CategoriaMenu> listCategoria) {
		for (CategoriaMenu categoriaMenu : listCategoria) {
			comboBox.getItems().add(categoriaMenu.getNome());
		}
	}

	private CategoriaMenu findCategoriaOfPiatto(Integer idCategoria) {
		for(CategoriaMenu categoriaMenu : categorie) {
			if (categoriaMenu.getIdCategoria() == idCategoria) {
				return categoriaMenu;
			}
		}
		return null;
	}
	
	
	private boolean piattoIsUnderModification() {
		return idPiatto != null;
	}
	
	
	private void salvaPiatto() {
		String nomePiatto = nomePiattoInput.getText();
		String costo = costoInput.getText();
		String descrizione = descrizioneInput.getText();
		String allergeni = allergeniInput.getText();
		String nomeSecondaLingua = nomeSecondaLinguaInput.getText();
		String descrizioneSecondaLingua = descrizioneSecondaLinguaInput.getText();
		String categoriaScelta = categoriaInput.getSelectionModel().getSelectedItem().toString();
		if (!(nomePiatto.isBlank() || costo.isBlank() || descrizione.isBlank())) {
			if(piattoIsUnderModification()) {
				if(menuDriver.modificaPiatto(nomePiatto, descrizione, costo, allergeni, nomeSecondaLingua, descrizioneSecondaLingua, categoriaScelta, idPiatto)) {
					errorLabel.setText("Piatto modificato con successo");
					errorLabel.setTextFill(Color.GREEN);
				}else {
					errorLabel.setText("Errore nella modifica del piatto");
					errorLabel.setTextFill(Color.RED);
				}
			}				
			else {
				if(menuDriver.creaPiatto(nomePiatto, descrizione, costo, allergeni, nomeSecondaLingua, descrizioneSecondaLingua, categoriaScelta)) {
					errorLabel.setText("Piatto creato con successo");
					errorLabel.setTextFill(Color.GREEN);
				}else {
					errorLabel.setText("Errore nella creazione del piatto");
					errorLabel.setTextFill(Color.RED);
				}
			}
		} else {
			errorLabel.setText("I campi nome, costo, descrizione e allergeni non possono essere vuoti");
		}
	}
}
