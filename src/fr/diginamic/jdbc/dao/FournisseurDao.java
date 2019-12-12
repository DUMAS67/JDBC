package fr.diginamic.jdbc.dao;

import java.util.ArrayList;

import fr.diginamic.jdbc.entites.Fournisseur;

public interface FournisseurDao {
	
	/* Méthode qui extrait les données des Fournisseurs de la base de donnée des Fournisseurs */
	ArrayList<Fournisseur> extraire();

	/* Méthode qui ajoute une donnée Fournisseur dans la base de donnée des Fournisseurs */
	void insert(Fournisseur fournisseur);

	/* Méthode qui modifie une donnée de Désignation de la base des Fournisseurs */
	int update(String ancienNom, String nouveauNom);

	/* Méthode qui efface une donnée Fournisseur de la base des Fournisseurs */
	boolean delete(Fournisseur fournisseur);
	}

