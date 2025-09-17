package interfaceApp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import databaseConnection.ConnectionDB;
import databaseObject.LoginConnection;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.Main;
import modelData.User;

public class LoginScene {
	
	Scene scLoginDvCO;
	BorderPane bpLoginDvCO;
	GridPane gpLoginDvCO;
	VBox vbLoginDvCO;
	HBox loginButtonBox, signupHyperlink;
	Stage stLoginDvCO;
	ConnectionDB loginCon;
	
	Label loginLabel, emailLabel, passwordLabel, signupLabel;
	Hyperlink signUp;
	TextField emailField;
	PasswordField passwordField;
	Button loginButton;
	MenuBar menubar;
	Menu menu;
	MenuItem login, register;

	public void getLoginScene(Stage stLoginDvCO) {
		this.stLoginDvCO = stLoginDvCO;
		loginCon = ConnectionDB.getInstance();
		interfaceLoginApp();
		positionLayoutLogin();
		componentActionLogin();
		stLoginDvCO.setTitle("Dv.CO | Login");
		stLoginDvCO.setScene(scLoginDvCO);
		stLoginDvCO.show();
	}
	
	public void interfaceLoginApp() {
		bpLoginDvCO = new BorderPane();
		gpLoginDvCO = new GridPane();
		vbLoginDvCO = new VBox();
		
		menubar = new MenuBar();
		menu = new Menu("Menu");
		login = new MenuItem("Login");
		register = new MenuItem("Register");
		login.setDisable(true);
		menu.getItems().addAll(login, register);
		menubar.getMenus().add(menu);

		loginLabel = new Label("LOGIN");
		loginLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 24));
		emailLabel = new Label("Email        : ");
		passwordLabel = new Label("Password : ");
		emailField = new TextField();
		emailField.setPromptText("Email");
		passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		loginButton = new Button("Login");
		
		signupLabel = new Label("Don't have an account?");
		signUp = new Hyperlink("Sign up!");
		
		bpLoginDvCO.setTop(menubar);
		bpLoginDvCO.setCenter(vbLoginDvCO);
		scLoginDvCO = new Scene(bpLoginDvCO, 620, 400);
	}
	
	public void positionLayoutLogin() {
		gpLoginDvCO.add(emailLabel, 0, 1);
		gpLoginDvCO.add(emailField, 1, 1);
		gpLoginDvCO.add(passwordLabel, 0, 2);
		gpLoginDvCO.add(passwordField, 1, 2);
		gpLoginDvCO.setAlignment(Pos.CENTER);
		
		vbLoginDvCO.getChildren().addAll(loginLabel, gpLoginDvCO);
		vbLoginDvCO.setAlignment(Pos.CENTER);
		
		loginButtonBox = new HBox(loginButton);
		loginButtonBox.setAlignment(Pos.CENTER_RIGHT); 
		
		signupHyperlink = new HBox(5, signupLabel, signUp);
		signupHyperlink.setAlignment(Pos.CENTER);
		
		gpLoginDvCO.add(loginButtonBox, 1, 3);
		gpLoginDvCO.add(signupHyperlink, 0, 4, 2, 1);
		
		vbLoginDvCO.setSpacing(10);
		gpLoginDvCO.setHgap(10);
		gpLoginDvCO.setVgap(10);
	}
	
	public void componentActionLogin() {
		register.setOnAction(e -> {
			clearFields();
			RegisterScene registerScDvCO = new RegisterScene();
			registerScDvCO.getRegisterScene(stLoginDvCO);
		});
		
		loginButton.setOnAction(event -> {
			actionHandleLogin();
		});
		
		signUp.setOnAction(e -> {
			RegisterScene registerScDvCO = new RegisterScene();
			registerScDvCO.getRegisterScene(stLoginDvCO);
		});
	}

	private void actionHandleLogin() {
		String email = emailField.getText();
		String password = passwordField.getText();

		if (email.isEmpty() || password.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Login Failed", "Email and Password must be filled!");
			return;
		}

		String query = "SELECT * FROM MsUser WHERE Email = ? AND Password = ?";
		try (PreparedStatement ps = loginCon.preparedStatement(query)) {
			ps.setString(1, email);
			ps.setString(2, password);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {

					@SuppressWarnings("unused")
					User user = LoginConnection.userLogin(email, password);

					if (Main.userLogin.getRole().equals("Admin")) {
						AdminHomeScene adminScDvCO = new AdminHomeScene();
						adminScDvCO.getAdminHomeScene(stLoginDvCO);
					} else if (Main.userLogin.getRole().equals("User")) {
						CustomerHomeScene custScDvCO = new CustomerHomeScene();
						custScDvCO.getCustHomeScene(stLoginDvCO);
					}
					return;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		showAlert(Alert.AlertType.ERROR, "Login Failed", "Wrong credentials.");
	}
	
	public void showAlert(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText("Error");
		alert.setContentText(message);
		alert.show();
	}

	private void clearFields() {
		emailField.clear();
		passwordField.clear();
	}
}
