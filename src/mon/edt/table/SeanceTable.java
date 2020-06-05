package mon.edt.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mon.edt.model.Seance;

public class SeanceTable extends Table<Seance> {

	private ArrayList<Seance> u;
	private ArrayList<Integer> ids;
	private Seance s;
	private Seance ss;
	private Seance sa;
	private ArrayList<Seance> sc = new ArrayList<Seance>();

	public SeanceTable(Connection connect) {
		super(connect);
	}

	@Override
	public Seance find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Seance> findAll(int id) {

		u = new ArrayList<Seance>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM seance " + "JOIN seance_etudiant s ON s.seance_id = seance.id "
							+ "WHERE s.utilisateur_id = ? AND etat = 1",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
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
	
	//Admin
	public ArrayList<Integer> findAllIds() {

		ids = new ArrayList<Integer>();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT DISTINCT id FROM seance ORDER BY date ASC",
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
	
	public Seance findSeance(int id) {

		s = new Seance();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM seance "
					+ "JOIN cours c ON seance.cours_id = c.id "
					+ "WHERE seance.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				s.setId(rs.getInt("id"));
				s.setDate(rs.getString("date"));
				s.setNomRecap(rs.getString("nom"));
				s.setHeure_debut(rs.getString("heure_debut"));
				s.setHeure_fin(rs.getString("heure_fin"));
				s.setEtat(rs.getInt("etat"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return s;
	}
	
	public Seance findSeanceEnseignant(String nomE, String date, String heure_d, String heure_f) {

		ss = new Seance();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM seance "
					+ "JOIN seance_enseignant se ON se.seance_id = seance.id "
					+ "JOIN utilisateur u ON u.id = se.enseignant_id "
					+ "WHERE u.nom = ? AND seance.date = ? AND seance.heure_debut = ? AND seance.heure_fin = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, nomE);
			ps.setString(2, date);
			ps.setString(3, heure_d);
			ps.setString(4, heure_f);
			ResultSet rs = ps.executeQuery();

			if(rs.first()) {
				ss.setId(rs.getInt("id"));
				ss.setSemaine(rs.getInt("semaine"));
				ss.setDate(rs.getString("date"));
				ss.setHeure_debut(rs.getString("heure_debut"));
				ss.setHeure_fin( rs.getString("heure_fin"));
				ss.setEtat(rs.getInt("etat"));
				ss.setCours_id(rs.getInt("cours_id"));
				ss.setType_id(rs.getInt("type_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ss;
	}
	
	
	///
	public Seance findSeanceValider(String date, String nom, String heure_d, String heure_f) {

		sa = new Seance();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM seance " +
					"JOIN cours c ON c.id = seance.cours_id " + 
					"WHERE seance.date = ? AND seance.heure_debut = ? AND seance.heure_fin = ? "
					+ "AND c.nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, date);
			ps.setString(2, heure_d);
			ps.setString(3, heure_f);
			ps.setString(4, nom);
			
			ResultSet rs = ps.executeQuery();

			if(rs.first()) {
				sa.setId(rs.getInt("id"));
				sa.setSemaine(rs.getInt("semaine"));
				sa.setDate(rs.getString("date"));
				sa.setHeure_debut(rs.getString("heure_debut"));
				sa.setHeure_fin( rs.getString("heure_fin"));
				sa.setEtat(rs.getInt("etat"));
				sa.setCours_id(rs.getInt("cours_id"));
				sa.setType_id(rs.getInt("type_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sa;
	}
	
	public ArrayList<Seance> findCours(String nomE) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT DISTINCT c.nom as nom FROM seance "
					+ "JOIN cours c ON seance.cours_id = c.id "
					+ "JOIN enseignant e ON e.cours_id = c.id "
					+ "JOIN utilisateur u ON u.id = e.utilisateur_id "
					+ "WHERE u.nom = ? "
					+ "ORDER BY c.id ASC",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, nomE);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				sc.add(new Seance(rs.getString("nom")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sc;
	}
	
	public int findLastSeanceID() {

		int id = 0;

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT id FROM seance " + 
					"ORDER BY id DESC",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = ps.executeQuery();

			if(rs.first()) {
				id = rs.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	
}
