package application.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import application.driver.UtenteDriver;
import application.model.Utente;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AccountController {
	boolean isInvisible = true;
	@FXML
	Button accountBtn, homeBtn, noticeBtn, menuBtn, personaleBtn, personalizzaBtn;
	@FXML
	ImageView sidebarBtn;
	@FXML
	TextField nomeInput, cognomeInput, mailInput;
	@FXML
	Button salvaBtn;
	@FXML
	Label errorLabel;
	
	private Stage stage;
	private Scene scene;
	private Parent parent;
	private Utente loggedUser = LoginController.loggedUser;
	private UtenteDriver utenteDriver = new UtenteDriver();
	
	public AccountController() {
		
	}
	
	@FXML
	public void initialize() {
		mostraDatiAccount();
	}
	
	public void mostraDatiAccount() {
		
		
		nomeInput.setText(loggedUser.getNome());
		cognomeInput.setText(loggedUser.getCognome());
		mailInput.setText(loggedUser.getEmail());		
	} 
	
	public void modificaProfilo(ActionEvent actionEvent) {
		String nome = nomeInput.getText().toString();
    	String email = mailInput.getText().toString();
    	String cognome = cognomeInput.getText().toString();
    	if(!(nome.isBlank() || cognome.isBlank() || email.isBlank())) {
			if(!utenteDriver.requestModifyAccountToServer(nome, email, cognome)) {
		    	errorLabel.setText("Errore");
		    	errorLabel.setTextFill(Color.RED);
			}else {
				loggedUser.setNome(nome);
				loggedUser.setCognome(cognome);
				loggedUser.setEmail(email);
		    	errorLabel.setText("Modifiche apportate con successo");
		    	errorLabel.setTextFill(Color.GREEN);
			}
		}else {
	    	errorLabel.setText("Compilare tutti i campi");
	    	errorLabel.setTextFill(Color.RED);
		}
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
	
}