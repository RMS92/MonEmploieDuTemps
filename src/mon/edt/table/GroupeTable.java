package mon.edt.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mon.edt.model.Groupe;
import mon.edt.model.Salle;
import mon.edt.model.Utilisateur;

public class GroupeTable extends Table<Groupe> {

	private Groupe g;
	private ArrayList<Integer> idG = new ArrayList<Integer>();;
	private Salle salle;
	private ArrayList<Integer> idS = new ArrayList<Integer>();
	

	public GroupeTable(Connection connect) {
		super(connect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Groupe find(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// Modis3 : find grp
	public Groupe findGroupeValider(String groupe, String promo, String date, String heure_d, String heure_f) {
		g = new Groupe();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM groupe "
				  + "JOIN seance_groupe sg ON sg.groupe_id = groupe.id "
				  + "JOIN seance s ON s.id = sg.seance_id "
				  + "JOIN promotion p ON p.id = groupe.id "
				  + "WHERE groupe.nom = ? AND p.nom = ? "
				  + "AND s.date = ? AND s.heure_debut = ? AND s.heure_fin = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, groupe);
			ps.setString(2, promo);
			ps.setString(3, date);
			ps.setString(4, heure_d);
			ps.setString(5, heure_f);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				g.setId(rs.getInt("id"));
				g.setNom(rs.getString("nom"));
				g.setPromotionId(rs.getString("promotion_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return g;
	}
	
	public ArrayList<Integer> findGroupeId(String nomG) {
		
		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT id FROM groupe "
				  + "WHERE nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, nomG);
		
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				idG.add(rs.getInt("id"));
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return idG;
	}
	
	//Modifs3 salle
	public Salle findSalleValider(String nom, String date, String heure_d, String heure_f) {
		salle = new Salle();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM salle "
				  + "JOIN seance_salle ss ON ss.salle_id = salle.id "
				  + "JOIN seance s ON s.id = ss.seance_id "
				  + "WHERE salle.nom = ? "
				  + "AND s.date = ? AND s.heure_debut = ? AND s.heure_fin = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, nom);
			ps.setString(2, date);
			ps.setString(3, heure_d);
			ps.setString(4, heure_f);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				salle.setId(rs.getInt("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return salle;
	}
	
	public ArrayList<Integer> findSalleId(String nomS) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT id FROM salle "
				  + "WHERE nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, nomS);
		
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				idS.add(rs.getInt("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return idS;
	}
	


}
