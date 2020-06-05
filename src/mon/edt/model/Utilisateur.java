package mon.edt.model;

import java.io.Serializable;

public class Utilisateur implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String email;
	private String password;
	private String nom;
	private String prenom;
	private int droit;
	
	public Utilisateur() {
		
	}
	
	public Utilisateur(int id) {
		this.id = id;
	}
	
	public Utilisateur(String nom) {
		this.nom = nom;
	}
	
	public Utilisateur(int id, String email, String nom, String prenom, int droit) {
		
		this.id = id;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.droit = droit;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPwd() {
		return this.password;
	}
	
	public void setPwd(String password) {
		this.password = password;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public int getDroit() {
		return this.droit;
	}
	
	public void setDroit(int droit) {
		this.droit = droit;
	}

}
