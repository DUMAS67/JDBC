package fr.diginamic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/* Classe qui connecte une base de donnée par fichier de paramétrage*/

public class ConnexionDB {

	public static Connection connecter(String fichier) {

		Connection maConnection = null;
		boolean first = false;

		try {
			ResourceBundle monFichierConf = ResourceBundle.getBundle(fichier);
			String user = monFichierConf.getString("user");
			String password = monFichierConf.getString("password");
			String url = "jdbc:" + monFichierConf.getString("url");
			String driver = monFichierConf.getString("driver");
			if (first != true) {
				Class.forName(driver);
				first = true;
			}
			maConnection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Le Driver driver n'a pas été trouvé");
		}
		return maConnection;
	}
}
