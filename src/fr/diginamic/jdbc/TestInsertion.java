package fr.diginamic.jdbc;

/* Classe executable qui insère une valeur dans la base des Fournissseurs*/

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



import fr.diginamic.jdbc.dao.FournisseurDaoJdbc;
import fr.diginamic.jdbc.entites.Fournisseur;

public class TestInsertion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection maConnection = null;

		try {
			maConnection = ConnexionDB.connecter("database");
			Statement monSt = maConnection.createStatement();
			FournisseurDaoJdbc fInsert = new FournisseurDaoJdbc(monSt);
			fInsert.insert(new Fournisseur(4, "La Maison des Peintures"));
			
			maConnection.commit();
			
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
			try {
				maConnection.rollback();
				System.out.println("erreur revient sur les données d'avant");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
