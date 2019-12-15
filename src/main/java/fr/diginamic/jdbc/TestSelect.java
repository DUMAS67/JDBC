package fr.diginamic.jdbc;
/* Classe executable qui scrute la base des Fournissseurs*/

import java.util.ArrayList;
import fr.diginamic.jdbc.dao.FournisseurDaoJdbc;
import fr.diginamic.jdbc.entites.Fournisseur;

public class TestSelect {

	public static void main(String[] args) {

		/* Affichage liste fournisseur */
		FournisseurDaoJdbc s = new FournisseurDaoJdbc();
		ArrayList<Fournisseur> lFour = s.extraire();
		s.afficheListFournisseur(lFour);
		s.closeConnection();
	}
}