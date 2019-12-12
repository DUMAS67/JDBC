package fr.diginamic.jdbc;

/* Classe executable qui efface un Fournisseur dans la base des Fournissseurs*/

import java.util.ArrayList;

import fr.diginamic.jdbc.dao.FournisseurDaoJdbc;
import fr.diginamic.jdbc.entites.Fournisseur;

public class TestDelete {

	public static void main(String[] args) {
		Boolean delOk = false;
		FournisseurDaoJdbc f = new FournisseurDaoJdbc();
		
		/* Effacement d'un Fournisseur */

		delOk = f.delete(new Fournisseur(4, "La Maison des Peintures"));

		/* Affichage liste fournisseur pour contrôle des tests */
		ArrayList<Fournisseur> lFour = f.extraire();
		f.afficheListFournisseur(lFour);
		f.closeConnection();
	}
}
