package fr.diginamic.jdbc.recensement.Dao;

import java.util.ArrayList;

import fr.diginamic.jdbc.recensement.entites.Departement;

public interface DepartementDao {

	
	/* Méthode qui crée un Departement pour la table Departement */
	void insert(Departement unDepartement);
	ArrayList<Departement>read();
	/* Méthode qui modifie une donnée de la table Departement */
	int update(String ancien, String nouveau);
	/* Méthode qui efface des données de la table Departement */
	int delete(Departement unDepartement);
	
	
}
