package mon.edt.model;

public class Etudiant {

	private int utilisateur_id;
	private int numero;
	private int groupe_id;

	public Etudiant(int utilisateur_id) {
		this.utilisateur_id = utilisateur_id;
	}

	public Etudiant(int utilisateur_id, int numero, int groupe_id) {

		this.utilisateur_id = utilisateur_id;
		this.numero = numero;
		this.groupe_id = groupe_id;
	}

	public int getUtilisateur_id() {
		return this.utilisateur_id;
	}

	public void setUtilisateur_id(int utilisateur_id) {
		this.utilisateur_id = utilisateur_id;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getGroupe_id() {
		return this.groupe_id;
	}

	public void setGroupe_id(int groupe_id) {
		this.groupe_id = groupe_id;
	}

}
