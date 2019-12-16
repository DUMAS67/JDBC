/**
 * 
 */
package fr.diginamic.jdbc.recensement.entites;

/**
 * @author DUMAS
 *
 */
public class Departement {

	/* code du Département*/
	private int idDepartement;
	/* Foreign Key pour la table Region*/
	private int idFRegion;

	/**
	 * @param idDepartement code du Département
	 * @param idFRegion Foreign Key pour la table Region
	 */
	public Departement(int idDepartement, int idFRegion) {
		super();
		this.idDepartement = idDepartement;
		this.idFRegion = idFRegion;
	}

	/**
	 * @return the idDepartement
	 */
	public int getIdDepartement() {
		return idDepartement;
	}

	/**
	 * @param idDepartement the idDepartement to set
	 */
	public void setIdDepartement(int idDepartement) {
		this.idDepartement = idDepartement;
	}

	/**
	 * @return the idFRegion
	 */
	public int getIdFRegion() {
		return idFRegion;
	}

	/**
	 * @param idFRegion the idFRegion to set
	 */
	public void setIdFRegion(int idFRegion) {
		this.idFRegion = idFRegion;
	}
	
	
	
}
