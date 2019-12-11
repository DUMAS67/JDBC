package fr.diginamic.jdbc.entites;
/* Classe Article référenceement de 
 * l'équivalent d'une table ARTICLE*/
public class Article {
	private int id; // id de l'article
    private String ref; // référence de l'article
    private String designation; // désignation de l'article
    private double prix;// prix de l'article
    private int idFou;// Foreign Key pour la table Fournisseur
    
    
	public Article(int id, String ref, String designation, double prix, int idFou) {
		super();
		this.id = id;
		this.ref = ref;
		this.designation = designation;
		this.prix = prix;
		this.idFou = idFou;
		
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getRef() {
		return ref;
	}


	public void setRef(String ref) {
		this.ref = ref;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public double getPrix() {
		return prix;
	}


	public void setPrix(double prix) {
		this.prix = prix;
	}


	public int getIdFou() {
		return idFou;
	}


	public void setIdFou(int idFou) {
		this.idFou = idFou;
	}
	
}