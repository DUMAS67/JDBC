/**
 * 
 */
package fr.diginamic.jdbc.recensement.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.jdbc.recensement.entites.Ville;

/**
 * @author DUMAS
 *
 */
public class VilleDAOJdbc implements VilleDAO {

	/* Definition d'un Loggeur pour contrôler les erreurs eventuelles*/
	private static final Logger LOG = LoggerFactory.getLogger("file");
	
	/* requete normale pour la méthode extraire */
	final String SELECTALL = "SELECT * FROM VILLE ";

	private Connection connection;
	private String commandeExtraction = SELECTALL;

	public VilleDAOJdbc() {
		super();
		this.connection = VilleDAOJdbc.connect("database");
	}

	/*
	 * méthode permettant de se connecter à partir d'un fichier de paramètre à
	 * une base
	 */

	static Connection connect(String fichier) {

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
			LOG.debug("Problème avec la connexion au fichier de base");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			LOG.debug("Le Driver driver n'a pas été trouvé");
		}
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.diginamic.jdbc.recensement.entites.VilleDAO#create(fr.diginamic.jdbc.
	 * recensement.entites.Ville)
	 */
	@Override
	public void insert(Ville uneVille) {
		try {
			if (uneVille != null && exist(uneVille)) {
				PreparedStatement insertV = null;
				String requete = "INSERT INTO VILLE (ID, NOM, POPULATION,ID_DPT, ID_REG) values (?,?,?,?,?)";
				insertV = connection.prepareStatement(requete);
				insertV.setInt(1, uneVille.getIdVille());
				insertV.setString(2, uneVille.getNom());
				insertV.setInt(3, uneVille.getPopulation());
				insertV.setInt(4, uneVille.getIdDepartement());
				insertV.setInt(5, uneVille.getIdRegion());
				insertV.executeUpdate();
				insertV.close();
				connection.commit();
			} 
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOG.debug("La requête n'a pu se faire - base en mode donnée antérieure");
			}
			LOG.debug("Problème avec le PreparedStatement");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.diginamic.jdbc.recensement.entites.VilleDAO#read()
	 */
	@Override
	public ArrayList<Ville> read() {
		ArrayList<Ville> listVille = new ArrayList<>(0);
		PreparedStatement monSt = null;
		ResultSet curseur = null;
		try {
			monSt = connection.prepareStatement(commandeExtraction);
			curseur = monSt.executeQuery();
			while (curseur.next()) {
				Integer idVille = curseur.getInt("ID");
				String nom = curseur.getString("NOM");
				Integer population = curseur.getInt("POPULATION");
				Integer idDepartement = curseur.getInt("ID_DPT");
				Integer idRegion = curseur.getInt("ID_REG");
				listVille.add(new Ville(idVille, nom, population, idDepartement, idRegion));
			}
			this.commandeExtraction = SELECTALL;
		} catch (SQLException e) {
			LOG.debug("Problème lié au PreparedStatement");
		} finally {
			try {
				curseur.close();
			} catch (SQLException e) {
				LOG.debug("Problème de cloture du curseur");
			}
			try {
				monSt.close();
			} catch (SQLException e) {
				LOG.debug("Problème de cloture du PreparedStatement");
			}
		}
		return listVille;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.diginamic.jdbc.recensement.entites.VilleDAO#update(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int update(String ancien, String nouveau) {
		int r = 0;
		PreparedStatement updateV = null;
		try {
			String update = "UPDATE VILLE SET NOM= ? WHERE NOM = ?";
			updateV = connection.prepareStatement(update);
			updateV.setString(1, nouveau);
			updateV.setString(2, ancien);
			r = updateV.executeUpdate();
			if (r != 1) {
				connection.rollback();
			} else {
				connection.commit();
			}
			updateV.close();
		} catch (SQLException e) {
			LOG.debug("Problème lié au PreparedStatement");;
		}
		return r;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.diginamic.jdbc.recensement.entites.VilleDAO#delete(fr.diginamic.jdbc.
	 * recensement.entites.Ville)
	 */
	@Override
	public int delete(Ville uneVille) {
		int r = 0;
		try {
			String delete = "DELETE FROM VILLE WHERE ID= ?";
			PreparedStatement del = connection.prepareStatement(delete);
			del.setInt(1, uneVille.getIdVille());
			r = del.executeUpdate();
			if (r == 1) {
				connection.commit();
			} else {
				connection.rollback();
			}
			del.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOG.debug("Problème lié au PraparedStatement");;
		}
		return r;
	}
/* méthode qui teste si la ville existe deja en base de donnée */
	public boolean exist(Ville ville) {

		PreparedStatement select = null;
		boolean villeExist = false;
		if (ville != null) {
			String requeteTest = "SELECT ID FROM VILLE WHERE ID =?";
			try {
				select = connection.prepareStatement(requeteTest);
				select.setInt(1, ville.getIdVille());
				ResultSet test = select.executeQuery();
				if (!test.next()) { // ou test.first()
					villeExist = true;
				} else {
					villeExist = false;
				}
				select.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				LOG.debug("Problème lié au PreparedStatement");;
			}
		}
		return villeExist;
	}

	/* Méthode qui cloture les connections à la base */
	public void closeConnection() {

		try {
			connection.close();
		} catch (SQLException e) {
			LOG.debug("Problème de clôture de la connexion");;
		}
	}
}
