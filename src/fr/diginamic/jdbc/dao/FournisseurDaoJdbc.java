package fr.diginamic.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.diginamic.jdbc.entites.Fournisseur;

public class FournisseurDaoJdbc implements FournisseurDao {

	private ResultSet curseur;
	private Statement statement;

	public FournisseurDaoJdbc(ResultSet curseur) {
		super();
		this.curseur = curseur;
	}

	public FournisseurDaoJdbc(Statement statement) {
		super();
		this.statement = statement;
	}
	/* Insère les données de la base des Fournisseurs dans une base*/
	
		public ArrayList<Fournisseur> extraire() {

		ArrayList<Fournisseur> listFour = new ArrayList<>(0);
		try {
			while (curseur.next()) {
				Integer id = curseur.getInt("ID");
				String nom = curseur.getString("NOM");
				listFour.add(new Fournisseur(id, nom));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listFour;// TODO Auto-generated method stub
	}
		/* Ajoute une donnée de la base des Fournisseurs*/
	@Override
	public void insert(Fournisseur fournisseur) {
		// TODO Auto-generated method stub
		try {
			String id = String.valueOf(fournisseur.getId());
			String nom = fournisseur.getNom();
			String insertF = "INSERT INTO FOURNISSEUR (ID, NOM) values (" + id + ",'" + nom + "')";

			statement.executeUpdate(insertF);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/* Modifie une donnée de la base des Fournisseurs*/
	@Override
	public int update(String ancienNom, String nouveauNom) {
		int r = 0;
		try {
			String update = "UPDATE FOURNISSEUR SET NOM= '" + nouveauNom + "' WHERE NOM = '" + ancienNom + "'";
			r = statement.executeUpdate(update);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	/* Efface une donnée de la base des Fournisseurs*/
	
	@Override
	public boolean delete(Fournisseur fournisseur) {
		// TODO Auto-generated method stub
		String id = String.valueOf(fournisseur.getId());
		int intBol = 0;
		boolean ok = false;
		try {
			String delete = "DELETE FROM FOURNISSEUR WHERE ID=" + id;
			intBol = statement.executeUpdate(delete);
			if (intBol == 1) {
				ok = true;
			} else {
				ok = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}

	/* Affiche une liste des données de Fournisseurs*/
	public void afficheListFournisseur(ArrayList<Fournisseur> list) {
		for (Fournisseur l : list) {
			System.out.println("ID : " + l.getId() + " NOM :" + l.getNom());
		}
	}
}