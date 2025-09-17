package interfaceApp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import databaseObject.RegisterConnection;
import databaseConnection.ConnectionDB;
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
import modelData.User;

public class RegisterScene {
	Scene scRegisterDvCO;
	BorderPane bpRegisterDvCO;
	GridPane gpRegisterDvCO;
	Stage stRegisterDvCO;
	VBox vbRegisterDvCO;
	ConnectionDB registerCon;
	HBox genderBox, signinHyperlink, registerButtonBox;
	
	Label registerLabel, usernameLabel, emailRegisLabel, passRegisLabel, confPassLabel, ageLabel, genderLabel, countryLabel, phoneNumLabel, signinLabel;
	TextField usernameField, emailRegisField, phoneNumField;
	PasswordField passRegisField, confPassField;
	Spinner<Integer> ageSpinner;
	RadioButton rbMale, rbFemale;
	ToggleGroup genderRB;
	ComboBox<String> countryCB;
	CheckBox agreeTerms;
	Button registerButton;
	MenuBar menubar;
	Menu menu;
	MenuItem login, register;
	Hyperlink signIn;

	public void getRegisterScene(Stage stRegisterDvCO) {
		this.stRegisterDvCO = stRegisterDvCO;
		registerCon = ConnectionDB.getInstance();
		interfaceRegisterApp();
		positionLayoutRegister();
		componentActionRegister();
		stRegisterDvCO.setTitle("Dv.CO | Register");
		stRegisterDvCO.setScene(scRegisterDvCO);
		stRegisterDvCO.show();
	}

	public void interfaceRegisterApp() {
		bpRegisterDvCO = new BorderPane();
		gpRegisterDvCO = new GridPane();
		vbRegisterDvCO = new VBox();
		
		menubar = new MenuBar();
		menu = new Menu("Menu");
		login = new MenuItem("Login");
		register = new MenuItem("Register");
		register.setDisable(true);
		menu.getItems().addAll(login, register);
		menubar.getMenus().add(menu);
		
		registerLabel = new Label("REGISTER");
		usernameLabel = new Label("Username: ");
		emailRegisLabel = new Label("Email: ");
		passRegisLabel = new Label("Password: ");
		confPassLabel = new Label("Confirm Password: ");
		ageLabel = new Label("Age: ");
		genderLabel = new Label("Gender: ");
		countryLabel = new Label("Country: ");
		phoneNumLabel = new Label("Phone Number: ");
		
		usernameField = new TextField();
		usernameField.setPromptText("Username");
		emailRegisField = new TextField();
		emailRegisField.setPromptText("Email");
		passRegisField = new PasswordField();
		passRegisField.setPromptText("Password");
		confPassField = new PasswordField();
		confPassField.setPromptText("Confirm Password");
		ageSpinner = new Spinner<>(1, 100, 1);
		genderRB = new ToggleGroup();
		rbMale = new RadioButton("Male");
		rbFemale = new RadioButton("Female");
		rbMale.setToggleGroup(genderRB);
		rbFemale.setToggleGroup(genderRB);
		genderBox = new HBox(10, rbMale, rbFemale);
		countryCB = new ComboBox<>();
		countryCB.getItems().addAll("Indonesia", "Singapore", "Malaysia", "Thailand", "Philippines", "Vietnam", "Australia", "Seoul Korea", "Japan" );
		phoneNumField = new TextField();
		phoneNumField.setPromptText("Phone Number");
		
		agreeTerms = new CheckBox("Agree to terms and condition");
		
		registerButton = new Button("Register");
		registerButtonBox = new HBox(registerButton);
		
		signinLabel = new Label("Already have an account?");
		signIn = new Hyperlink("Sign in!");
		signinHyperlink = new HBox(5, signinLabel, signIn);
	}
	
	public void positionLayoutRegister() {
		gpRegisterDvCO.add(usernameLabel, 0, 0);
		gpRegisterDvCO.add(usernameField, 1, 0);
		gpRegisterDvCO.add(emailRegisLabel, 0, 1);
		gpRegisterDvCO.add(emailRegisField, 1, 1);
		gpRegisterDvCO.add(passRegisLabel, 0, 2);
		gpRegisterDvCO.add(passRegisField, 1, 2);
		gpRegisterDvCO.add(confPassLabel, 0, 3);
		gpRegisterDvCO.add(confPassField, 1, 3);
		gpRegisterDvCO.add(ageLabel, 0, 4);
		gpRegisterDvCO.add(ageSpinner, 1, 4);
		gpRegisterDvCO.add(genderLabel, 0, 5);
		gpRegisterDvCO.add(genderBox, 1, 5);
		gpRegisterDvCO.add(countryLabel, 0, 6);
		gpRegisterDvCO.add(countryCB, 1, 6);
		gpRegisterDvCO.add(phoneNumLabel, 0, 7);
		gpRegisterDvCO.add(phoneNumField, 1, 7);
		gpRegisterDvCO.add(agreeTerms, 1, 8);

		vbRegisterDvCO.getChildren().addAll(registerLabel, gpRegisterDvCO);
		vbRegisterDvCO.setAlignment(Pos.CENTER);
		
		signinHyperlink.setAlignment(Pos.CENTER);
		gpRegisterDvCO.add(signinHyperlink, 0, 10, 2, 1);
		
		registerButtonBox.setAlignment(Pos.CENTER_RIGHT); 
		gpRegisterDvCO.add(registerButtonBox, 1, 9);
		
		registerLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 24));
		vbRegisterDvCO.setSpacing(10);
		
		gpRegisterDvCO.setAlignment(Pos.CENTER);
		gpRegisterDvCO.setHgap(10);
		gpRegisterDvCO.setVgap(10);
		
		bpRegisterDvCO.setTop(menubar);
		bpRegisterDvCO.setCenter(vbRegisterDvCO);
		scRegisterDvCO = new Scene(bpRegisterDvCO, 620, 500);
	}
	
	public void componentActionRegister() {
		login.setOnAction(e -> {
			clearFields();
			LoginScene loginScDvCO = new LoginScene();
			loginScDvCO.getLoginScene(stRegisterDvCO);
		});
		
		registerButton.setOnAction(e -> actionHandleRegister());
		signIn.setOnAction(e -> {
			clearFields();
			LoginScene loginScDvCO = new LoginScene();
			loginScDvCO.getLoginScene(stRegisterDvCO);
		});
		
	}
	
	private void actionHandleRegister() {
		String username = usernameField.getText();
		String email = emailRegisField.getText();
		String password = passRegisField.getText();
		String confirmPassword = confPassField.getText();
		int age = ageSpinner.getValue();
		RadioButton selectedGender = (RadioButton) genderRB.getSelectedToggle();
        String gender = selectedGender != null ? selectedGender.getText() : "";
		String country = countryCB.getValue();
		String phone = phoneNumField.getText();
		boolean agree = agreeTerms.isSelected();

		if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                || gender.isEmpty() || country == null || phone.isEmpty() || !agreeTerms.isSelected()) {
			showAlertError(Alert.AlertType.ERROR, "Registration Failed", "All fields must be filled, and terms must be agreed!");
			return;
		}

		if (username.length() < 3 || username.length() > 15) {
			showAlertError(Alert.AlertType.ERROR, "Registration Failed", "Username must be between 3-15 characters.");
			return;
		}

		if (!email.endsWith("@gmail.com")) {
			showAlertError(Alert.AlertType.ERROR, "Registration Failed", "Email must end with '@gmail.com'.");
			return;
		}

		if (!validationPassword(password)) {
			showAlertError(Alert.AlertType.ERROR, "Registration Failed", "Password must be alphanumeric.");
			return;
		}

		if (!password.equals(confirmPassword)) {
			showAlertError(Alert.AlertType.ERROR, "Registration Failed", "Passwords do not match.");
			return;
		}

		if (age <= 13) {
			showAlertError(Alert.AlertType.ERROR, "Registration Failed", "Age must be older than 13 years.");
			return;
		}

		if (phone.length() < 12 || phone.length() > 12) {
			showAlertError(Alert.AlertType.ERROR, "Registration Failed", "Phone Number must be 12 characters.");
			return;
		}

		if (!agree) {
			showAlertError(Alert.AlertType.ERROR, "Registration Failed", "You must agree to the terms and conditions.");
			return;
		}

		String userId = generateUserId();

		User user = new User(userId, username, email, password, age, gender, country, phone, "User");
	    boolean regisSuccess = RegisterConnection.userRegister(user);

	    if (regisSuccess) {
	        showAlertSuccess(Alert.AlertType.INFORMATION, "Registration Success", "User registered successfully!");
	        LoginScene loginScDvCO = new LoginScene();
	        loginScDvCO.getLoginScene(stRegisterDvCO);
	    } else {
	        showAlertError(Alert.AlertType.ERROR, "Registration Failed", "An error occurred during registration. Please try again.");
	    }
	}
	
	private boolean validationPassword(String password) {
	    boolean hasLetter = false;
	    boolean hasDigit = false;

	    for (char c : password.toCharArray()) {
	    	if (Character.isLetter(c)) {
	            hasLetter = true;
	        } else if (Character.isDigit(c)) {
	            hasDigit = true;
	        }
	        if (hasLetter && hasDigit) {
	            return true;
	        }
	    }
	    return false;
	}


	private String generateUserId() {
		String query = "SELECT UserID FROM MsUser ORDER BY UserID DESC LIMIT 1";
		try (PreparedStatement ps = registerCon.preparedStatement(query); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				String uId = rs.getString("UserID");
				int numId = Integer.parseInt(uId.substring(2)) + 1;
				return String.format("US%03d", numId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "US001";
	}
	
	public void showAlertError(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText("Error");
		alert.setContentText(message);
		alert.show();
	}
	
	public void showAlertSuccess(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText("Success");
		alert.setContentText(message);
		alert.show();
	}

	private void clearFields() {
		usernameField.clear();
		emailRegisField.clear();
		passRegisField.clear();
		confPassField.clear();
		phoneNumField.clear();
		ageSpinner.getValueFactory().setValue(18);
		genderRB.selectToggle(null);
		countryCB.setValue(null);
		agreeTerms.setSelected(false);
	}
}
