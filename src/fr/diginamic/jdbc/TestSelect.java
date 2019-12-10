package fr.diginamic.jdbc;

import java.awt.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.diginamic.jdbc.entites.Fournisseur;

public class TestSelect {

	public static void main(String[] args) {
		boolean first = false;
		
		try {
			Connection maConnection = ConnexionDB.connecter("database");
			
			Statement monSt = maConnection.createStatement();
			
			ResultSet curseur = monSt.executeQuery("SELECT * FROM FOURNISSEUR");
			ArrayList<Fournisseur> listFour = new ArrayList<>(0);
			while (curseur.next()) {
				Integer id = curseur.getInt("ID");
				String nom = curseur.getString("NOM");
				listFour.add(new Fournisseur(id,nom));
				}
			curseur.close();
			maConnection.close();
			for (Fournisseur l : listFour) {
				System.out.println("ID : " + l.getId() + " NOM :" + l.getNom());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}