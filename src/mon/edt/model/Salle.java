package mon.edt.model;

public class Salle {

	private int id;
	private String nom;
	private int capacite;
	private int site_id;
	
	public Salle() {
		
	}
	
	public Salle(int id, String nom, int capacite, int site_id) { //constructeur
		this.setId(id);
		this.setNom(nom);
		this.setCapacite(capacite);
		this.setSite_id(site_id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public int getSite_id() {
		return site_id;
	}

	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
}
