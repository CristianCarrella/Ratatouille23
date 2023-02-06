module ratatouille_desktop {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.graphics;
	requires java.sql;
	requires com.gluonhq.charm.glisten;
	requires org.json;
	requires org.apache.httpcomponents.httpclient;
	requires org.apache.httpcomponents.httpcore;
	requires org.apache.httpcomponents.httpmime;
	requires junit;
	opens application to javafx.graphics, javafx.fxml;
	opens application.controller to javafx.fxml;
}
