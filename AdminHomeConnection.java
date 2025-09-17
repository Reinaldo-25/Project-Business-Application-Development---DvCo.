package databaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseConnection.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelData.Donut;

public class AdminHomeConnection {

	public static ObservableList<Donut> getDonutList() {
		ObservableList<Donut> donutList = FXCollections.observableArrayList();
        String query = "SELECT * FROM MsDonut";
        try (Connection adminCon = ConnectionDB.getConnection();
             PreparedStatement ps = adminCon.prepareStatement(query);
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

	public static boolean addDonut(Donut donut) {
        try (Connection adminCon = ConnectionDB.getConnection()) {
        	String query = "INSERT INTO MsDonut (DonutID, DonutName, DonutDescription, DonutPrice) VALUES (?, ?, ?, ?)";
        	PreparedStatement ps = adminCon.prepareStatement(query);
        	ps.setString(1, donut.getId());
        	ps.setString(2, donut.getName());
        	ps.setString(3, donut.getDescription());
        	ps.setInt(4, donut.getPrice());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public static boolean updateDonut(Donut donut) {
        try (Connection adminCon = ConnectionDB.getConnection()) {
            String query = "UPDATE MsDonut SET DonutName = ?, DonutDescription = ?, DonutPrice = ? WHERE DonutID = ?";
            PreparedStatement ps = adminCon.prepareStatement(query);
            ps.setString(1, donut.getName());         
            ps.setString(2, donut.getDescription());  
            ps.setInt(3, donut.getPrice());           
            ps.setString(4, donut.getId());    
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	public static boolean deleteDonut(String donutId) {
		String deleteCartQuery = "DELETE FROM cart WHERE DonutID = ?";
	    String deleteTransactionDetailQuery = "DELETE FROM TransactionDetail WHERE DonutID = ?";
	    String deleteDonutQuery = "DELETE FROM MsDonut WHERE DonutID = ?";

	    try (Connection adminCon = ConnectionDB.getConnection()) {
	        
	        try (PreparedStatement psCart = adminCon.prepareStatement(deleteCartQuery)) {
	            psCart.setString(1, donutId);
	            psCart.executeUpdate();
	        }

	        try (PreparedStatement psTransactionDetail = adminCon.prepareStatement(deleteTransactionDetailQuery)) {
	            psTransactionDetail.setString(1, donutId);
	            psTransactionDetail.executeUpdate();
	        }

	        try (PreparedStatement psDonut = adminCon.prepareStatement(deleteDonutQuery)) {
	            psDonut.setString(1, donutId);
	            int rows = psDonut.executeUpdate();
	            return rows > 0;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
    }
    

	public static String generateDonutId() {
        String query = "SELECT DonutID FROM MsDonut ORDER BY DonutID DESC LIMIT 1";
        try (Connection adminCon = ConnectionDB.getConnection(); 
                PreparedStatement ps = adminCon.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
               if (rs.next()) {
                String lastId = rs.getString("DonutID");
                int newId = Integer.parseInt(lastId.substring(2)) + 1;
                return String.format("DN%03d", newId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "DN001";
    }
}
