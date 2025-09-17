package databaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseConnection.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelData.Donut;
import modelData.User;

public class CustomerHomeConnection {
	
	public static ObservableList<Donut> getDonutList() {
        ObservableList<Donut> donutList = FXCollections.observableArrayList();
        String query = "SELECT * FROM MsDonut";

        try (Connection custConnect = ConnectionDB.getConnection();
             PreparedStatement ps = custConnect.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("DonutID");
                String name = rs.getString("DonutName");
                String description = rs.getString("DonutDescription");
                int price = rs.getInt("DonutPrice");
                donutList.add(new Donut(id, name, description, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return donutList;
    }
	
	public static boolean actionAddToCart(Donut donut, User user, int quantity) {
        try (Connection custConnect = ConnectionDB.getConnection()) {
        	String checkQuery = "SELECT * FROM cart WHERE UserID = ? AND DonutID = ?";
        	PreparedStatement psCheck = custConnect.prepareStatement(checkQuery);
        	psCheck.setString(1, user.getId());
        	psCheck.setString(2, donut.getId());
        	ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
            	int qtyCurrent = rs.getInt("Quantity");
            	int qtyTotal = qtyCurrent + quantity;
            	
            	String updateQuery = "UPDATE cart SET Quantity = ? WHERE UserID = ? AND DonutID = ?";
            		try (PreparedStatement psUpdate = custConnect.prepareStatement(updateQuery)) {
	            		psUpdate.setInt(1, qtyTotal);
	            		psUpdate.setString(2, user.getId());
	            		psUpdate.setString(3, donut.getId());
	            		psUpdate.executeUpdate();
            		}
            		
            } else {
            	String insertQuery = "INSERT INTO cart (UserID, DonutID, Quantity) VALUES (?, ?, ?)";
            	PreparedStatement psInsert = custConnect.prepareStatement(insertQuery);
            	psInsert.setString(1, user.getId()); 
                psInsert.setString(2, donut.getId()); 
                psInsert.setInt(3, quantity); 
            	psInsert.executeUpdate();
            }
            return true; 
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
