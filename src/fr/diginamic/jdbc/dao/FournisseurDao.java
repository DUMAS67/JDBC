package fr.diginamic.jdbc.dao;

import java.util.ArrayList;

import fr.diginamic.jdbc.entites.Fournisseur;

public interface FournisseurDao {

	ArrayList<Fournisseur> extraire();

	void insert(Fournisseur fournisseur);

	
	int update(String ancienNom, String nouveauNom);

	boolean delete(Fournisseur fournisseur);


	

		
	}

