package mon.edt.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mon.edt.model.Salle;
import mon.edt.model.Seance;
import mon.edt.utils.EdtColonne;

public class EdtReferentTable extends Table<EdtColonne> {

	private ArrayList<Seance> u;
	// SEARCH
	private ArrayList<Seance> s;
	// Prof
	private ArrayList<Integer> p;
	// salle
	private ArrayList<Seance> ss;
	// promos
	private ArrayList<Seance> sp;

	private ArrayList<String> date;
	private ArrayList<Integer> id;
	private String nomMatiere;
	private String nomEnseignant = "";
	private String nomGroupe;
	private String nomPromo;
	private String salle = "";
	private String capacite;
	private String type;
	private int etat;

	//
	private ArrayList<Salle> salles;

	public EdtReferentTable(Connection connect) {
		super(connect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EdtColonne find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Seance> findAll(int id, int semaine) {

		u = new ArrayList<Seance>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM seance " + "JOIN cours c ON c.id = seance.cours_id "
							+ "JOIN seance_etudiant s ON s.seance_id = seance.id "
							+ "WHERE s.utilisateur_id = ? AND seance.semaine = ? " + "ORDER BY seance.heure_debut ASC",
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
					// enseignant
					+ "JOIN seance_enseignant ss ON ss.seance_id = seance.id "
					+ "JOIN utilisateur u ON u.id = ss.enseignant_id " +

					// etudiant
					"JOIN seance_etudiant se ON se.seance_id = seance.id "
					+ "JOIN utilisateur uu ON uu.id = se.utilisateur_id " +

					// Cours
					"JOIN cours c ON c.id = seance.cours_id " +
					// groupe
					"JOIN seance_groupe sg ON sg.seance_id = seance.id " + "JOIN groupe g ON g.id = sg.groupe_id " +

					// type
					"JOIN type_cours tc ON tc.id = seance.type_id " +

					"WHERE seance.semaine = ? " + "AND ( c.nom LIKE ? OR u.nom LIKE ? OR g.nom LIKE ? OR tc.name LIKE ?"
					+ "OR uu.nom LIKE ?) " + "ORDER BY seance.heure_debut", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setInt(1, semaine);
			ps.setString(2, matiere + "%");
			ps.setString(3, matiere + "%");
			ps.setString(4, "%" + matiere + "%");
			ps.setString(5, "%" + matiere + "%");
			ps.setString(6, matiere + "%");
			ResultSet rs = ps.executeQuery();

			/*
			 * SELECT * FROM seance JOIN seance_enseignant ss ON ss.seance_id = seance.id
			 * JOIN utilisateur u ON u.id = ss.enseignant_id WHERE u.nom LIKE 'Segado'
			 */

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

	// FIND PROF GRILLE
	public ArrayList<Integer> findAllProf(int id, int semaine, String matiere) {

		p = new ArrayList<Integer>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT DISTINCT seance.id FROM seance " + "JOIN seance_enseignant se ON se.seance_id = seance.id "
							+ "JOIN utilisateur u ON u.id = se.enseignant_id " +

							"JOIN seance_etudiant s ON s.seance_id = seance.id "
							+ "JOIN etudiant e ON e.utilisateur_id = s.utilisateur_id "
							+ "JOIN groupe g ON g.id = e.groupe_id "
							+ "JOIN utilisateur uu ON uu.id = e.utilisateur_id " +

							"WHERE seance.semaine = ? "

							+ "AND (u.nom LIKE ? OR uu.nom LIKE ?) " + "ORDER BY seance.heure_debut",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setInt(1, semaine);
			ps.setString(2, matiere + "%");
			ps.setString(3, matiere + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				p.add(rs.getInt("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	public Seance findSeance(int id) {
		Seance seance = new Seance();

		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM seance " + "WHERE seance.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				seance.setId(rs.getInt("id"));
				seance.setSemaine(rs.getInt("semaine"));
				seance.setDate(rs.getString("date"));
				seance.setHeure_debut(rs.getString("heure_debut"));
				seance.setHeure_fin(rs.getString("heure_fin"));
				seance.setEtat(rs.getInt("etat"));
				seance.setCours_id(rs.getInt("cours_id"));
				seance.setType_id(rs.getInt("type_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return seance;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public ArrayList<Seance> findSearchSalle(int semaine, String salle) {

		ss = new ArrayList<Seance>();

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

	public ArrayList<Seance> findSearchPromos(int semaine, String promos) {

		String mots[] = promos.split(" ");

		sp = new ArrayList<Seance>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM seance " + "JOIN seance_etudiant se ON se.seance_id = seance.id "
							+ "JOIN etudiant e ON e.utilisateur_id = se.utilisateur_id "
							+ "JOIN promotion p ON p.id = e.promotion_id " + "JOIN groupe g ON g.id = e.groupe_id "
							+ "WHERE seance.semaine = ? AND p.nom LIKE ? AND g.nom LIKE ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, semaine);
			ps.setString(2, mots[0]);
			ps.setString(3, mots[1]);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				sp.add(new Seance(rs.getInt("id"), rs.getInt("semaine"), rs.getString("date"),
						rs.getString("heure_debut"), rs.getString("heure_fin"), rs.getInt("etat"),
						rs.getInt("cours_id"), rs.getInt("type_id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sp;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////

	public ArrayList<String> findToutesDates(int id, int semaine) {
		date = new ArrayList<String>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT DISTINCT date FROM seance " + "JOIN seance_etudiant se ON se.seance_id = seance.id "
							+ "JOIN utilisateur u ON u.id = se.utilisateur_id "
							+ "WHERE u.id = ? AND semaine = ? ORDER BY date ASC",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ps.setInt(2, semaine);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				do {
					date.add(rs.getString("date"));
				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return date;
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

	// reporting

	public ArrayList<Salle> finAllSalle() {

		salles = new ArrayList<Salle>();
		
		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM salle",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();
         
			while (rs.next()) {
				salles.add(new Salle( rs.getInt("id"), rs.getString("nom"), rs.getInt("capacite"), 
						rs.getInt("site_id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return salles;
	}
	/**/

}
