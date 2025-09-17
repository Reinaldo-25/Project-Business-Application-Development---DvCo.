package databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
	private final static String USERNAME = "root";
	private final static String PASSWORD = "";
	private final static String DATABASE = "dvco";
	private final static String HOST = "localhost:3306";
	private final static String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private static Connection con; 
	private Statement st; 
	private static ConnectionDB instance; 
	public ResultSet rs; 
	public ResultSetMetaData rsm; 
	
	public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
    }

	private ConnectionDB() {
		try {
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ConnectionDB getInstance() {
		if (instance == null) {
			instance = new ConnectionDB();
		}
		return instance;
	}

	public ResultSet execQuery(String Query) {
		try {
			rs = st.executeQuery(Query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void execUpdate(String Query) {
		try {
			st.executeUpdate(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public PreparedStatement preparedStatement(String query) {
        try {
            con = getConnection();
            return con.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
