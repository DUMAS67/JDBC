package fr.diginamic.jdbc.recensement.entites;

public class Ville {

	
	/** Représente une ville
	 * @author DIGINAMIC
	 *
	 */
		
		/** identification de la ville */
		private int idVille;
		/** nom de la  ville */
		private String nom;
		/** population totale */
		private int population;
		
		/** Pour table Ville, déclaration de Foreign Key
		 * 
		 */
		/** codeDepartement : code du département */
		private int idDepartement;
		
		/** codeRegion : code de la région */
		private int idRegion;
		
		/** Constructeur
		 * @param codeRegion code de la région
		 * @param nomRegion nom de la région
		 * @param codeDepartement code du département
		 * @param nom nom de la ville
		 * @param population population totale
		 */
		
		
		public Ville(int idVille, String nom, int population,int idDepartement, int idRegion) {
			super();
			this.idVille = idVille;
			this.nom = nom;
			this.population = population;
			this.idDepartement = idDepartement;
			this.idRegion = idRegion;
		}
		
		/**
		 * @return the idVille
		 */
		public int getIdVille() {
			return idVille;
		}
		/**
		 * @param idVille the idVille to set
		 */
		public void setIdVille(int idVille) {
			this.idVille = idVille;
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
		/**
		 * @return the population
		 */
		public int getPopulation() {
			return population;
		}
		/**
		 * @param population the population to set
		 */
		public void setPopulation(int population) {
			this.population = population;
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
		
		
		
		
}