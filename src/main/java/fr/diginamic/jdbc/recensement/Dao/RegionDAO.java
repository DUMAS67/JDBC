/**
 * 
 */
package fr.diginamic.jdbc.recensement.Dao;

import java.util.ArrayList;

import fr.diginamic.jdbc.recensement.entites.Region;

/**
 * @author DUMAS
 *
 */
public interface RegionDAO {
	
	
	/* Méthode qui crée une Region pour la table Region */
	void insert(Region uneRegion);
	ArrayList<Region >read();
	/* Méthode qui modifie une donnée de la table Region */
	int update(String ancien, String nouveau);
	/* Méthode qui efface des données de la table Region */
	int delete(Region uneRegion);
	

}
