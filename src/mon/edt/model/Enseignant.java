package mon.edt.model;

public class Enseignant {

	private int utilisateur_id;
	private int cours_id;

	public Enseignant(int utilisateur_id) {
		this.utilisateur_id = utilisateur_id;
	}

	public Enseignant(int utilisateur_id, int cours_id) { //constructeur

		this.utilisateur_id = utilisateur_id;
		this.cours_id = cours_id;
	}

	public int getUtilisateur_id() {
		return this.utilisateur_id;
	}

	public void setUtilisateur_id(int utilisateur_id) {
		this.utilisateur_id = utilisateur_id;
	}

	public int getCours_id() {
		return this.cours_id;
	}

	public void setCours_id(int cours_id) {
		this.cours_id = cours_id;
	}
}
