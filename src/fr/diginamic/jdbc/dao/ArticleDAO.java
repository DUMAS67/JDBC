package fr.diginamic.jdbc.dao;

import java.util.ArrayList;

import fr.diginamic.jdbc.entites.Article;
/* Interface qui decrit les méthodes 
 * obligatoires pour le traitement de la table ARTICLE */
public interface ArticleDAO {

	ArrayList<Article> extraire();

	void insert(Article article);
	
	int update(String ancienNom, String nouveauNom);

	boolean delete(Article article);	
		
}
