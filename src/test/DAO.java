package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
	
	public  Connection ccn;
	public  Statement st;
	public  ResultSet rs;
	
	public  Statement resulet()  throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		ccn = DriverManager.getConnection("jdbc:postgresql://192.168.6.100:5432/postgres", "postgres","postgres");
		st  = ccn.createStatement();
		return st;
	}
	
	
	public  void stopClose() throws SQLException {
		
		if (rs != null) {
			rs.close();
		}
		if (st != null) {
			st.close();
		}
		if (ccn != null) {
			ccn.close();
		}

	}
	
}
