package application.controller;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import application.driver.MenuDriver;
import application.model.Avviso;
import application.model.CategoriaMenu;
import application.model.Piatto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class MenuController implements Observer{
	
	boolean isInvisible = true;
	@FXML
	Button accountBtn;
	@FXML
	Button homeBtn;
	@FXML
	Button noticeBtn;
	@FXML
	Button menuBtn;
	@FXML
	Button personaleBtn;
	@FXML
	Button personalizzaBtn;
	@FXML
	ImageView sidebarBtn;
	@FXML
	Label errorLabel;
	@FXML
	TextField inputField;
	@FXML
	ImageView filterBtn;
	@FXML
	VBox vBoxLayout;
	
	@FXML
	ArrayList<VBox> categorie = new ArrayList<VBox>();
	
	@FXML
	ArrayList<BorderPane> removedPiatti = new ArrayList<BorderPane>();
	ArrayList<String> removedPiattiIndex = new ArrayList<String>();
	ArrayList<Integer> removedPiattiIndex2 = new ArrayList<Integer>();
	
	private Stage stage;
	private Scene scene;
	private Parent parent;
	private MenuDriver menuDriver = new MenuDriver();

	public MenuController()  {
		
	}
	
	@FXML
	public void initialize() {
		menuDriver.requestMenuFromServer(this);
		inputField.setOnKeyPressed(event -> {
			
			if(event.getCode() == KeyCode.BACK_SPACE) {
				int i = 0;
				for(BorderPane removed : removedPiatti) {
					for(VBox v : categorie) {
						if(v.getId().equals(removedPiattiIndex.get(i))) {
							v.getChildren().add(1, removed);
						}
					}
					i++;
				}

				removedPiattiIndex.clear();
				removedPiattiIndex2.clear();
				removedPiatti.clear();
				
				System.out.print("ARRIVO QUI");
				
				searchPiatto();
			}
			if (event.getCode().isLetterKey()) {
				searchPiatto();
			}
			
		});
	}
	
	public void searchPiatto() {
		String input = inputField.getText().toString();
		if(!input.isBlank()) {
			ArrayList<Piatto> result = menuDriver.requestSearchPiatto(input);
			for(VBox v : categorie) {
				//PER CATEGORIA
//				HBox hBox = (HBox) v.getChildren().get(0);
//				Label label = (Label) hBox.getChildren().get(1);
//				System.out.print(label.getText())
				
				for(int i = 1; i < v.getChildren().size(); i++) { //itero gli elementi di una categoria presenti sull'interfaccia
					BorderPane pane = (BorderPane) v.getChildren().get(i);
					Label label = (Label) pane.getTop();
					String textLabel = label.getText().toString();
					if(!isPresentInResult(textLabel, result)) {
						BorderPane pane2 = pane;
						v.getChildren().remove(i);
						removedPiatti.add(pane2);
						removedPiattiIndex.add(v.getId());
						removedPiattiIndex2.add(i);
					}
				}
			}
		}
	}
	
	
	private boolean isPresentInResult(String nomePiatto, ArrayList<Piatto> piatti) {
		for(Piatto piatto : piatti) {
			if(nomePiatto.contains(piatto.getNome())) {
				return true;
			}
		}
		return false;
	}
	
	public void goToAggiungiPiatto(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/AggiungiModificaPiattoScene.fxml"));
		stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void goToHome(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/Home.fxml"));
		changeScene(actionEvent, root);
	}

	private void changeScene(ActionEvent actionEvent, Parent root) {
		stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		double prevWidth = stage.getWidth();
		double prevHeight = stage.getHeight();
		stage.setScene(scene);
		stage.setWidth(prevWidth);
		stage.setHeight(prevHeight);
		stage.show();
	}
	
	public void goToNotice(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/AvvisiScene.fxml"));
		changeScene(actionEvent, root);
	}
	
	public void goToMenu(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/MenuScene.fxml"));
		changeScene(actionEvent, root);
	}
	
	public void goToGestisciPersonale(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/RisorseUmaneScene.fxml"));
		changeScene(actionEvent, root);
	}
	
	public void goToPersonalizzaAttivita(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/PersonalizzazioneAttivitaScene.fxml"));
		changeScene(actionEvent, root);
	}
	
	public void goToAccount(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/AccountScene.fxml"));
		changeScene(actionEvent, root);
	}
	
	
	public void showSideBar() {
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), e -> {
			homeBtn.setVisible(!isInvisible);
		}));

		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> {
			noticeBtn.setVisible(!isInvisible);
		}));

		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(150), e -> {
			menuBtn.setVisible(!isInvisible);
		}));
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> {
			personaleBtn.setVisible(!isInvisible);
		}));
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(250), e -> {
			personalizzaBtn.setVisible(!isInvisible);
		}));
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300), e -> {
			personalizzaBtn.setVisible(!isInvisible);
		}));
		
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(350), e -> {
			accountBtn.setVisible(!isInvisible);
		}));
		timeline.play();
		isInvisible = !isInvisible;
	
	}
	
	private VBox findVBox(Integer idCategoria) {
		for(VBox v : categorie) {
			if(Integer.parseInt(v.getId()) == idCategoria) {
				return v;
			}
		}
		return null;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Piatto) {
			Piatto piatto = (Piatto) o;
			BorderPane containerPiatto = new BorderPane();
			containerPiatto.setStyle("-fx-border-color: #003F91; -fx-border-radius: 20; -fx-border-width: 2;");
			generateHeaderNotice(piatto, containerPiatto);
			generateBodyNotice(piatto, containerPiatto);
			ImageView imageView = generateLeftGarbageButtonNotice(containerPiatto, piatto.getIdElemento());
			generateRightPositionButtons(containerPiatto);
			VBox vBox = findVBox(piatto.getIdCategoria());
			vBox.getChildren().add(1, containerPiatto);
		}else if (o instanceof CategoriaMenu) {
			CategoriaMenu categoriaMenu = (CategoriaMenu) o;
			VBox v = findVBox(categoriaMenu.getIdCategoria());
			if (v == null) {
				v = new VBox();
			}
			v.setSpacing(10.0);
			categorie.add(v);
			v.setId(String.valueOf(categoriaMenu.getIdCategoria()));
			vBoxLayout.getChildren().add(v);
			Label nomeCategoriaLabel = new Label(categoriaMenu.getNome());
			nomeCategoriaLabel.setAlignment(Pos.CENTER);
			nomeCategoriaLabel.setMaxWidth(Double.MAX_VALUE);
			nomeCategoriaLabel.setTextAlignment(TextAlignment.CENTER);
			nomeCategoriaLabel.setPrefWidth(100);
			String path = "src/application/img/down_arrow.png";
			String path2 = "src/application/img/right_arrow.png";
			InputStream iStream = null;
			InputStream iStream2 = null;
			try {
				iStream = new FileInputStream(path);
				iStream2 = new FileInputStream(path2);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	        Image image = new Image(iStream);
	        Image image2 = new Image(iStream2);
			ImageView imageView = new ImageView();
			imageView.setFitWidth(25.0);
			imageView.setFitHeight(34.0);
			imageView.setPickOnBounds(true);
			imageView.setPreserveRatio(true);
			imageView.setImage(image);
			HBox h = new HBox();
			h.setMaxWidth(Float.MAX_VALUE);
			h.setAlignment(Pos.CENTER);
			h.getChildren().add(imageView);
			h.getChildren().add(nomeCategoriaLabel);
			v.getChildren().add(0, h);
			imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
				VBox vBox = findVBox(categoriaMenu.getIdCategoria());
				if(existPiattiInCategoria(vBox)) {
					vBox.getChildren().clear();
					imageView.setImage(image2);
					vBox.getChildren().add(0, h);
				} else {
					imageView.setImage(image);
					menuDriver.requestGetPiattiFromServer(MenuController.this, categoriaMenu.getNome());
				}

			});
		}
	}

	private void generateRightPositionButtons(BorderPane containerPiatto) {
		VBox vBox = new VBox();
		BorderPane pane1 = new BorderPane();
		pane1.setStyle("-fx-border-color: #003F91; -fx-border-width: 2; -fx-border-style: solid none none none;");
		String path = "src/application/img/sort_arrow_up.png";
		ImageView imageView = setImageView(path, 30.0, 30.0, 2.0, 10.0);
		imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
			
		});
		pane1.setCenter(imageView);
		
		BorderPane pane2 = new BorderPane();
		pane2.setStyle("-fx-border-color: #003F91; -fx-border-width: 2; -fx-border-style: solid none none none;");
		String path2 = "src/application/img/sort_arrow_down.png";
		ImageView imageView2 = setImageView(path2, 30.0, 30.0, 2.0, 10.0);
		imageView2.setTranslateY(5);
		imageView2.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
			
		});
		pane2.setCenter(imageView2);
		vBox.getChildren().add(pane1);
		vBox.getChildren().add(pane2);
		containerPiatto.setRight(vBox);
		containerPiatto.setAlignment(vBox, Pos.CENTER);
	}

	private ImageView setImageView(String path, Double width, Double height, Double x, Double y) {
		InputStream iStream = null;
		try {
			iStream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        Image image = new Image(iStream);
		ImageView imageView = new ImageView();
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		imageView.setLayoutX(x);
		imageView.setLayoutY(y);
		imageView.setPickOnBounds(true);
		imageView.setPreserveRatio(true);
		imageView.setImage(image);
		return imageView;
	}

	private boolean existPiattiInCategoria(VBox vBox) {
		return vBox.getChildren().size() > 1;
	}
	
	private ImageView generateLeftGarbageButtonNotice(BorderPane containerNotice, Integer idAvviso) {
		Pane pane = new Pane();
		pane.setStyle("-fx-border-color: #003F91; -fx-border-width: 2; -fx-border-style: solid none none none;");
		String path = "src/application/img/garbage.png";
        ImageView imageView = setImageView(path, 34.0, 60.0, 3.0, 23.0);
		imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
			if(menuDriver.requestDeletePiatto(MenuController.this, idAvviso)) {
				containerNotice.getChildren().clear();
				containerNotice.setPrefHeight(0);
				containerNotice.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 20;");
				Label label = new Label("Piatto eliminato");
				containerNotice.setCenter(label);
			} else {
				errorLabel.setText("Errore nella cancellazione del messaggio");
				errorLabel.setTextFill(Color.RED);
			}
        });
		pane.getChildren().add(imageView);
		containerNotice.setLeft(pane);
		containerNotice.setAlignment(imageView, Pos.CENTER);
		return imageView;
	}

	private void generateBodyNotice(Piatto piatto, BorderPane containerNotice) {
		Label body = new Label(piatto.getDescrizione());
		body.setAlignment(Pos.TOP_LEFT);
		body.setStyle("-fx-border-color: #003F91; -fx-border-style: solid solid none solid; -fx-border-width: 2; -fx-background-color: #FFF; -fx-background-radius: 20;");
		body.setPrefHeight(87.0);
		body.setPrefWidth(453.0);
		body.setMaxWidth(Double.MAX_VALUE);
		containerNotice.setCenter(body);
	}

	private void generateHeaderNotice(Piatto piatto, BorderPane containerNotice) {
		Label header = new Label(piatto.getNome() + " " + piatto.getPrezzo() + "€");
		header.setPrefHeight(17.0);
		header.setPrefWidth(453.0);
		header.setMaxWidth(Double.MAX_VALUE);
		header.setPadding(new Insets(0, 0, 0 , 10));
		containerNotice.setTop(header);
	}
}
