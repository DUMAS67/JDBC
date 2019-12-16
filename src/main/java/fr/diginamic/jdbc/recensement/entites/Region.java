/**
 * 
 */
package fr.diginamic.jdbc.recensement.entites;

/**
 * @author DUMAS
 *
 */
public class Region {
	
	/* Code de la Région */
	private int idRegion;
	
	/* Nom de la Region */
	private String nom;

	/**
	 * @param idRegion Code de la Région
	 * @param nom Nom de la Region
	 */
	public Region(int idRegion, String nom) {
		super();
		this.idRegion = idRegion;
		this.nom = nom;
	}

	/**
	 * @return the idRegion
	 */
	public int getIdRegion() {
		return idRegion;
	}

	/**
	 * @param idRegion the idRegion to set
	 */
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	
	

}
