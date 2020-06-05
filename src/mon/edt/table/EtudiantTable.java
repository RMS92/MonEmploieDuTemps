package mon.edt.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mon.edt.model.Etudiant;
import mon.edt.model.Groupe;

public class EtudiantTable extends Table<Etudiant> {

	private Etudiant u;
	private Groupe g;
	private String promo;
	//
	private ArrayList<Integer> coursId = new ArrayList<Integer>();
 	private int nbCoursReporting;
 	private String nomCours;
 	//
 	private ArrayList<Integer> enseignantId = new ArrayList<Integer>();
 	private String nomEnseignant;
 	private int nbCoursEnseignant;

	public EtudiantTable(Connection connect) {
		super(connect);
	}

	public Etudiant find(int id) {
		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * " + "FROM etudiant " + "JOIN utilisateur u ON etudiant.utilisateur_id = u.id "
							+ "WHERE utilisateur_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				u = new Etudiant(rs.getInt("utilisateur_id"), rs.getInt("numero"), rs.getInt("groupe_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}

	public Groupe findGroupe(int id, int groupe_id) {
		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * " + "FROM groupe " + "JOIN etudiant e ON e.groupe_id = groupe.id "
							+ "WHERE e.utilisateur_id = ? AND groupe.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ps.setInt(2, groupe_id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				g = new Groupe(rs.getInt("id"), rs.getString("nom"), rs.getString("promotion_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return g;
	}

	public String findPromo(int id) {
		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * " + "FROM promotion " + "JOIN etudiant e ON e.promotion_id = promotion.id "
							+ "WHERE e.utilisateur_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				promo = rs.getString("nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return promo;
	}

	// reporting

	public ArrayList<Integer> findCoursIdEtudiant(int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT DISTINCT cours.id FROM cours " +
					"JOIN seance s ON s.cours_id = cours.id " + 
					"JOIN seance_etudiant se ON se.seance_id = s.id " +
					"JOIN utilisateur u ON u.id = se.utilisateur_id " + 
					"WHERE u.id = ? "
					+ "ORDER BY cours.id ASC",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				coursId.add(rs.getInt("cours.id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return coursId;
	}

	public int findNbCoursReporting(int cours_id, int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT COUNT(seance.cours_id) as count FROM seance "
							+ "JOIN seance_etudiant se ON se.seance_id = seance.id "
							+ "JOIN utilisateur u ON u.id = se.utilisateur_id "
							+ "WHERE seance.cours_id = ? AND u.id = ? AND seance.etat = 1",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, cours_id);
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				nbCoursReporting = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nbCoursReporting;
	}
	
	public String findCoursNomEtudiant(int cours_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT nom FROM cours " +
					"WHERE id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, cours_id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				nomCours = rs.getString("nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nomCours;
	}
	
	//reporting2
	
	public ArrayList<Integer> findEnseeignantIdEtudiant(int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT DISTINCT utilisateur.id FROM utilisateur " + 
					"JOIN seance_enseignant se ON se.enseignant_id = utilisateur.id " + 
					"JOIN seance s ON s.id = se.seance_id " + 
					"JOIN seance_etudiant ss ON ss.seance_id = s.id " + 
					"JOIN utilisateur e ON e.id = ss.utilisateur_id " + 
					"WHERE e.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				enseignantId.add(rs.getInt("utilisateur.id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return enseignantId;
	}
	
	public String findNomEnseignant(int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT nom FROM utilisateur " +
			         "WHERE id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				nomEnseignant = rs.getString("nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nomEnseignant;
	}
	
	public int findNbCoursEnseignant(int id_enseignant, int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT COUNT(seance.id) as count FROM seance " + 
					"JOIN seance_enseignant se ON se.seance_id = seance.id " + 
					"JOIN utilisateur u ON u.id = se.enseignant_id " + 
					"JOIN seance_etudiant ss ON ss.seance_id = seance.id " + 
					"JOIN utilisateur uu ON uu.id = ss.utilisateur_id " + 
					"WHERE u.id = ? AND uu.id = ? AND seance.etat = 1",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id_enseignant);
			ps.setInt(2, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				nbCoursEnseignant = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nbCoursEnseignant;
	}

}
