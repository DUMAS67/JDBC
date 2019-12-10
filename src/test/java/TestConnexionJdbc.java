package test.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.mariadb.jdbc.Driver;

public class TestConnexionJdbc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			DriverManager.registerDriver(new Driver());
			ResourceBundle monFichierConf = ResourceBundle.getBundle("database");
			String user = monFichierConf.getString("user");
			String password = monFichierConf.getString("password");

			String url = "jdbc:" + monFichierConf.getString("url");
			Connection maConnection = DriverManager.getConnection(url, user, password);
			System.out.println(maConnection.getCatalog());
			maConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
