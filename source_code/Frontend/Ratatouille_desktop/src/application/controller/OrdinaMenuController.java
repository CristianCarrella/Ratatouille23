package application.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import application.driver.MenuDriver;
import application.model.CategoriaMenu;
import application.model.Piatto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrdinaMenuController {
	public OrdinaMenuController() {	}
	@FXML
	ComboBox<String> comboboxInput;
	@FXML
	VBox vBoxLayout2;
	@FXML
	Label errorLabel, nomeCategoriaAttuale;
	@FXML
	Button aggiungBtn;
	@FXML
	Button categoriaSuccessivaBtn;
	@FXML
	Button categoriaPrecedenteBtn;
	@FXML
	HBox hBoxLayout, hBoxLayout1;
	@FXML
	BorderPane panelLayout;
	@FXML
	Button resetBtn;
	
	@FXML
	Button applica;
	
	private Stage stage;
	private Scene scene;
	private Parent parent;
	
	private MenuDriver menuDriver = new MenuDriver();
	private ArrayList<CategoriaMenu> categorieForUI;
	private ArrayList<CategoriaMenu> categorie;
	private Integer categoriaCounter = 1;
	private ArrayList<Piatto> piatti = null;
	private ArrayList<Piatto> allPiattiOfResturant = new ArrayList<Piatto>();
	private ArrayList<VBox> categorieVBoxs = new ArrayList<>();
	private Stack<VBox> savedStateLeft = new Stack<VBox>();
	private Stack<VBox> savedStateRight = new Stack<VBox>();
	private Integer pagesDiscovered = 1, numOfCategorie = 0;
	private ArrayList<CategoriaMenu> queueCategoria = new ArrayList<CategoriaMenu>();
	private Integer indexOfCategoria = 0;
	private boolean backPressed = false, resetPressed = false;
	
	
	public void backToMenu() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/MenuScene.fxml"));
		stage = (Stage) (errorLabel.getScene().getWindow());
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	private boolean existAlreadyAnMenu(List<Piatto> menu) {
		for (Piatto piatto : menu) {
			if(piatto.getPosizione() != null)
				return true;
		}
		return false;
	}
	
	@FXML
	public void initialize(){
		try {
			allPiattiOfResturant = menuDriver.getAllPiattiOfMenu();
			categorieForUI = menuDriver.getCategorieRistoranteLoggedUserWihoutChangeUI();
			categorie = new ArrayList<>(categorieForUI);
			numOfCategorie = categorieForUI.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(existAlreadyAnMenu(allPiattiOfResturant)) {
			VBox menuVBox = new VBox();
			menuVBox.setPrefWidth(250);
			menuVBox.setMinWidth(180);
			menuVBox.setMaxWidth(Double.MAX_VALUE);
			Label spacingLabel = new Label();
			menuVBox.getChildren().add(spacingLabel);
			Label oldMenuLabel = new Label("MENU ATTUALE");
			oldMenuLabel.setMaxWidth(Double.MAX_VALUE);
			oldMenuLabel.setAlignment(Pos.CENTER);
			menuVBox.getChildren().add(oldMenuLabel);
			Collections.sort(allPiattiOfResturant, (o1, o2) -> o1.getPosizione().compareTo(o2.getPosizione()));
			for(CategoriaMenu categoriaMenu : categorie) {
				if(categoriaMenu.getPosizione() != 0) {
					Label categoriaLabel = new Label(categoriaMenu.getNome());
					categoriaLabel.setStyle("-fx-text-fill: #003F91");
					menuVBox.getChildren().add(categoriaLabel);
					for(Piatto piatto : allPiattiOfResturant) {
						if(categoriaMenu.getIdCategoria() == piatto.getIdCategoria()) {
							System.out.println(piatto.getPosizione());
							if(piatto.getPosizione() != 0) {
								Label piattoLabel = new Label(piatto.getNome());
								menuVBox.getChildren().add(piattoLabel);
							}
						}
					}
				}
			}
			hBoxLayout1.getChildren().add(menuVBox);
		}
		
		for (CategoriaMenu categoriaMenu : categorieForUI) {
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
			if(resetPressed) {
				resetPressed = false;
				queueCategoria.remove((int)indexOfCategoria);
			}
			resetBtn.setDisable(false);
			errorLabel.setText("");
			if(isSelectedSomethingInMainCombobox()) {
				
				if(!savedStateLeft.contains((VBox)panelLayout.getCenter()))
					savedStateLeft.add((VBox)panelLayout.getCenter());
				
				if (savedStateLeft.size() < numOfCategorie) {
					indexOfCategoria++;
					if(backPressed) {
						if(savedStateRight.size() > 0) {
							panelLayout.setCenter(savedStateRight.pop());
						}
						else {
							backPressed = false;
							pagesDiscovered++;
							comboboxInput.getItems().clear();
							fillComboBoxWithListCategorie(comboboxInput, categorieForUI);
							panelLayout.setCenter(null);
							comboboxInput.setDisable(false);
							aggiungBtn.setDisable(false);	
						}
					} else { 
							pagesDiscovered++;
							comboboxInput.getItems().clear();
							comboboxInput.setPromptText("Categoria " + (indexOfCategoria + 1));
							fillComboBoxWithListCategorie(comboboxInput, categorieForUI);
							panelLayout.setCenter(null);
							comboboxInput.setDisable(false);
							aggiungBtn.setDisable(false);		
					}
				} else {
					errorLabel.setText("Non ci sono altre categorie");
				}
			}else {
				errorLabel.setText("Per andare avanti devi compilare la categoria");
			}
			nomeCategoriaAttuale.setText("Categoria " + (indexOfCategoria + 1));
		});
		
		categoriaPrecedenteBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
			if(isSelectedSomethingInMainCombobox()){
				if(resetPressed) {
					resetPressed = false;
					queueCategoria.remove((int)indexOfCategoria);
				}
				resetBtn.setDisable(false);
				errorLabel.setText("");
				backPressed = true;
				if(!savedStateRight.contains((VBox)panelLayout.getCenter()))
					savedStateRight.add((VBox)panelLayout.getCenter());
				if(savedStateLeft.size() > 0) {
					indexOfCategoria--;
					panelLayout.setCenter(savedStateLeft.pop());
					comboboxInput.setDisable(true);
					aggiungBtn.setDisable(true);				
				}else {
					errorLabel.setText("Non ci sono altre categorie");
				}
			}else {
				errorLabel.setText("Per andare avanti devi compilare la categoria");
			}
			nomeCategoriaAttuale.setText("Categoria " + (indexOfCategoria + 1));
		});
		
		resetBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
			if(isSelectedSomethingInMainCombobox()){
				comboboxInput.getItems().clear();
				if(queueCategoria.size() > 0) {
					resetPressed = true;
					CategoriaMenu actualCategoria = queueCategoria.get(indexOfCategoria);
					VBox vBox = findVBoxContainsCategoria(actualCategoria.getNome());
					if(vBox != null) {
						vBox.getChildren().clear();
						categorieForUI.add(actualCategoria);
					}
					for (Iterator iterator = queueCategoria.iterator(); iterator.hasNext();) {
						CategoriaMenu categoriaMenu = (CategoriaMenu) iterator.next();
						System.out.println(categoriaMenu.getNome());
					}
					
				}
				fillComboBoxWithListCategorie(comboboxInput, categorieForUI);
				
				panelLayout.setCenter(null);
				comboboxInput.setDisable(false);
				aggiungBtn.setDisable(false);
			} else {
				errorLabel.setText("Ancora non hai compilato niente");
			}
		});
		
		applica.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
			menuDriver.requestDeleteMenuSorting();
			int posizioneCategoria = 1, posizionePiatto = 1;
			for (VBox vBox : categorieVBoxs) {
				posizionePiatto = 1;
				if(!vBox.getChildren().isEmpty()) {
					Label nomeCategoria = (Label) vBox.getChildren().get(0);
					Integer idCategoria = getCategoriaIdFromNome(nomeCategoria.getText());
					menuDriver.requestUpdatePositionCategoria(idCategoria, posizioneCategoria);
					for(int i = 1; i < vBox.getChildren().size(); i++) {
						Label nomePiatto = (Label) vBox.getChildren().get(i);
						Integer idPiatto = getPiattoIdFromNome(nomePiatto.getText());
						System.out.println(idPiatto);
						menuDriver.requestUpdatePositionInMenuPiatto(idPiatto, posizionePiatto);
						posizionePiatto++;
					}
				}
				posizioneCategoria++;
			}
		});
		
		
		
	}

	private Integer getCategoriaIdFromNome(String nomeInput) {
		for(CategoriaMenu categoriaMenu : categorie) {
			if(categoriaMenu.getNome().equals(nomeInput)) {
				return categoriaMenu.getIdCategoria();
			}
		}
		return null;
	}
	
	private Integer getPiattoIdFromNome(String nomeInput) {
		for(Piatto piatto : allPiattiOfResturant) {
			if(piatto.getNome().equals(nomeInput)) {
				return piatto.getIdElemento();
			}
		}
		return null;
	}

	private boolean isSelectedSomethingInMainCombobox() {
		return !(comboboxInput.getSelectionModel().getSelectedItem() == null && !comboboxInput.isDisabled());
	}
	
	public void primaCategoriaScelta() throws Exception {
		ComboBox<String> comboBox = new ComboBox<String>();
		String categoriaScelta = comboboxInput.getSelectionModel().getSelectedItem();	
		
		if(categoriaScelta == null) {
			comboboxInput.getSelectionModel().select(0);
			categoriaScelta = comboboxInput.getSelectionModel().getSelectedItem();
			System.out.println(categoriaScelta);
			generateInputStructureForSortOfMenu(comboBox, categoriaScelta); 
		} else {
			generateInputStructureForSortOfMenu(comboBox, categoriaScelta); 
		}
		
	}

	private void generateInputStructureForSortOfMenu(ComboBox<String> comboBox, String categoriaScelta) throws Exception {
		removeFromListCategoria(categoriaScelta, categorieForUI);
		Label categoria = new Label(categoriaScelta);
		categoria.setStyle("-fx-text-fill: #003F91");
		VBox categoriaVBox = new VBox();
		categoriaVBox.getChildren().add(categoria);
		categorieVBoxs.add(categoriaVBox);
		vBoxLayout2.getChildren().add(indexOfCategoria + 2, categoriaVBox);
		piatti = fillPiattiOfCategoria(categoriaScelta);
		VBox vBox = new VBox();
		panelLayout.setCenter(vBox);
		generatePiattoChooseCombobox(comboBox, vBox, categoriaVBox);
	}
	
	private VBox findVBoxContainsCategoria(String nomeCategoria) {
		for(VBox v : categorieVBoxs) {
			if(!v.getChildren().isEmpty()) {
				Label nomeCategoriaLabel = (Label) v.getChildren().get(0);
				if(nomeCategoriaLabel.getText().toString().contains(nomeCategoria)) {
					return v;
				}	
			}
		}
		return null;
	}

	private void fillComboBoxWithListPiatti(ComboBox<String> comboBox) {
		for (Piatto piatto : piatti) {
			comboBox.getItems().add(piatto.getNome());
		}
	}
	
	private void fillComboBoxWithListCategorie(ComboBox<String> comboBox, List<CategoriaMenu> listCategoria) {
		for (CategoriaMenu categoriaMenu : listCategoria) {
			comboBox.getItems().add(categoriaMenu.getNome());
		}
	}
	

	private void generatePiattoChooseCombobox(ComboBox<String> comboBox, VBox vBoxLayout, VBox categoriaVBox) {
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
					categoriaVBox.getChildren().add(piatto);
					generatePiattoChooseCombobox(new ComboBox<String>(), vBoxLayout, categoriaVBox);
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
				queueCategoria.add(categoria);
				iterator.remove();
			}
		}
	}

	private ArrayList<Piatto> fillPiattiOfCategoria(String categoriaScelta) throws Exception {
		ArrayList<Piatto> piatti = menuDriver.getPiattiFromCategorieWithoutChangeUI(categoriaScelta);
		return piatti;
	}
	
}


