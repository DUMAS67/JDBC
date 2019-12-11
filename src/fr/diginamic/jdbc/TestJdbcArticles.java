package fr.diginamic.jdbc;

/* Classe executable de test de Table ARTICLE ET FOURNISSEUR*/
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.diginamic.jdbc.dao.ArticleDaoJdbc;
import fr.diginamic.jdbc.dao.FournisseurDaoJdbc;
import fr.diginamic.jdbc.entites.Article;
import fr.diginamic.jdbc.entites.Fournisseur;

public class TestJdbcArticles {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Connection maConnection = ConnexionDB.connecter("database");
			Statement monSt = maConnection.createStatement();
			FournisseurDaoJdbc fInsert = new FournisseurDaoJdbc(monSt);
			fInsert.insert(new Fournisseur(4, "La Maison des Peintures"));
			maConnection.commit();
			ArticleDaoJdbc a = new ArticleDaoJdbc(monSt);
			a.insert(new Article(11, "X23", "Peinture blanche 1L", 12.5, 4));
			a.insert(new Article(12, "X24", "Peinture rouge mate 1L", 15.5, 4));
			a.insert(new Article(13, "X26", "Peinture noire laquée 1L", 17.8, 4));
			a.insert(new Article(14, "X12", "Peinture bleue mate 1L", 15.5, 4));
			maConnection.commit();
			int up = a.lCommandeUpdate("UPDATE ARTICLE SET PRIX=(PRIX*0.75) where DESIGNATION LIKE '%mate %'");
			if (up == 1) {
				maConnection.commit();
			} else {
				maConnection.rollback();
			}

			/* la Base renvoie le calcul de la moyenne des prix de peinture */
			ResultSet avg = monSt.executeQuery("SELECT AVG(PRIX) from ARTICLE");
			if (avg.first() != false) {
				// on teste bien qu'il y est un resultat dans le resultset
				// (on se place aussi sur le premier enregistrement)
				Double result = avg.getDouble(1);
				System.out.println("Prix moyen des Articles : " + result);
			}

			/*
			 * Suppression des articles contenant les articles contenant de la
			 * peinture
			 */
			ResultSet peinture = monSt.executeQuery("SELECT * FROM ARTICLE WHERE DESIGNATION LIKE '%Peinture %'");
			ArticleDaoJdbc p = new ArticleDaoJdbc(peinture);
			ArrayList<Article> listP = p.extraire();
			boolean f = false;
			ArticleDaoJdbc p1 = new ArticleDaoJdbc(monSt);
			for (Article aP : listP) {
				f = p1.delete(aP);
				if (f) {
					maConnection.commit();
				} else {
					maConnection.rollback();
				}
			}
			/* Effacement d'un Fournisseur */
			Fournisseur fO = new Fournisseur(4, "La Maison des Peintures");
			f = fInsert.delete(fO);
			if (f) {
				maConnection.commit();
			} else {
				maConnection.rollback();
			}

			/* Affichage liste Article pour contrôle des tests */
			ResultSet curseur = monSt.executeQuery("SELECT * FROM ARTICLE ");
			ArticleDaoJdbc aa = new ArticleDaoJdbc(curseur);
			ArrayList<Article> listA = aa.extraire();
			aa.afficheListArticle(listA);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
