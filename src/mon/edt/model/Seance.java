package mon.edt.model;

public class Seance {

	private int id;
	private int semaine;
	private String date;
	private String heure_debut;
	private String heure_fin;
	private int etat;
	private int cours_id;
	private int type_id;

	private int cours_idRecap;
	private String nomRecap;

	public Seance() {

	}

	public Seance(int id) {
		this.id = id;
	}

	public Seance(String nomRecap, int cours_idRecap) { //constructeur
		this.setNomRecap(nomRecap);
		this.setCours_idRecap(cours_idRecap);
	}
	
	public Seance(String nomRecap) {
		this.nomRecap = nomRecap;
	}

	public Seance(int id, int semaine, String date, String heure_debut, String heure_fin, int etat, int cours_id,
			int type_id) {

		this.id = id;
		this.semaine = semaine;
		this.date = date;
		this.heure_debut = heure_debut;
		this.heure_fin = heure_fin;
		this.etat = etat;
		this.cours_id = cours_id;
		this.type_id = type_id;
	}

	public Seance(Seance seance) {
		this.id = seance.getId();
		this.semaine = seance.getSemaine();
		this.date = seance.getDate();
		this.heure_debut = seance.getHeure_debut();
		this.etat = seance.getEtat();
		this.heure_fin = seance.getHeure_fin();
		this.cours_id = seance.getCours_id();
		this.type_id = seance.getType_id();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSemaine() {
		return this.semaine;
	}

	public void setSemaine(int semaine) {
		this.semaine = semaine;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHeure_debut() {
		return this.heure_debut;
	}

	public void setHeure_debut(String heure_debut) {
		this.heure_debut = heure_debut;
	}

	public String getHeure_fin() {
		return this.heure_fin;
	}

	public void setHeure_fin(String heure_fin) {
		this.heure_fin = heure_fin;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public int getCours_id() {
		return cours_id;
	}

	public void setCours_id(int cours_id) {
		this.cours_id = cours_id;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getNomRecap() {
		return nomRecap;
	}

	public void setNomRecap(String nomRecap) {
		this.nomRecap = nomRecap;
	}

	public int getCours_idRecap() {
		return cours_idRecap;
	}

	public void setCours_idRecap(int cours_idRecap) {
		this.cours_idRecap = cours_idRecap;
	}

}
