package main;

import interfaceApp.*;
import javafx.application.Application;
import javafx.stage.Stage;
import modelData.User;
import javafx.scene.control.Alert;

public class Main extends Application {
	public static User userLogin = null;

	@Override
	public void start(Stage stMain) {
		try {
			new LoginScene().getLoginScene(stMain);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void showAlert(Alert.AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
