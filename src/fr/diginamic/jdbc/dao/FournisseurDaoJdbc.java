package fr.diginamic.jdbc.dao;

/* Classe contenant toutes les méthodes implémentées de FournisseurDao
 * + méthodes annexes de 
 * - connection aux fichier paramétré des connexions à une base
 * - fermeture d'une connection
 * - lecture d'une liste de tableau de Fournisseur
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fr.diginamic.jdbc.entites.Fournisseur;

public class FournisseurDaoJdbc implements FournisseurDao {

	private Connection connection;

	public FournisseurDaoJdbc() {
		super();
		this.connection = FournisseurDaoJdbc.connecter("database");
	}

	/*
	 * méthode permettant de se connecter à un fichier de paramètre d'accès à
	 * une base
	 */
	static Connection connecter(String fichier) {

		boolean first = false;
		Connection connection = null;
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
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Le Driver driver n'a pas été trouvé");
		}
		return connection;
	}

	public ArrayList<Fournisseur> extraire() {

		ArrayList<Fournisseur> listFour = new ArrayList<>(0);
		PreparedStatement monSt = null;
		ResultSet curseur = null;
		try {
			monSt = connection.prepareStatement("SELECT * FROM FOURNISSEUR ");
			curseur = monSt.executeQuery();
			while (curseur.next()) {
				Integer id = curseur.getInt("ID");
				String nom = curseur.getString("NOM");
				listFour.add(new Fournisseur(id, nom));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				curseur.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				monSt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listFour;
	}

	@Override
	public void insert(Fournisseur fournisseur) {
		PreparedStatement insertF = null;

		try {
			if (fournisseur != null) {
				String requeteTest = "SELECT ID FROM FOURNISSEUR WHERE ID =?";
				insertF = connection.prepareStatement(requeteTest);
				insertF.setInt(1, fournisseur.getId());
				ResultSet test = insertF.executeQuery();
				if (!test.first()) { // ou test.next()
					insertF.close();
					String requete = "INSERT INTO FOURNISSEUR (ID, NOM) values (?,?)";
					insertF = connection.prepareStatement(requete);
					insertF.setInt(1, fournisseur.getId());
					insertF.setString(2, fournisseur.getNom());
					insertF.executeUpdate();
					insertF.close();
					connection.commit();
				}
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public int update(String ancienNom, String nouveauNom) {
		int r = 0;
		PreparedStatement updateF = null;
		try {
			String update = "UPDATE FOURNISSEUR SET NOM= ? WHERE NOM = ?";
			updateF = connection.prepareStatement(update);
			updateF.setString(1, nouveauNom);
			updateF.setString(2, ancienNom);
			r = updateF.executeUpdate();
			if (r != 1) {
				connection.rollback();
			} else {
				connection.commit();
			}
			updateF.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public boolean delete(Fournisseur fournisseur) {
		int r = 0;
		boolean ok = false;
		try {
			String delete = "DELETE FROM FOURNISSEUR WHERE ID= ?";
			PreparedStatement del = connection.prepareStatement(delete);
			del.setInt(1, fournisseur.getId());
			r = del.executeUpdate();
			if (r == 1) {
				ok = true;
				connection.commit();
			} else {
				ok = false;
				connection.rollback();
			}
			del.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}

	public void closeConnection() {

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Affiche une liste des données de Fournisseurs */
	public void afficheListFournisseur(ArrayList<Fournisseur> list) {
		for (Fournisseur l : list) {
			System.out.println("ID : " + l.getId() + " NOM :" + l.getNom());
		}
	}
}