package mon.edt.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mon.edt.model.Seance;

public class ModifsAdminTable extends Table<Object> {

	public ModifsAdminTable(Connection connect) {
		super(connect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	// Modifs 1 : affecte un enseignant a un cours
	public void updateCours(int seance_id, int utilisateur_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"INSERT INTO seance_enseignant SET seance_id = ?, enseignant_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, seance_id);
			ps.setInt(2, utilisateur_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteCours(int seance_id, int utilisateur_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"DELETE FROM seance_enseignant WHERE seance_id = ? AND enseignant_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, seance_id);
			ps.setInt(2, utilisateur_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Modifs2 : update etat

	public void updateEtat(String date, String heureDebut, String heureFin, int etat) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"UPDATE seance SET etat = ? WHERE date = ? AND heure_debut = ? AND heure_fin = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, etat);
			ps.setString(2, date);
			ps.setString(3, heureDebut);
			ps.setString(4, heureFin);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Modifs3 insert into

	// seance
	public void insertSeance(int semaine, String date, int heure_d, String heure_f, int etat, int cours_id,
			int type_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"INSERT INTO seance SET semaine = ?, date = ?, heure_debut = ?, heure_fin = ?, etat = ?, cours_id = ?, type_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, semaine);
			ps.setString(2, date);
			ps.setInt(3, heure_d);
			ps.setString(4, heure_f);
			ps.setInt(5, etat);
			ps.setInt(6, cours_id);
			ps.setInt(7, type_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// seance enseignant

	public void insertSeanceEnseignant(int seance_id, int enseignant_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"INSERT INTO seance_enseignant SET seance_id = ?, enseignant_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, seance_id);
			ps.setInt(2, enseignant_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// seance etudiant
	
	public void insertSeanceEtudiant(int seance_id, int etudiant_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"INSERT INTO seance_etudiant SET seance_id = ?, utilisateur_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, seance_id);
			ps.setInt(2, etudiant_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	//seance groupe
	
	public void insertSeanceGroupe(int seance_id, int groupe_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"INSERT INTO seance_groupe SET seance_id = ?, groupe_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, seance_id);
			ps.setInt(2, groupe_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// seance salle
	
	public void insertSeanceSalle(int seance_id, int salle_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"INSERT INTO seance_salle SET seance_id = ?, salle_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, seance_id);
			ps.setInt(2, salle_id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	//Modifs4
	public void updateSeance(String semaine, String date, String heureDebut, String heureFin, int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"UPDATE seance SET semaine = ?, date = ?, heure_debut = ?, heure_fin = ? " +
			         "WHERE seance.id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, semaine);
			ps.setString(2, date);
			ps.setString(3, heureDebut);
			ps.setString(4, heureFin);
			ps.setInt(5, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void deleteSalle(int id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"DELETE FROM seance_salle WHERE seance_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, id);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void insertBonnesSalle(int seance_id, int salle_id) {

		try {
			PreparedStatement ps = this.connect.prepareStatement(
					"INSERT INTO seance_salle SET seance_id = ?, salle_id = ?",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setInt(1, seance_id);
			ps.setInt(2, salle_id);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	
}
