package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import fr.diginamic.jdbc.entites.Article;

/* Classe implémentée de ArticleDAO qui fournit les méthodes 
 * obligatoires pour le traitement de la table ARTICLE */

public class ArticleDaoJdbc implements ArticleDAO {

	final String SELECTALL = "SELECT * FROM ARTICLE ";// requete normale pour la methode extraire"
	private Connection connection;
	private String commandeExtraction = SELECTALL;

	public ArticleDaoJdbc() {
		super();
		this.connection = ArticleDaoJdbc.connecter("database");
	}

	/* méthode permettant de se connecter à partir d'un fichier de paramètre à une base*/
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

	@Override
	public void insert(Article article) {
		try {
			PreparedStatement insertA = null;
			if (article != null) {
				String requeteTest = "SELECT ID FROM ARTICLE WHERE ID =?";
				insertA = connection.prepareStatement(requeteTest);
				insertA.setInt(1, article.getId());
				ResultSet test = insertA.executeQuery();
				if (!test.next()) { // ou test.first()
					insertA.close();
					String requete = "INSERT INTO ARTICLE (ID, REF, DESIGNATION, PRIX, ID_FOU) values (?,?,?,?,?)";
					insertA = connection.prepareStatement(requete);
					insertA.setInt(1, article.getId());
					insertA.setString(2, article.getRef());
					insertA.setString(3, article.getDesignation());
					insertA.setDouble(4, article.getPrix());
					insertA.setInt(5, article.getIdFou());
					insertA.executeUpdate();
					insertA.close();
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
		PreparedStatement updateA = null;
		try {
			String update = "UPDATE ARTICLE SET NOM= ? WHERE NOM = ?";
			updateA = connection.prepareStatement(update);
			updateA.setString(1, nouveauNom);
			updateA.setString(2, ancienNom);
			r = updateA.executeUpdate();
			if (r != 1) {
				connection.rollback();
			} else {
				connection.commit();
			}
			updateA.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public boolean delete(Article article) {

		int r = 0;
		boolean ok = false;
		try {
			String delete = "DELETE FROM ARTICLE WHERE ID= ?";
			PreparedStatement del = connection.prepareStatement(delete);
			del.setInt(1, article.getId());
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
/* Méthode qui efface une liste d'Article selectionné d'une base */
	public void effacerListArticle(ArrayList<Article> list) {

		boolean f = false;
		for (Article aP : list) {
			f = this.delete(aP);
			if (f) {
				try {
					connection.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					connection.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public ArrayList<Article> extraire() {
		ArrayList<Article> listArt = new ArrayList<>(0);
		PreparedStatement monSt = null;
		ResultSet curseur = null;
		try {
			monSt = connection.prepareStatement(commandeExtraction);
			curseur = monSt.executeQuery();
			while (curseur.next()) {
				Integer id = curseur.getInt("ID");
				String ref = curseur.getString("REF");
				String designation = curseur.getString("DESIGNATION");
				Double prix = curseur.getDouble("PRIX");
				Integer idFou = curseur.getInt("ID_FOU");
				listArt.add(new Article(id, ref, designation, prix, idFou));
			}
			this.commandeExtraction = SELECTALL;
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
		return listArt;
	}

	/* méthode qui envoie une requête à la base */
	public int askVoid(String commande) {
		int r = 0;
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(commande);
			r = stat.executeUpdate(commande);
			if (r == 1) {
				connection.commit();
			} else {
				connection.rollback();
			}
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	/*méthode qui envoie une requête à la base et qui retourne un résultat sur les prix des articles*/
	public double askDouble(String commande) {

		Double result = 0.0d;
		PreparedStatement state = null;
		ResultSet avg = null;
		try {
			state = connection.prepareStatement(commande);
			avg = state.executeQuery(commande);
			if (avg.next()) {
				result = avg.getDouble(1);
			} else {
				System.out.println("Pas de réponse possible à votre requête ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				avg.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/* Affiche une liste des données de la table Article */
	public void afficheListArticle(ArrayList<Article> list) {
		for (Article l : list) {
			System.out.println("ID : " + l.getId() + " REF : " + l.getRef() + " DESIGNATION : " + l.getDesignation()
					+ " Prix : " + l.getPrix() + " ID_FOU : " + l.getIdFou());
		}
	}

	/* Méthode qui cloture les connections à la base */
	public void closeConnection() {

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getCommandeExtraction() {
		return commandeExtraction;
	}
/* Setter utilisé pour passage dans la méthode extraire d'une autre requete*/
	public void setCommandeExtraction(String commandeExtraction) {
		this.commandeExtraction = commandeExtraction;
	}
}