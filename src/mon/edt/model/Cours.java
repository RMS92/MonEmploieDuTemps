package mon.edt.model;

public class Cours {

	private int id;
	private String nom;

	public Cours(int id, String nom) {
		this.setId(id);
		this.setNom(nom);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
