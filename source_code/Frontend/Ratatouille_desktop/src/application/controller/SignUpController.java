package application.controller;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SignUpController {
	@FXML
	TextField nomeField;
	@FXML
	PasswordField passwordField;
	@FXML
	DatePicker dataNascitaField;
	@FXML
	TextField cognomeField;
	@FXML
	TextField emailField;
	@FXML
	Label loginLabel;
	@FXML
	Label errorLabel;
	@FXML
	Button signUpButton;
	
	private Stage stage;
	private Scene scene;
	private Parent parent;
	
    @FXML
    public void initialize() {
    	loginLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
        	try {
				goToLoginScene();
			} catch (IOException e) {
				e.printStackTrace();
			}
        });
    }
    
    public void signUp(ActionEvent actionEvent) throws IOException{
    	String nome = nomeField.getText();
    	String email = emailField.getText();
    	String password = passwordField.getText();
    	String cognome = cognomeField.getText();
    	String dataNascita = dataNascitaField.getValue().toString();
    	
    	//Richiesta al server
    	//Se la registrazione fallisce
    	errorLabel.setText("Errore");
    	errorLabel.setTextFill(Color.RED);
    	//Se la registrazione ha successo
    	errorLabel.setText("Registrazione avvenuta con successo");
    	errorLabel.setTextFill(Color.GREEN);
    	goToLoginScene();
    }
    
	private void goToLoginScene() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/Login.fxml"));
		stage = (Stage) loginLabel.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
