package mon.edt.model;

public class Groupe {

	private int id;
	private String nom;
	private String promotionId;

	public Groupe() {
		
	}
	
	public Groupe(int id, String nom, String promotionId) { //constructeur
		this.setId(id);
		this.setNom(nom);
		this.setPromotionId(promotionId);
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

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
}
