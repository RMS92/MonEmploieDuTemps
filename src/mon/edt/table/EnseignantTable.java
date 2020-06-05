package mon.edt.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mon.edt.model.Enseignant;
import mon.edt.model.Utilisateur;

public class EnseignantTable extends Table<Enseignant> {

	private Enseignant u;
	private ArrayList<Integer> ids;
	private Utilisateur enseignant;
	//
	private ArrayList<Integer> groupeIds = new ArrayList<Integer>();
	private String nomGrp;
	private int nbCours;

	public EnseignantTable(Connection connect) {
		super(connect);
	}

	@Override
	public Enseignant find(int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM enseignant " + "JOIN utilisateur u ON u.id = enseignant.utilisateur_id "
							+ "JOIN cours c ON c.id = enseignant.cours_id " + "WHERE utilisateur_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				u = new Enseignant(rs.getInt("utilisateur_id"), rs.getInt("cours_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}

	// Admin

	public ArrayList<Integer> findAllIds() {

		ids = new ArrayList<Integer>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT DISTINCT id FROM utilisateur " + "JOIN enseignant e ON e.utilisateur_id = utilisateur.id "
							+ "ORDER BY utilisateur.nom ASC",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ids.add(rs.getInt("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ids;
	}

	public Utilisateur findEnseignant(int id) {
		enseignant = new Utilisateur();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM utilisateur " + "WHERE utilisateur.id = ?", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {

				enseignant.setId(rs.getInt("id"));
				enseignant.setEmail(rs.getString("email"));
				enseignant.setNom(rs.getString("nom"));
				enseignant.setPrenom(rs.getString("prenom"));
				enseignant.setDroit(rs.getInt("droit"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return enseignant;
	}
	
	//reporting
	
	public ArrayList<Integer> findGrpIdEnseignant(int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT DISTINCT groupe.id FROM groupe " + 
					"JOIN seance_groupe sg ON sg.groupe_id = groupe.id " + 
					"JOIN seance s ON s.id = sg.seance_id " + 
					"JOIN seance_enseignant se ON se.seance_id = s.id " + 
					"JOIN utilisateur u ON u.id = se.enseignant_id " + 
					"WHERE u.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				groupeIds.add(rs.getInt("groupe.id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return groupeIds;
	}
	
	public String findNomGrp(int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT groupe.nom, p.nom FROM groupe " + 
					"JOIN promotion p ON p.id = groupe.promotion_id " + 
					"WHERE groupe.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				nomGrp =  rs.getString("p.nom") + rs.getString("groupe.nom") + " ";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nomGrp;
	}
	
	public int findNbCoursEnseignant(int id, int groupe_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT COUNT(seance.id) as count FROM seance " + 
					"JOIN seance_groupe sg ON sg.seance_id = seance.id " + 
					"JOIN groupe g ON g.id = sg.groupe_id " + 
					"JOIN seance_enseignant se ON se.seance_id = seance.id " + 
					"JOIN utilisateur u ON u.id = se.enseignant_id " + 
					"WHERE u.id = ? AND g.id = ? AND seance.etat = 1",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ps.setInt(2, groupe_id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				nbCours = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nbCours;
	}
	
	/**/
}
