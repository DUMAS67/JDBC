package test.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TestConnexionJdbc {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			ResourceBundle monFichierConf = ResourceBundle.getBundle("database");
			String user = monFichierConf.getString("user");
			String password = monFichierConf.getString("password");
			String url = "jdbc:" + monFichierConf.getString("url");
			String driver =monFichierConf.getString("driver");
			Class.forName(driver);
			Connection maConnection = DriverManager.getConnection(url, user, password);
			System.out.println(maConnection.getCatalog());
			maConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Le Driver driver n'a pas été trouvé");
		}
	}
}
