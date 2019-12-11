package fr.diginamic.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.diginamic.jdbc.dao.FournisseurDaoJdbc;
import fr.diginamic.jdbc.entites.Fournisseur;

public class TestSelect {

	/* Classe executable qui scrute la base des Fournissseurs*/
	public static void main(String[] args) {

		try {
			Connection maConnection = ConnexionDB.connecter("database");
			Statement monSt = maConnection.createStatement();
			
			/* Affichage liste fournisseur pour contrôle des tests*/
			ResultSet curseur = monSt.executeQuery("SELECT * FROM FOURNISSEUR ");
			FournisseurDaoJdbc a = new FournisseurDaoJdbc(curseur);
			ArrayList<Fournisseur> lFour = a.extraire();
			a.afficheListFournisseur(lFour);
			
			/* fermeture des pointeurs sur le fichier de configuration et de la base*/
			curseur.close();
			monSt.close();
			maConnection.close();
			a.afficheListFournisseur(lFour);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}