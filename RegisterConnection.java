package databaseObject;

import modelData.User;
import databaseConnection.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterConnection {

    public static boolean userRegister(User user) {
        try (Connection registerCon = ConnectionDB.getConnection()) {
        	String query = "INSERT INTO MsUser (UserID, Username, Email, Password, Age, Gender, Country, PhoneNumber, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        	PreparedStatement ps = registerCon.prepareStatement(query);
        	ps.setString(1, user.getId());
        	ps.setString(2, user.getUsername());
        	ps.setString(3, user.getEmail());
        	ps.setString(4, user.getPassword());  // assuming password is included in User class
        	ps.setInt(5, user.getAge());
        	ps.setString(6, user.getGender());
        	ps.setString(7, user.getCountry());
        	ps.setString(8, user.getPhoneNumber());
        	ps.setString(9, user.getRole());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
