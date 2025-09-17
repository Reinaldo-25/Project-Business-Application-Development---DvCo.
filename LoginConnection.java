package databaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import databaseConnection.ConnectionDB;
import javafx.scene.control.Alert;
import main.Main;
import modelData.User;

public class LoginConnection {
	
	public static User userLogin(String email, String password) {
        try (Connection loginCon = ConnectionDB.getConnection()) {
            String query = "SELECT * FROM MsUser WHERE Email = ? AND Password = ?";
            PreparedStatement ps = loginCon.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Main.userLogin = new User(
                        rs.getString("UserID"), 
                        rs.getString("Username"), 
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getInt("Age"), 
                        rs.getString("Gender"), 
                        rs.getString("Country"),
                        rs.getString("PhoneNumber"), 
                        rs.getString("Role")
                );
                return Main.userLogin;
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Wrong credentials.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static void showAlert(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText("Error");
		alert.setContentText(message);
		alert.show();
	}
}

