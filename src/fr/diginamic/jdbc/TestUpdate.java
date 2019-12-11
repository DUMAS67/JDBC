package fr.diginamic.jdbc;

/* Classe executable qui change une valeur dans la base des Fournissseurs*/

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.diginamic.jdbc.dao.FournisseurDaoJdbc;
import fr.diginamic.jdbc.entites.Fournisseur;

public class TestUpdate {

	public static void main(String[] args) {
		try {
			Connection maConnection = ConnexionDB.connecter("database");
			Statement monSt = maConnection.createStatement();
			FournisseurDaoJdbc up = new FournisseurDaoJdbc(monSt);
			int upresult = up.update("FDM SA", "FDM SARL");
			if (upresult == 1) {
				maConnection.commit();
			} else {
				maConnection.rollback();
			}
			/* Affichage liste fournisseur pour contrôle des tests*/
			ResultSet curseur = monSt.executeQuery("SELECT * FROM FOURNISSEUR ");
			FournisseurDaoJdbc a = new FournisseurDaoJdbc(curseur);
			ArrayList<Fournisseur> lFour = a.extraire();
			a.afficheListFournisseur(lFour);
			
			/* fermeture des pointeurs sur le fichier de configuration et de la base*/
			curseur.close();
			monSt.close();
			maConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
