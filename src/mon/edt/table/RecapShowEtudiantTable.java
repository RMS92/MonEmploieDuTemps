package mon.edt.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mon.edt.model.Seance;
import mon.edt.utils.RecapShowEtudiant;

public class RecapShowEtudiantTable extends Table<RecapShowEtudiant> {

	private ArrayList<Seance> u;
	private ArrayList<Seance> ubis;
	private ArrayList<Integer> id;
	private String nom = "";
	private String groupes = "";
	private String salle;
	private String site;

	public RecapShowEtudiantTable(Connection connect) {
		super(connect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RecapShowEtudiant find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Seance> findAll(int id, String matiere) {

		u = new ArrayList<Seance>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM seance " + "JOIN cours c ON c.id = seance.cours_id "
							+ "JOIN seance_etudiant s ON s.seance_id = seance.id "
							+ "WHERE s.utilisateur_id = ? AND etat = 1 AND c.nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ps.setString(2, matiere);
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

	public ArrayList<Seance> findAllEnseignant(int id, String matiere) {

		ubis = new ArrayList<Seance>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM seance " + "JOIN cours c ON c.id = seance.cours_id "
							+ "JOIN seance_enseignant s ON s.seance_id = seance.id "
							+ "WHERE s.enseignant_id = ? AND etat = 1 AND c.nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ps.setString(2, matiere);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ubis.add(new Seance(rs.getInt("id"), rs.getInt("semaine"), rs.getString("date"),
						rs.getString("heure_debut"), rs.getString("heure_fin"), rs.getInt("etat"),
						rs.getInt("cours_id"), rs.getInt("type_id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ubis;
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
					"SELECT * FROM utilisateur " + "JOIN enseignant e ON e.utilisateur_id = utilisateur.id "
							+ "JOIN seance s ON s.cours_id = e.cours_id " + "JOIN cours c ON c.id = s.cours_id "
							+ "WHERE utilisateur.id = ? AND s.type_id = ? AND c.nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ps.setInt(2, type_cours);
			ps.setString(3, matiere);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {

				nom = rs.getString("nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nom;
	}

	public String findGroupes(int seance_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT nom from groupe " + "JOIN seance_groupe sg ON sg.groupe_id = groupe.id "
							+ "JOIN seance s ON s.id = sg.seance_id " + "WHERE s.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, seance_id);
			ResultSet rs = ps.executeQuery();

			int i = 0;

			if (rs.first()) {
				do {
					if (i > 0) {
						groupes += rs.getString("nom") + " ";
					} else {
						groupes = rs.getString("nom") + " ";
					}
					i++;

				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return groupes;
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

	public String findSite(String salle) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM site " + "JOIN salle s ON s.site_id = site.id " + "WHERE s.nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, salle);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				site = rs.getString("nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return site;
	}

}
