package application.controller;

import java.io.IOException;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AvvisiController {
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
	TextArea testoAvviso;
	@FXML
	Button inviaBtn;

	
	private Stage stage;
	private Scene scene;
	private Parent parent;
	
	public AvvisiController() {
	
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