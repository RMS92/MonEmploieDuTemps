package mon.edt.utils;

public class RecapShowEtudiant {

	private String date;
	private String heure;
	private String professeur;
	private String groupes;
	private String salle;
	private String site;

	public RecapShowEtudiant(String date, String heure, String professeur, String groupes, String salle, String site) {
		this.setDate(date);
		this.setHeure(heure);
		this.setProfesseur(professeur);
		this.setGroupes(groupes);
		this.setSalle(salle);
		this.setSite(site);
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getProfesseur() {
		return professeur;
	}

	public void setProfesseur(String professeur) {
		this.professeur = professeur;
	}

	public String getSalle() {
		return salle;
	}

	public void setSalle(String salle) {
		this.salle = salle;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGroupes() {
		return groupes;
	}

	public void setGroupes(String groupes) {
		this.groupes = groupes;
	}
}
