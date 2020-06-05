package mon.edt.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mon.edt.model.Seance;
import mon.edt.utils.RecapEtudiant;

public class RecapEtudiantTable extends Table<RecapEtudiant> {

	private String premierResultat;
	private String dernierResultat;
	private int cours_id;
	private ArrayList<Seance> u;
	private ArrayList<Seance> ubis;

	public RecapEtudiantTable(Connection connect) {
		super(connect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RecapEtudiant find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public String findPremierSeance(String nomMatiere) {

		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM seance "
					+ "JOIN cours c ON c.id = seance.cours_id " + "WHERE c.nom = ? " + "ORDER BY date ASC",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, nomMatiere);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {

				this.premierResultat = rs.getString("date");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.premierResultat;
	}

	public String findDernierSeance(String nomMatiere) {

		try {
			PreparedStatement ps = this.connect
					.prepareStatement(
							"SELECT * FROM seance " + "JOIN cours c ON c.id = seance.cours_id " + "WHERE c.nom = ? "
									+ "ORDER BY date DESC",
							ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, nomMatiere);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {

				this.dernierResultat = rs.getString("date");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.dernierResultat;
	}

	public int findNbHeure(int id, int cours_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT COUNT(cours_id) as count FROM cours " + "JOIN seance e ON e.cours_id =cours.id "
							+ "JOIN seance_etudiant s ON s.seance_id = e.id "
							+ "WHERE s.utilisateur_id = ? AND e.etat = 1 AND e.cours_id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ps.setInt(2, cours_id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {

				this.cours_id = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.cours_id;
	}

	public ArrayList<Seance> findAllDistinct(int id) {

		u = new ArrayList<Seance>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT DISTINCT nom, e.cours_id FROM cours " + "JOIN seance e ON e.cours_id = cours.id "
							+ "JOIN seance_etudiant s ON s.seance_id = e.id "
							+ "WHERE s.utilisateur_id = ? AND e.etat = 1",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				u.add(new Seance(rs.getString("nom"), rs.getInt("cours_id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}

	public ArrayList<Seance> findAllDistinctEnseignants(int id) {

		ubis = new ArrayList<Seance>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT DISTINCT nom, e.cours_id FROM cours " + "JOIN seance e ON e.cours_id = cours.id "
							+ "JOIN seance_enseignant s ON s.seance_id = e.id "
							+ "WHERE s.enseignant_id = ? AND e.etat = 1",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ubis.add(new Seance(rs.getString("nom"), rs.getInt("cours_id")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ubis;
	}

	public int findNbHeureEnseignant(int id, int cours_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT COUNT(cours_id) as count FROM cours " + "JOIN seance e ON e.cours_id = cours.id "
							+ "JOIN seance_enseignant s ON s.seance_id = e.id "
							+ "WHERE s.enseignant_id = ? AND e.etat = 1 AND e.cours_id = ?",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ps.setInt(2, cours_id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {

				this.cours_id = rs.getInt("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this.cours_id;
	}

}
