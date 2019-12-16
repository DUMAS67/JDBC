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

import fr.diginamic.jdbc.recensement.entites.Region;

public class RegionDAOJdbc implements RegionDAO {

	/* Definition d'un Loggeur pour contrôler les erreurs eventuelles */
	private static final Logger LOG = LoggerFactory.getLogger("file");

	/* requete normale pour la méthode read() */
	final String SELECTALL = "SELECT * FROM REGION ";

	private Connection connection;
	private String commandeExtraction = SELECTALL;

	public RegionDAOJdbc() {
		super();
		this.connection = RegionDAOJdbc.connect("database");
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
			System.out.println("Le Driver driver n'a pas été trouvé");
		}
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.diginamic.jdbc.recensement.entites.RegionDAO#create(fr.diginamic.jdbc.
	 * recensement.entites.Region)
	 */
	@Override
	public void insert(Region uneRegion) {
		try {
			PreparedStatement insertR = null;
			if (uneRegion != null && exist(uneRegion)) {
				String requete = "INSERT INTO REGION (ID, NOM) values (?,?)";
				insertR = connection.prepareStatement(requete);
				insertR.setInt(1, uneRegion.getIdRegion());
				insertR.setString(2, uneRegion.getNom());
				insertR.executeUpdate();
				insertR.close();
				connection.commit();
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOG.debug("La requête n'a pu se faire - base en mode donnée antérieure");
			}
			LOG.debug("Problème lié au PreparedStatement");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.diginamic.jdbc.recensement.entites.RegionDAO#read()
	 */
	@Override
	public ArrayList<Region> read() {
		ArrayList<Region> listRegion = new ArrayList<>(0);
		PreparedStatement monSt = null;
		ResultSet curseur = null;
		try {
			monSt = connection.prepareStatement(commandeExtraction);
			curseur = monSt.executeQuery();
			while (curseur.next()) {
				Integer idRegion = curseur.getInt("ID");
				String nom = curseur.getString("NOM");
				listRegion.add(new Region(idRegion, nom));
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
		return listRegion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.diginamic.jdbc.recensement.entites.RegionDAO#update(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public int update(String ancien, String nouveau) {
		int r = 0;
		PreparedStatement updateR = null;
		try {
			String update = "UPDATE REGION SET NOM= ? WHERE NOM = ?";
			updateR = connection.prepareStatement(update);
			updateR.setString(1, nouveau);
			updateR.setString(2, ancien);
			r = updateR.executeUpdate();
			if (r != 1) {
				connection.rollback();
			} else {
				connection.commit();
			}
			updateR.close();
		} catch (SQLException e) {
			LOG.debug("Problème lié au PreparedStatement");
		}
		return r;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.diginamic.jdbc.recensement.entites.RegionDAO#delete(fr.diginamic.jdbc.
	 * recensement.entites.Region)
	 */
	@Override
	public int delete(Region uneRegion) {
		int r = 0;
		try {
			String delete = "DELETE FROM REGION WHERE ID= ?";
			PreparedStatement del = connection.prepareStatement(delete);
			del.setInt(1, uneRegion.getIdRegion());
			r = del.executeUpdate();
			if (r == 1) {
				connection.commit();
			} else {
				connection.rollback();
			}
			del.close();
		} catch (SQLException e) {
			LOG.debug("Problème lié au PreparedStatement");
		}
		return r;
	}

	/* méthode qui teste si la Region existe deja en base de donnée */
	public boolean exist(Region region) {

		boolean existeRegion = false;
		try {
			PreparedStatement select = null;
			String requeteTest = "SELECT NOM FROM REGION WHERE NOM =?";
			select = connection.prepareStatement(requeteTest);
			select.setString(1, region.getNom());
			ResultSet test = select.executeQuery();
			if (!test.next()) { // ou test.first()
				existeRegion = true;
			} else {
				existeRegion = false;
			}
			select.close();
		} catch (SQLException e) {
			LOG.debug("Problème lié au PreparedStatement");
		}
		return existeRegion;
	}

	/* Méthode qui cloture les connections à la base */
	public void closeConnection() {

		try {
			connection.close();
		} catch (SQLException e) {
			LOG.debug("Problème lié à la clôture de la connexion");
		}
	}
}