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

import fr.diginamic.jdbc.recensement.entites.Departement;

public class DepartementDAOJdbc implements DepartementDao {

	/* Definition d'un Loggeur pour contrôler les erreurs eventuelles*/
	private static final Logger LOG = LoggerFactory.getLogger("file");
	
	/* requete normale pour la méthode read() */
	final String SELECTALL = "SELECT * FROM DEPARTEMENT ";

	private Connection connection;
	private String commandeExtraction = SELECTALL;

	public DepartementDAOJdbc() {
		super();
		this.connection = DepartementDAOJdbc.connect("database");
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
			LOG.debug("Le Driver driver n'a pas été trouvé");
		}
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.diginamic.jdbc.recensement.entites.DepartementDAO#create(fr.diginamic.
	 * jdbc. recensement.entites.Departement)
	 */
	@Override
	public void insert(Departement unDepartement) {
		try {
			PreparedStatement insertD = null;
			if (unDepartement != null && exist(unDepartement)) {
				String requete = "INSERT INTO DEPARTEMENT (ID, ID_REG) values (?,?)";
				insertD = connection.prepareStatement(requete);
				insertD.setInt(1, unDepartement.getIdDepartement());
				insertD.setInt(2, unDepartement.getIdFRegion());
				insertD.executeUpdate();
				insertD.close();
				connection.commit();
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOG.debug("La requête n'a pu se faire - base en mode donnée antérieure");;
			}
			LOG.debug("Problème avec le PreparedStatement");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.diginamic.jdbc.recensement.entites.DepartementDAO#read()
	 */
	@Override
	public ArrayList<Departement> read() {
		ArrayList<Departement> listDepartement = new ArrayList<>(0);
		PreparedStatement monSt = null;
		ResultSet curseur = null;
		try {
			monSt = connection.prepareStatement(commandeExtraction);
			curseur = monSt.executeQuery();
			while (curseur.next()) {
				Integer idDepartement = curseur.getInt("ID");
				Integer idFRegion = curseur.getInt("ID_REG");
				listDepartement.add(new Departement(idDepartement, idFRegion));
			}
			this.commandeExtraction = SELECTALL;
		} catch (SQLException e) {
			LOG.debug("Problème avec le PreparedStatement");
		} finally {
			try {
				curseur.close();
			} catch (SQLException e) {
				LOG.debug("Problème de cloture du PreparedStatement");
			}
			try {
				monSt.close();
			} catch (SQLException e) {
				LOG.debug("Problème de cloture du curseur");
			}
		}
		return listDepartement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.diginamic.jdbc.recensement.entites.DepartementDAO#update(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public int update(String ancien, String nouveau) {
		int r = 0;
		PreparedStatement updateR = null;
		try {
			String update = "UPDATE DEPARTEMENT SET NOM= ? WHERE NOM = ?";
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
	 * fr.diginamic.jdbc.recensement.entites.DepartementDAO#delete(fr.diginamic.
	 * jdbc. recensement.entites.Departement)
	 */
	@Override
	public int delete(Departement unDepartement) {
		int r = 0;
		try {
			String delete = "DELETE FROM DEPARTEMENT WHERE ID= ?";
			PreparedStatement del = connection.prepareStatement(delete);
			del.setInt(1, unDepartement.getIdDepartement());
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

	/* méthode qui teste si le département existe deja en base de donnée */

	public boolean exist(Departement departement) {

		boolean existeDepartement = false;
		try {
			PreparedStatement select = null;
			String requeteTest = "SELECT ID FROM DEPARTEMENT WHERE ID =?";
			select = connection.prepareStatement(requeteTest);
			select.setInt(1, departement.getIdDepartement());
			ResultSet test = select.executeQuery();
			if (!test.next()) { // ou test.first()
				existeDepartement = true;
			} else {
				existeDepartement = false;
			}
			select.close();
		} catch (SQLException e) {
			LOG.debug("Problème lié au PreparedStatement");
		}
		return existeDepartement;
	}

	/* Méthode qui cloture les connections à la base */
	public void closeConnection() {

		try {
			connection.close();
		} catch (SQLException e) {
			LOG.debug("Problème avec la clôture de la connexion");
		}
	}
}
