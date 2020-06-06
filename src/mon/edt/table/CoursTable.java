package mon.edt.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mon.edt.model.Cours;

public class CoursTable extends Table<Cours> {

	private Cours u;
	private String groupe;
	private String promo;
	private String prof;
	private String salle;

	public CoursTable(Connection connect) { 
		//connexion
		super(connect);
	}

	@Override
	public Cours find(int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM cours " + "WHERE id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				u = new Cours(rs.getInt("id"), rs.getString("nom"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}
	
	//modifs 4
	public String findGroupe(int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT groupe.nom FROM groupe " +
		                       "JOIN seance_groupe sg ON sg.groupe_id = groupe.id " +
					           "JOIN seance s ON s.id = sg.seance_id " +
		                       "WHERE s.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				groupe = rs.getString("groupe.nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return groupe;
	}
	
	public String findPromo(String groupe) {

		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT promotion.nom FROM promotion " +
		                       "JOIN groupe g ON g.promotion_id = promotion.id " +
		                       "WHERE g.nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, groupe);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				promo = rs.getString("promotion.nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return promo;
	}
	
	public String findProf(int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT utilisateur.nom FROM utilisateur " +
		                       "JOIN seance_enseignant sg ON sg.enseignant_id = utilisateur.id " +
		                       "JOIN seance s ON s.id = sg.seance_id "
		                       + "WHERE s.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				prof = rs.getString("utilisateur.nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return prof;
	}
	
	public String findSalle(int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT salle.nom FROM salle " +
		                       "JOIN seance_salle ss ON ss.salle_id = salle.id " +
		                       "JOIN seance s ON s.id = ss.seance_id "
		                       + "WHERE s.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				salle = rs.getString("salle.nom");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return salle;
	}

}
