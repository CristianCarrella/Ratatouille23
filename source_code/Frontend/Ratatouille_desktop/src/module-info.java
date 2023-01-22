module ratatouille_desktop {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.graphics;
	requires java.sql;
	opens application to javafx.graphics, javafx.fxml;
	opens application.controller to javafx.fxml;
}
