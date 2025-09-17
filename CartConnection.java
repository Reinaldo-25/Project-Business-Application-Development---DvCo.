package databaseObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import databaseConnection.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelData.CartItems;

public class CartConnection {
	public static ObservableList<CartItems> getCartItems(String userId) {
        ObservableList<CartItems> cartItem = FXCollections.observableArrayList();
        try (Connection cartCon = ConnectionDB.getConnection()) {
            String query = "SELECT c.DonutID, d.DonutName, d.DonutPrice, c.Quantity FROM cart c JOIN msdonut d ON c.DonutID = d.DonutID WHERE c.UserID = ?";
            PreparedStatement ps = cartCon.prepareStatement(query);
            ps.setString(1, userId); 
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String id = rs.getString("DonutID");
                String name = rs.getString("DonutName");
                int price = rs.getInt("DonutPrice");
                int quantity = rs.getInt("Quantity");
                int total = price * quantity;

                cartItem.add(new CartItems(id, name, price, quantity, total));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartItem;
    }

	public static boolean checkoutCart(CartItems selectedCartItem, String userId) {
	    String transactionId = generateTransactionId();  
	    String insertHeaderQuery = "INSERT INTO TransactionHeader (TransactionID, UserID) VALUES (?, ?)";
	    String insertDetailQuery = "INSERT INTO TransactionDetail (TransactionID, DonutID, Quantity) VALUES (?, ?, ?)";
	    String deleteCartQuery = "DELETE FROM cart WHERE UserID = ? AND DonutID = ?";

	    try (Connection cartCon = ConnectionDB.getConnection()) {
	        try (PreparedStatement psHeader = cartCon.prepareStatement(insertHeaderQuery)) {
	            psHeader.setString(1, transactionId);
	            psHeader.setString(2, userId);
	            psHeader.executeUpdate();
	        }

	        try (PreparedStatement psDetail = cartCon.prepareStatement(insertDetailQuery)) {
	            psDetail.setString(1, transactionId);
	            psDetail.setString(2, selectedCartItem.getId()); 
	            psDetail.setInt(3, selectedCartItem.getQuantity());
	            psDetail.executeUpdate();
	        }

	        try (PreparedStatement psDelete = cartCon.prepareStatement(deleteCartQuery)) {
	            psDelete.setString(1, userId);
	            psDelete.setString(2, selectedCartItem.getId()); 
	            psDelete.executeUpdate();
	        }

	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
    
    public static String generateTransactionId() {
		String query = "SELECT TransactionID FROM TransactionHeader ORDER BY TransactionID DESC LIMIT 1";
		try (Connection cartCon = ConnectionDB.getConnection(); PreparedStatement ps = cartCon.prepareStatement(query)) {
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            String tId = rs.getString("TransactionID");
	            int numId = Integer.parseInt(tId.substring(2)) + 1;
	            return String.format("TR%03d", numId);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "TR001";
	}
}
