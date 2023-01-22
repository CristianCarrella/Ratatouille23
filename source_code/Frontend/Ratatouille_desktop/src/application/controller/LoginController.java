package application.controller;



import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class LoginController {
	@FXML
	TextField email;
	@FXML
	PasswordField password;
	@FXML
	Label errorLabel;
	@FXML
	VBox layoutFields;
	
	private Stage stage;
	private Scene scene;
	private Parent parent;
	
	public LoginController() {}
	
	public void login(ActionEvent actionEvent) throws IOException {
		System.out.print(email.getText());
		System.out.print(password.getText());
		//richiesta al server
		//Se richiesta andata male
		errorLabel.setText("Errore");
		errorLabel.setTextFill(Color.RED);
		//Se richiesta andata bene
		errorLabel.setText("Login avvenuto con successo");
		errorLabel.setTextFill(Color.GREEN);
		errorLabel.setVisible(true);
		goToHomeScene(actionEvent);
	}
	
	private void goToHomeScene(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/Home.fxml"));
		stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
