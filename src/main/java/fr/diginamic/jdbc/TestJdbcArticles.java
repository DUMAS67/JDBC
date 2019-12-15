package fr.diginamic.jdbc;
/* Classe executable de test pour ecriture, effacement d'articles
 *  et de fournisseurs selon certains critères sur une base de donnée*/
import java.util.ArrayList;
import fr.diginamic.jdbc.dao.ArticleDaoJdbc;
import fr.diginamic.jdbc.dao.FournisseurDaoJdbc;
import fr.diginamic.jdbc.entites.Article;
import fr.diginamic.jdbc.entites.Fournisseur;

public class TestJdbcArticles {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArticleDaoJdbc a = new ArticleDaoJdbc();
		FournisseurDaoJdbc f = new FournisseurDaoJdbc();
		f.insert(new Fournisseur(4, "La Maison des Peintures"));
		a.insert(new Article(11, "X23", "Peinture blanche 1L", 12.5, 4));
		a.insert(new Article(12, "X24", "Peinture rouge mate 1L", 15.5, 4));
		a.insert(new Article(13, "X26", "Peinture noire laquée 1L", 17.8, 4));
		a.insert(new Article(14, "X12", "Peinture bleue mate 1L", 15.5, 4));
		/* Baisse du prix de 25% d'Article de Peinture mate */
		String requete = "UPDATE ARTICLE SET PRIX=(PRIX*0.75) where DESIGNATION LIKE '%mate %'";
		a.askVoid(requete);
		/*
		 * Affichage de la liste des Articles
		 */
		ArrayList<Article> listArticle = a.extraire();
		a.afficheListArticle(listArticle);

		/* la Base renvoie le calcul de la moyenne des prix de peinture */
		requete = "SELECT AVG(PRIX) from ARTICLE";
		Double resultat = a.askDouble(requete);
		System.out.println("Prix moyen des Articles : " + resultat);

		
		 /* *
		 * Suppression des articles contenant les articles contenant de la
		 * peinture
		 */
		requete = "SELECT * FROM ARTICLE WHERE DESIGNATION LIKE '%Peinture %'";
		a.setCommandeExtraction(requete);
		ArrayList<Article> listArt = a.extraire();
		a.effacerListArticle(listArt);

		/* Effacement d'un Fournisseur */
		Fournisseur fO = new Fournisseur(4, "La Maison des Peintures");
		f.delete(fO);

		/* Affichage liste Article pour contrôle des tests */
		ArrayList<Article> lArt = a.extraire();
		a.afficheListArticle(lArt);

		/*
		 * fermeture des pointeurs sur le fichier de configuration et de la base
		 */
		a.closeConnection();
		f.closeConnection();
	}
}
