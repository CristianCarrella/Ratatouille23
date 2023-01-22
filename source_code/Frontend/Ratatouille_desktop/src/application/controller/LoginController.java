package application.controller;
import java.awt.event.MouseAdapter;
import java.io.IOException;

import application.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginController {
	@FXML
	TextField email;
	@FXML
	PasswordField password;
	@FXML
	Label errorLabel;
	@FXML
	Label signUp;
	@FXML
	VBox layoutFields;
	
	private Stage stage;
	private Scene scene;
	private Parent parent;
	private DatabaseConnection db;
	
	public LoginController() {}
	
//	public void requestToServer(){
//        try {
//            run(email, password);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void run(String email, String password) throws IOException {
//    	try {
//            URL url = new URL("https://jsonplaceholder.typicode.com/posts/1");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Accept", "application/json");
//
//            if (conn.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
//            }
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    (conn.getInputStream())));
//
//            String output;
//            String jsonString = "";
//            while ((output = br.readLine()) != null) {
//                jsonString += output;
//            }
//
//            JSONObject json = new JSONObject(jsonString);
//            System.out.println("JSON Response: " + json.toString());
//
//            conn.disconnect();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
	
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
		goToHomeScene(actionEvent);
	}
	
	@FXML
	public void initialize() {
		signUp.addEventFilter(MouseEvent.MOUSE_CLICKED, MouseEvent -> {
			try {
            	goToSignUpScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
	}
	
	public void goToSignUpScene() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/SignUp.fxml"));
		stage = (Stage) (signUp.getScene().getWindow());
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	private void goToHomeScene(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/fxmls/Home.fxml"));
		stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
}
