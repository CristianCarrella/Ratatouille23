module ratatouille_desktop {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	opens application to javafx.graphics, javafx.fxml;
	opens application.controller to javafx.fxml;
}
