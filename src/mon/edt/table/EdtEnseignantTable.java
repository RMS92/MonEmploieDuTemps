package mon.edt.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mon.edt.model.Seance;
import mon.edt.utils.EdtColonne;

public class EdtEnseignantTable extends Table<EdtColonne> {

	private ArrayList<Seance> u;
	// SEARCH
	private ArrayList<Seance> s;
	private ArrayList<Seance> ss;
	private ArrayList<Integer> id;
	private String nomMatiere;
	private String nomEnseignant = "";
	private String nomGroupe;
	private String nomPromo;
	private String salle = "";
	private String capacite;
	private String type;
	private int etat;

	public EdtEnseignantTable(Connection connect) {
		super(connect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EdtColonne find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Seance> findAll(int id, int semaine) {  //méthode de recherche pour tt afficher 

		u = new ArrayList<Seance>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM seance " + "JOIN cours c ON c.id = seance.cours_id "
							+ "JOIN seance_enseignant s ON s.seance_id = seance.id "
							+ "WHERE s.enseignant_id = ? AND seance.semaine = ? " + "ORDER BY seance.heure_debut ASC",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ps.setInt(2, semaine);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				u.add(new Seance(rs.getInt("id"), rs.getInt("semaine"), rs.getString("date"),
						rs.getString("heure_debut"), rs.getString("heure_fin"), rs.getInt("etat"),
						rs.getInt("cours_id"), rs.getInt("type_id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}

	public ArrayList<Seance> findAllSearch(int id, int semaine, String matiere) {

		s = new ArrayList<Seance>();

		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM seance "
					//enseignant
					+ "JOIN seance_enseignant ss ON ss.seance_id = seance.id " + 
					"JOIN utilisateur u ON u.id = ss.enseignant_id " +

					//etudiant
					"JOIN seance_enseignant se ON se.seance_id = seance.id " + 
					"JOIN utilisateur uu ON uu.id = se.enseignant_id " +

					//Cours
					"JOIN cours c ON c.id = seance.cours_id " +
					//groupe
					"JOIN seance_groupe sg ON sg.seance_id = seance.id "
					+ "JOIN groupe g ON g.id = sg.groupe_id " +

					//type
					"JOIN type_cours tc ON tc.id = seance.type_id " +

					"WHERE seance.semaine = ? AND uu.id = ? " 
					+ "AND ( c.nom LIKE ? OR u.nom LIKE ? OR g.nom LIKE ? OR tc.name LIKE ?) " + "ORDER BY seance.heure_debut", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, semaine);
			ps.setInt(2, id);
			ps.setString(3, matiere + "%");
			ps.setString(4, matiere + "%");
			ps.setString(5, "%" + matiere + "%");
			ps.setString(6, "%" + matiere + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				s.add(new Seance(rs.getInt("id"), rs.getInt("semaine"), rs.getString("date"),
						rs.getString("heure_debut"), rs.getString("heure_fin"), rs.getInt("etat"),
						rs.getInt("cours_id"), rs.getInt("type_id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return s;
	}

	public ArrayList<Seance> findSearchSalle(int semaine, String salle) { //recherche salle
		

		ss = new ArrayList<Seance>(); //aa

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM seance " + "JOIN seance_salle ss ON ss.seance_id = seance.id "
							+ "JOIN salle s ON s.id = ss.salle_id " + "WHERE seance.semaine = ? AND s.nom LIKE ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, semaine);
			ps.setString(2, salle + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ss.add(new Seance(rs.getInt("id"), rs.getInt("semaine"), rs.getString("date"),
						rs.getString("heure_debut"), rs.getString("heure_fin"), rs.getInt("etat"),
						rs.getInt("cours_id"), rs.getInt("type_id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ss;
	}

	public String findNomMatieres(int id, int seance_id) {

		// ETAT A CHANGER PEUT ETRE
		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT cours.nom FROM cours " + "JOIN seance s ON s.cours_id = cours.id " + "JOIN utilisateur u "
							+ "WHERE u.id = ? AND s.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ps.setInt(2, seance_id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				nomMatiere = rs.getString("nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nomMatiere;
	}

	public ArrayList<Integer> findEnseignantId(int seance_id) {
		id = new ArrayList<Integer>();
		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT DISTINCT utilisateur.id FROM utilisateur "
					+ "JOIN enseignant e ON e.utilisateur_id = utilisateur.id "
					+ "JOIN seance_enseignant se ON se.enseignant_id = e.utilisateur_id " + "WHERE se.seance_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, seance_id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				id.add(rs.getInt("id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	public String findEnseignantNom(int id, int type_cours, String matiere) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT DISTINCT utilisateur.nom FROM utilisateur "
							+ "JOIN enseignant e ON e.utilisateur_id = utilisateur.id "
							+ "JOIN seance s ON s.cours_id = e.cours_id " + "JOIN cours c ON c.id = s.cours_id "
							+ "WHERE utilisateur.id = ? AND s.type_id = ? AND c.nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ps.setInt(2, type_cours);
			ps.setString(3, matiere);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {

				nomEnseignant = rs.getString("nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nomEnseignant;
	}

	public String findGroupe(int seance_id) {

		try {
			PreparedStatement ps = this.connect
					.prepareStatement(
							"SELECT * FROM groupe " + "JOIN seance_groupe sg ON sg.groupe_id = groupe.id "
									+ "WHERE sg.seance_id = ?",
							ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, seance_id);
			ResultSet rs = ps.executeQuery();

			int i = 0;

			if (rs.first()) {
				do {
					if (i > 0) {
						nomGroupe += rs.getString("nom") + " ";
					} else {
						nomGroupe = rs.getString("nom") + " ";
					}
					i++;

				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nomGroupe;
	}

	public String findPromotion(String groupe_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM promotion " + "JOIN groupe g ON g.promotion_id = promotion.id " + "WHERE g.nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, groupe_id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				nomPromo = rs.getString("nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nomPromo;
	}

	public String findType(int seance_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM type_cours " + "JOIN seance s ON s.type_id = type_cours.id " + "WHERE s.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, seance_id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				type = rs.getString("name");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return type;
	}

	public String findSalle(int seance_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM salle " + "JOIN seance_salle ss ON ss.salle_id = salle.id "
							+ "JOIN seance s ON s.id = ss.seance_id " + "WHERE s.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, seance_id);
			ResultSet rs = ps.executeQuery();

			int i = 0;

			if (rs.first()) {
				do {
					if (i > 0) {
						salle += rs.getString("nom") + " ";
					} else {
						salle = rs.getString("nom") + " ";
					}
					i++;

				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return salle;
	}

	public String findCapacite(int seance_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM salle " + "JOIN seance_salle ss ON ss.salle_id = salle.id "
							+ "JOIN seance s ON s.id = ss.seance_id " + "WHERE s.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, seance_id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				capacite = rs.getString("capacite");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return capacite;
	}

	public int findEtat(int seance_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT etat FROM seance " + "WHERE seance.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, seance_id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				etat = rs.getInt("etat");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return etat;
	}

}
