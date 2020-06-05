package mon.edt.utils;

public class RecapEtudiant {

	private String matiere;
	private String premiereSeance;
	private String derniereSeance;
	private String duree;
	private int nbSeance;

	public RecapEtudiant(String matiere, String premiereSeance, String derniereSeance, String duree, int nbSeance) {
		this.setMatiere(matiere);
		this.setPremiereSeance(premiereSeance);
		this.setDerniereSeance(derniereSeance);
		this.setDuree(duree);
		this.setNbSeance(nbSeance);
	}

	public String getMatiere() {
		return matiere;
	}

	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}

	public String getPremiereSeance() {
		return premiereSeance;
	}

	public void setPremiereSeance(String premiereSeance) {
		this.premiereSeance = premiereSeance;
	}

	public String getDerniereSeance() {
		return derniereSeance;
	}

	public void setDerniereSeance(String derniereSeance) {
		this.derniereSeance = derniereSeance;
	}

	public String getDuree() {
		return duree;
	}

	public void setDuree(String duree) {
		this.duree = duree;
	}

	public int getNbSeance() {
		return nbSeance;
	}

	public void setNbSeance(int nbSeance) {
		this.nbSeance = nbSeance;
	}
}
