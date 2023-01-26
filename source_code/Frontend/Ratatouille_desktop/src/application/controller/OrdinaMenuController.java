package application.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import application.driver.MenuDriver;
import application.model.CategoriaMenu;
import application.model.Piatto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OrdinaMenuController {
	public OrdinaMenuController() {	}
	@FXML
	ComboBox<String> comboboxInput;
	@FXML
	VBox vBoxLayout2;
	@FXML
	Label errorLabel;
	@FXML
	Button aggiungBtn;
	@FXML
	Button categoriaSuccessivaBtn;
	@FXML
	Button categoriaPrecedenteBtn;
	@FXML
	HBox hBoxLayout;
	@FXML
	BorderPane panelLayout;
	
	MenuDriver menuDriver = new MenuDriver();
	ArrayList<CategoriaMenu> categorie;
	Integer categoriaCounter = 1;
	ArrayList<Piatto> piatti = null;
	Stack<VBox> savedStateLeft = new Stack<VBox>();
	Stack<VBox> savedStateRight = new Stack<VBox>();
	Integer pagesDiscovered = 1, numOfCategorie = 0;
	boolean backPressed = false;
	
	@FXML
	public void initialize(){
		try {
			categorie = menuDriver.getCategorieRistoranteLoggedUserWihoutChangeUI();
			numOfCategorie = categorie.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (CategoriaMenu categoriaMenu : categorie) {
			comboboxInput.getItems().add(categoriaMenu.getNome());
		}
		comboboxInput.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			comboboxInput.setDisable(true);
			
		}); 
		
		aggiungBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
			aggiungBtn.setDisable(true);
			try {
				primaCategoriaScelta();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		categoriaSuccessivaBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
			savedStateLeft.add((VBox)panelLayout.getCenter());
			if(pagesDiscovered < numOfCategorie && backPressed) {
				panelLayout.setCenter(savedStateRight.pop());
			} else { 
				if(pagesDiscovered >= numOfCategorie) {
					panelLayout.setCenter(savedStateRight.pop());
				} else {
					pagesDiscovered++;
					fillComboBoxWithListCategorie(comboboxInput);
					panelLayout.setCenter(null);
					comboboxInput.setDisable(false);
					aggiungBtn.setDisable(false);		
				}
			}
		});
		
		categoriaPrecedenteBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
			backPressed = true;
			savedStateRight.add((VBox)panelLayout.getCenter());
			panelLayout.setCenter(savedStateLeft.pop());
			comboboxInput.setDisable(true);
			aggiungBtn.setDisable(true);
		});
		
		
	}
	
	public void primaCategoriaScelta() throws Exception {
		ComboBox<String> comboBox = new ComboBox<String>();
		String categoriaScelta = comboboxInput.getSelectionModel().getSelectedItem();	
		removeFromListCategoria(categoriaScelta, categorie);
		comboboxInput.getItems().clear();
		if(categoriaScelta == null) {
			errorLabel.setText("Errore");
		} else {
			Label categoria = new Label(categoriaScelta + " "  + categoriaCounter);
			categoriaCounter ++;
			categoria.setStyle("-fx-text-fill: #003F91");
			vBoxLayout2.getChildren().add(categoria);
			piatti = fillPiattiOfCategoria(categoriaScelta);
			VBox vBox = new VBox();
			panelLayout.setCenter(vBox);
			generatePiattoChooseCombobox(comboBox, vBox); 
		}
		
	}

	private void fillComboBoxWithListPiatti(ComboBox<String> comboBox) {
		for (Piatto piatto : piatti) {
			comboBox.getItems().add(piatto.getNome());
		}
	}
	
	private void fillComboBoxWithListCategorie(ComboBox<String> comboBox) {
		for (CategoriaMenu categoriaMenu : categorie) {
			comboBox.getItems().add(categoriaMenu.getNome());
		}
	}
	

	private void generatePiattoChooseCombobox(ComboBox<String> comboBox, VBox vBoxLayout) {
		if(piatti.size() != 0) {
			fillComboBoxWithListPiatti(comboBox);
			vBoxLayout.getChildren().add(comboBox);
			comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
				comboBox.setDisable(true);
				String piattoScelto = comboBox.getSelectionModel().getSelectedItem();
				removeFromListPiatto(piattoScelto, piatti);
				if(piattoScelto == null) {
					errorLabel.setText("Errore");
				}else {
					Label piatto = new Label(piattoScelto);
					vBoxLayout2.getChildren().add(piatto);
					generatePiattoChooseCombobox(new ComboBox<String>(), vBoxLayout);
				}
			});
		}else {
			errorLabel.setText("Non ci sono più elementi in questa categoria");
		}
	}

	private void removeFromListPiatto(String piattoScelto, List<Piatto> piatti) {
		for (Iterator iterator = piatti.iterator(); iterator.hasNext();) {
			Piatto piatto = (Piatto) iterator.next();
			if(piatto.getNome() == piattoScelto) {
				iterator.remove();
			}
		}
	}
	
	private void removeFromListCategoria(String categoriaScelta, List<CategoriaMenu> categorie) {
		for (Iterator iterator = categorie.iterator(); iterator.hasNext();) {
			CategoriaMenu categoria = (CategoriaMenu) iterator.next();
			if(categoria.getNome() == categoriaScelta) {
				iterator.remove();
			}
		}
	}

	private ArrayList<Piatto> fillPiattiOfCategoria(String categoriaScelta) throws Exception {
		ArrayList<Piatto> piatti = menuDriver.getPiattiFromCategorieWithoutChangeUI(categoriaScelta);
		return piatti;
	}
	
}
