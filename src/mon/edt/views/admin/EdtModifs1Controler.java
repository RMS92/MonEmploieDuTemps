package mon.edt.views.admin;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mon.edt.connection.Login;
import mon.edt.model.Seance;
import mon.edt.model.Utilisateur;
import mon.edt.table.EnseignantTable;
import mon.edt.table.ModifsAdminTable;
import mon.edt.table.SeanceTable;
import mon.edt.table.UtilisateurTable;
import mon.edt.utils.EdtColonne;
import mon.edt.utils.RecapEtudiant;

public class EdtModifs1Controler implements Initializable {

	@FXML
	TableView<Utilisateur> enseignantTable;
	@FXML
	TableColumn<Utilisateur, String> nomColumn;
	@FXML
	TableColumn<Utilisateur, String> prenomColumn;
	@FXML
	TableView<Seance> coursTable;
	@FXML
	TableColumn<Seance, String> dateColumn;
	@FXML
	TableColumn<Seance, String> matiereColumn;
	@FXML
	TableColumn<Seance, String> dateDebutColumn;
	@FXML
	TableColumn<Seance, String> dateFinColumn;
	// Enseignant
	@FXML
	private Label enseignantNomSelection;
	@FXML
	private Label enseignantPrenomSelection;
	// Cours
	@FXML
	private Label coursDateSelection;
	@FXML
	private Label coursMatiereSelection;
	@FXML
	private Label coursHeureDebutSelection;
	@FXML
	private Label coursHeureFinSelection;

	@FXML
	private Label errors;

	private ObservableList<Utilisateur> enseignantList = FXCollections.observableArrayList();
	private ObservableList<Seance> coursList = FXCollections.observableArrayList();

	// enseignant
	private ArrayList<Integer> ids;
	private Utilisateur u;
	// cours
	private ArrayList<Integer> idsSeance;
	private Seance s;
	// Valider
	private Seance seanceE;
	private Seance sValider;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		remplirTabEnseignant();
		remplirTabCours();
	}

	@FXML
	public void valider() {
		// recup nom enseignant
		String nomE = enseignantNomSelection.getText();
		// recup comparaison
		String dateRecup = coursDateSelection.getText();
		String matiereRecup = coursMatiereSelection.getText();
		String heure_debutRecup = coursHeureDebutSelection.getText();
		String heure_finRecup = coursHeureFinSelection.getText();

		if (dateRecup.equals("") || nomE.equals("")) {
			// erreur blindage ici
			errors.setText("Veuillez - sélectionner un champ !");
		}

		SeanceTable sT = new SeanceTable(Login.getInstance());
		this.seanceE = new Seance();
		this.sValider = new Seance();

		// find la seance du prof selectionné
		this.seanceE = sT.findSeanceEnseignant(nomE, dateRecup, heure_debutRecup, heure_finRecup);

		// find la seance selectionné
		this.sValider = sT.findSeanceValider(dateRecup, matiereRecup, heure_debutRecup, heure_finRecup);
		System.out.println(this.seanceE.getId() + " " + this.sValider.getId());

		if (this.seanceE.getId() == this.sValider.getId()) {

			errors.setText("L'enseignant " + nomE + " a déjà cours le " + dateRecup + " de " + heure_debutRecup + " à "
					+ heure_finRecup);
		} else {

			ArrayList<Seance> comp = new ArrayList<Seance>();
			comp = sT.findCours(nomE);
			/*for (int i = 0; i < comp.size(); i++) {
				System.out.println(comp.get(i).getNomRecap());
			}*/

			for (int i = 0; i < comp.size(); i++) {
				
				if (comp.get(i).getNomRecap().equals(matiereRecup)) {
					// find id enseignant
					UtilisateurTable uT = new UtilisateurTable(Login.getInstance());
					int utilisateur_id = uT.findId(nomE);
					// System.out.println(utilisateur_id);

					// Update
					ModifsAdminTable mT = new ModifsAdminTable(Login.getInstance());
					mT.updateCours(this.sValider.getId(), utilisateur_id);

					errors.setText("L'enseignant " + nomE + " a bien été affecté au cours du " + dateRecup + " de "
							+ heure_debutRecup + " à " + heure_finRecup);

				} /*else {
					errors.setText("L'enseignant " + nomE + " ne peut pas donner de cours de " + matiereRecup);
				}*/
				
			}

		}

	}

	@FXML
	public void supprimer() {
		// recup nom enseignant
		String nomE = enseignantNomSelection.getText();
		// recup comparaison
		String dateRecup = coursDateSelection.getText();
		String matiereRecup = coursMatiereSelection.getText();
		String heure_debutRecup = coursHeureDebutSelection.getText();
		String heure_finRecup = coursHeureFinSelection.getText();

		if (dateRecup.equals("") || nomE.equals("")) {
			// erreur blindage ici
			errors.setText("Veuillez - sélectionner un champ !");
		}

		SeanceTable sT = new SeanceTable(Login.getInstance());
		this.seanceE = new Seance();
		this.sValider = new Seance();

		// find la seance du prof selectionné
		this.seanceE = sT.findSeanceEnseignant(nomE, dateRecup, heure_debutRecup, heure_finRecup);

		// find la seance selectionné
		this.sValider = sT.findSeanceValider(dateRecup, matiereRecup, heure_debutRecup, heure_finRecup);
		System.out.println(this.seanceE.getId() + " " + this.sValider.getId());

		if (this.seanceE.getId() == this.sValider.getId()) {

			// System.out.println(comp.getNomRecap());

			// find id enseignant
			UtilisateurTable uT = new UtilisateurTable(Login.getInstance());
			int utilisateur_id = uT.findId(nomE);
			// System.out.println(utilisateur_id);

			// Delete
			ModifsAdminTable mT = new ModifsAdminTable(Login.getInstance());
			mT.deleteCours(this.sValider.getId(), utilisateur_id);

			errors.setText("L'enseignant " + nomE + " a été désafecter du cours de " + dateRecup + " de "
					+ heure_debutRecup + " à " + heure_finRecup);
		} else {

			ArrayList<Seance> comp = new ArrayList<Seance>();
			comp = sT.findCours(nomE);
			// System.out.println(comp.getNomRecap());

            for (int i = 0; i < comp.size(); i++) {
				
				if (comp.get(i).getNomRecap().equals(matiereRecup)) {

				errors.setText("L'enseignant " + nomE + " n' a pas cours de " + dateRecup + " de " + heure_debutRecup
						+ " à " + heure_finRecup);
				}
				/*else {
					errors.setText("L'enseignant " + nomE + " ne donne pas de cours de " + matiereRecup);
				}*/
			} 

		}

	}

	private void remplirTabEnseignant() {

		nomColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
		prenomColumn.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));

		enseignantList = this.getDataEnseignant();
		enseignantTable.setItems(enseignantList);

		// SHOW VALEURE LABEL QUAND CLIQUE
		enseignantTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showEnseignantDetails(newValue));

	}

	private void remplirTabCours() {

		dateColumn.setCellValueFactory(new PropertyValueFactory<Seance, String>("date"));
		matiereColumn.setCellValueFactory(new PropertyValueFactory<Seance, String>("nomRecap"));
		dateDebutColumn.setCellValueFactory(new PropertyValueFactory<Seance, String>("heure_debut"));
		dateFinColumn.setCellValueFactory(new PropertyValueFactory<Seance, String>("heure_fin"));

		coursList = this.getDataCours();
		coursTable.setItems(coursList);

		// SHOW VALEURE LABEL QUAND CLIQUE
		coursTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showCoursDetails(newValue));
	}

	// SHOW VALEUR LABEL QUAND CLIQUE SUR UNE CASE
	private void showEnseignantDetails(Utilisateur u) {
		if (u != null) {
			enseignantNomSelection.setText(u.getNom());
			enseignantPrenomSelection.setText(u.getPrenom());
		} else {
			enseignantNomSelection.setText("");
			enseignantPrenomSelection.setText("");
		}
	}

	// SHOW VALEUR LABEL QUAND CLIQUE SUR UNE CASE
	private void showCoursDetails(Seance s) {
		if (s != null) {
			coursDateSelection.setText(s.getDate());
			coursMatiereSelection.setText(s.getNomRecap());
			coursHeureDebutSelection.setText(s.getHeure_debut());
			coursHeureFinSelection.setText(s.getHeure_fin());
		} else {
			coursDateSelection.setText("");
			coursMatiereSelection.setText("");
			coursHeureDebutSelection.setText("");
			coursHeureFinSelection.setText("");
		}
	}

	private ObservableList<Utilisateur> getDataEnseignant() {

		EnseignantTable eT = new EnseignantTable(Login.getInstance());
		// Init
		this.ids = new ArrayList<Integer>();
		// find tous les enseignants
		this.ids = eT.findAllIds();

		for (int i = 0; i < this.ids.size(); i++) {

			this.u = new Utilisateur();
			this.u = eT.findEnseignant(ids.get(i));
			this.enseignantList.add(this.u);
		}

		return this.enseignantList;
	}

	private ObservableList<Seance> getDataCours() {

		SeanceTable sT = new SeanceTable(Login.getInstance());
		this.idsSeance = new ArrayList<Integer>();

		this.idsSeance = sT.findAllIds();
		for (int i = 0; i < this.idsSeance.size(); i++) {

			this.s = new Seance();
			this.s = sT.findSeance(this.idsSeance.get(i));
			this.coursList.add(this.s);
		}

		return this.coursList;
	}

}
