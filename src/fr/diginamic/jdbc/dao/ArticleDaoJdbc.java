package fr.diginamic.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.diginamic.jdbc.entites.Article;
/* Classe implémentée de ArticleDAO qui fournit les méthodes 
 * obligatoires pour le traitement de la table ARTICLE */
public class ArticleDaoJdbc implements ArticleDAO {

	private Statement statement;
	private ResultSet curseur;

	public ArticleDaoJdbc(Statement statement) {
		super();
		this.statement = statement;
	}

	public ArticleDaoJdbc(ResultSet curseur) {
		super();
		this.curseur = curseur;
	}

	@Override
	public void insert(Article article) {
		// TODO Auto-generated method stub
		try {
			String id = String.valueOf(article.getId());
			String ref = article.getRef();
			String designation = article.getDesignation();
			Double prix = article.getPrix();
			String idFou = String.valueOf(article.getIdFou());
			String valeurs = id + ",'" + ref + "','" + designation + "'," + prix + "," + idFou;
			String insertF = "INSERT INTO ARTICLE (ID, REF, DESIGNATION, PRIX, ID_FOU) values (" + valeurs + ")";
			statement.executeUpdate(insertF);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/* Méthode qui update des données de la table ARTICLE */
	@Override
	public int update(String ancienNom, String nouveauNom) {
		int r = 0;
		try {
			String update = "UPDATE ARTICLE SET NOM= '" + nouveauNom + "' WHERE NOM = '" + ancienNom + "'";
			r = statement.executeUpdate(update);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	/* Méthode qui efface des données de la table ARTICLE */
	@Override
	public boolean delete(Article article) {
		String id = String.valueOf(article.getId());
		int intBol = 0;
		boolean ok = false;
		try {
			String delete = "DELETE FROM ARTICLE WHERE ID=" + id;
			intBol = statement.executeUpdate(delete);
			if (intBol == 1) {
				ok = true;
			} else {
				ok = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}
/* Méthode qui extrait des données de la table ARTICLE et les met dans une liste*/
	@Override
	public ArrayList<Article> extraire() {
		ArrayList<Article> listArt = new ArrayList<>(0);
		try {
			while (curseur.next()) {
				Integer id = curseur.getInt("ID");
				String ref = curseur.getString("REF");
				String designation = curseur.getString("DESIGNATION");
				Double prix = curseur.getDouble("PRIX");
				int idFou = curseur.getInt("ID_FOU");
				listArt.add(new Article(id, ref, designation, prix, idFou));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listArt;
	}

	/* méthode qui envoie une requête à la base*/
	public int lCommandeUpdate(String commande) {
		int r = 0;
		try {
			r = statement.executeUpdate(commande);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	/* Affiche une liste des données de la table Article */
	public void afficheListArticle(ArrayList<Article> list) {
		for (Article l : list) {
			System.out.println("ID : " + l.getId() + " REF : " + l.getRef() + " DESIGNATION : " + l.getDesignation()
					+ " Prix : " + l.getPrix() + " ID_FOU : " + l.getIdFou());
		}
	}
}