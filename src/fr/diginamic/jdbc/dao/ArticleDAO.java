package fr.diginamic.jdbc.dao;

import java.util.ArrayList;

import fr.diginamic.jdbc.entites.Article;
/* Interface qui decrit les méthodes 
 * obligatoires pour le traitement de la table ARTICLE */
public interface ArticleDAO {

	/* Méthode qui extrait des données de la table ARTICLE et les met dans une liste*/
	ArrayList<Article> extraire();
	/* Méthode qui insère des données Article dans la table ARTICLE */
	void insert(Article article);
	/* Méthode qui update des données de la table ARTICLE */
	int update(String ancienNom, String nouveauNom);
	/* Méthode qui efface des données de la table ARTICLE */
	boolean delete(Article article);	
		
}
