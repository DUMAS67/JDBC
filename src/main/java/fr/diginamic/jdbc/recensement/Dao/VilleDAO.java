/**
 * 
 */
package fr.diginamic.jdbc.recensement.Dao;

import java.util.ArrayList;

import fr.diginamic.jdbc.recensement.entites.Ville;


/**
 * @author DUMAS
 *
 */
public interface VilleDAO {

	/* Méthode qui crée une ville pour la table VILLE */
	void insert(Ville uneVille);
	ArrayList<Ville >read();
	/* Méthode qui modifie une donnée de la table VILLE */
	int update(String ancien, String nouveau);
	/* Méthode qui efface des données de la table VILLE */
	int delete(Ville uneVille);
	
}
