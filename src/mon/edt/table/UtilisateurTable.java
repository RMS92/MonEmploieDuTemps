package mon.edt.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mon.edt.model.Groupe;
import mon.edt.model.Utilisateur;

public class UtilisateurTable extends Table<Utilisateur> {

	private Utilisateur u;
	private int id;
	private ArrayList<Integer> nomU = new ArrayList<Integer>();
	private ArrayList<Integer> ids = new ArrayList<Integer>();;
	//
	private Utilisateur uE;

	public UtilisateurTable(Connection connect) {
		super(connect);
	}

	public Utilisateur find(int id) {
		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT * FROM utilisateur " + "WHERE id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				u = new Utilisateur(rs.getInt("id"), rs.getString("email"), rs.getString("nom"), rs.getString("prenom"),
						rs.getInt("droit"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}
	
	public int findId(String nom) {
		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT id FROM utilisateur " + 
		    "WHERE nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, nom);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				id = rs.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}
	
	//Modifs3 find enseignant
	public Utilisateur findEnseignantValider(String nom, String date, String heure_d, String heure_f) {
		uE = new Utilisateur();

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM utilisateur "
				  + "JOIN seance_enseignant se ON se.enseignant_id = utilisateur.id "
				  + "JOIN seance s ON s.id = se.seance_id "
				  + "WHERE utilisateur.nom = ? "
				  + "AND s.date = ? AND s.heure_debut = ? AND s.heure_fin = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, nom);
			ps.setString(2, date);
			ps.setString(3, heure_d);
			ps.setString(4, heure_f);
			ResultSet rs = ps.executeQuery();

			if (rs.first()) {
				uE.setId(rs.getInt("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return uE;
	}
	
	public ArrayList<Integer> findIdsNom(String nomGroupe) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"SELECT * FROM utilisateur " + 
					"JOIN etudiant e ON e.utilisateur_id = utilisateur.id " + 
					"JOIN groupe g ON g.id = e.groupe_id " + 
					"WHERE g.nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, nomGroupe);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				nomU.add(rs.getInt("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nomU;
	}
	
	public ArrayList<Integer> findIds(String nom) {
		
		try {
			PreparedStatement ps = this.connect.prepareStatement("SELECT id FROM utilisateur " + 
		    "WHERE nom = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, nom);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ids.add(rs.getInt("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ids;
	}
	/*SELECT * FROM utilisateur
JOIN etudiant e ON e.utilisateur_id = utilisateur.id
JOIN groupe g ON g.id = e.groupe_id
WHERE g.nom = 'Gr01'*/

	
}
