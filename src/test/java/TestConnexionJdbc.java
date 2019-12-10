package test.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.mariadb.jdbc.Driver;

public class TestConnexionJdbc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			DriverManager.registerDriver(new Driver());
			String user = "u62ktagdxswnv1uk";
			String password = "0ZB5W9ESwCMAS2FLIrXq";
			String url = "jdbc:mariadb://bssfeveqoxjcx2plc0lr-mysql.services.clever-cloud.com:3306/bssfeveqoxjcx2plc0lr";
			Connection maConnection = DriverManager.getConnection(url, user, password);
			System.out.println(maConnection.getCatalog());
			maConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
