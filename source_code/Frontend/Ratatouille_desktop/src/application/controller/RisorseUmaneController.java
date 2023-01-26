package application.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.gluonhq.charm.glisten.control.ToggleButtonGroup;

import application.driver.UtenteDriver;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class RisorseUmaneController {

	boolean isInvisible = true;
	@FXML
	Button accountBtn, homeBtn, noticeBtn, menuBtn, personaleBtn, personalizzaBtn;
	@FXML
	ImageView sidebarBtn;
	@FXML
	TextField nomeInput, cognomeInput, emailInput, passwordInput;
	@FXML
	ToggleButtonGroup ruoloInput;
	@FXML
	Button invioBtn;
	@FXML
	Label errorLabel;
	@FXML
	RadioButton supervisoreRBtn, addettoSalaRBtn, addettoCucinaRBtn;
	@FXML
	DatePicker dataNascitaInput;
	
	private Stage stage;
	private Scene scene;
	private Parent parent;
	UtenteDriver utenteDriver = new UtenteDriver();

	public RisorseUmaneController() {}
	
	public void creaNuovoImpiegato() {
		if(!nomeInput.getText().toString().isBlank() && !cognomeInput.getText().toString().isBlank() && !emailInput.getText().toString().isBlank() && !passwordInput.getText().toString().isBlank() && !dataNascitaInput.getValue().toString().isBlank()) {			
			if(supervisoreRBtn.isSelected()) {
				errorLabel.setText("Dipendente inserito nel sistema");
		    	errorLabel.setTextFill(Color.GREEN);
				utenteDriver.addNewEmployee(nomeInput.getText().toString(), cognomeInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString(), dataNascitaInput.getValue().toString(), "supervisore");
				nomeInput.setText("");
				cognomeInput.setText("");
				emailInput.setText("");
				passwordInput.setText("");
				dataNascitaInput.setValue(null);
			}
			if(addettoSalaRBtn.isSelected()) {
				errorLabel.setText("Dipendente inserito nel sistema");
		    	errorLabel.setTextFill(Color.GREEN);
				utenteDriver.addNewEmployee(nomeInput.getText().toString(), cognomeInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString(), dataNascitaInput.getValue().toString(), "addetto_sala");
				nomeInput.setText("");
				cognomeInput.setText("");
				emailInput.setText("");
				passwordInput.setText("");
				dataNascitaInput.setValue(null);
			}
			if(addettoCucinaRBtn.isSelected()) {
				errorLabel.setText("Dipendente inserito nel sistema");
		    	errorLabel.setTextFill(Color.GREEN);
				utenteDriver.addNewEmployee(nomeInput.getText().toString(), cognomeInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString(), dataNascitaInput.getValue().toString(), "addetto_cucina");
				nomeInput.setText("");
				cognomeInput.setText("");
				emailInput.setText("");
				passwordInput.setText("");
				dataNascitaInput.setValue(null);
			}
			if(!(supervisoreRBtn.isSelected() || addettoSalaRBtn.isSelected() || addettoCucinaRBtn.isSelected())) {
				errorLabel.setText("Selezionare un ruolo per il dipendente");
		    	errorLabel.setTextFill(Color.RED);
			}
			addettoCucinaRBtn.setSelected(false);
			addettoSalaRBtn.setSelected(false);
			supervisoreRBtn.setSelected(false);
		} else {
			errorLabel.setText("Compilare tutti i campi");
	    	errorLabel.setTextFill(Color.RED);
		}
	}
	
	public void goToGestisciPersonaleScene(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/GestisciPersonaleScene.fxml"));
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
	
}
