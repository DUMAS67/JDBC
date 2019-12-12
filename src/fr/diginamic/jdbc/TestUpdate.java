package fr.diginamic.jdbc;

/* Classe executable qui change une valeur dans la base des Fournissseurs*/

import java.util.ArrayList;
import fr.diginamic.jdbc.dao.FournisseurDaoJdbc;
import fr.diginamic.jdbc.entites.Fournisseur;

public class TestUpdate {

	public static void main(String[] args) {
		FournisseurDaoJdbc f = new FournisseurDaoJdbc();

		f.update("FDM SA", "FDM SARL");

		/* Affichage liste fournisseur pour contrôle des tests */
		ArrayList<Fournisseur> lFour = f.extraire();
		f.afficheListFournisseur(lFour);
		f.closeConnection();
	}
}
