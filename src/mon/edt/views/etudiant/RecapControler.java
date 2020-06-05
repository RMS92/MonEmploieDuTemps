package mon.edt.views.etudiant;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mon.edt.connection.Login;
import mon.edt.model.Cours;
import mon.edt.model.Seance;
import mon.edt.model.Utilisateur;
import mon.edt.session.Charg;
import mon.edt.table.CoursTable;
import mon.edt.table.RecapEtudiantTable;
import mon.edt.table.RecapShowEtudiantTable;
import mon.edt.utils.RecapEtudiant;
import mon.edt.utils.RecapShowEtudiant;

public class RecapControler implements Initializable {
	// RECAP MATIERE
	@FXML
	TableView<RecapEtudiant> recapTable;
	@FXML
	TableColumn<RecapEtudiant, String> matieres;
	@FXML
	TableColumn<RecapEtudiant, String> premiereSeance;
	@FXML
	TableColumn<RecapEtudiant, String> derniereSeance;
	@FXML
	TableColumn<RecapEtudiant, String> dureeSeance;
	@FXML
	TableColumn<RecapEtudiant, Integer> nombreSeance;
	@FXML
	TextField searchMatiere;

	// RECAP COURS
	@FXML
	TableView<RecapShowEtudiant> recapCoursTable;
	@FXML
	TableColumn<RecapShowEtudiant, String> date;
	@FXML
	TableColumn<RecapShowEtudiant, String> heure;
	@FXML
	TableColumn<RecapShowEtudiant, String> professeur;
	@FXML
	TableColumn<RecapShowEtudiant, String> groupes;
	@FXML
	TableColumn<RecapShowEtudiant, String> salle;
	@FXML
	TableColumn<RecapShowEtudiant, String> site;
	@FXML
	TextField searchInfos;

	// Labels
	@FXML
	Label totalHeures;
	@FXML
	Label coursSelection;

	// LIST ITEMS OBSERVABLES
	private ObservableList<RecapEtudiant> list = FXCollections.observableArrayList();
	private ObservableList<RecapShowEtudiant> listSeance = FXCollections.observableArrayList();

	// POUR MATIERES
	private Utilisateur u;
	private Cours c;
	private ArrayList<Seance> e;
	private String premierCours;
	private String dernierCours;
	private int nbrCours;
	private String duree;
	private int totalH = 0;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		// INITIALISATION TAB
		matieres.setCellValueFactory(new PropertyValueFactory<RecapEtudiant, String>("matiere"));
		premiereSeance.setCellValueFactory(new PropertyValueFactory<RecapEtudiant, String>("premiereSeance"));
		derniereSeance.setCellValueFactory(new PropertyValueFactory<RecapEtudiant, String>("derniereSeance"));
		dureeSeance.setCellValueFactory(new PropertyValueFactory<RecapEtudiant, String>("duree"));
		nombreSeance.setCellValueFactory(new PropertyValueFactory<RecapEtudiant, Integer>("nbSeance"));

		list = this.getDataRecap();
		recapTable.setItems(list);

		// MODIF VALEURE LABEL QUAND CLIQUE
		recapTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showCoursDetails(newValue));

		// FCT DE RECHERCHES
		searchMatiere();
	}

	@FXML
	public void handleButtonAction(MouseEvent event) {
		// if(event.getSource() == closeLabel) {
		// System.exit(0);
		// }
	}

	// BOUTON SHOW MORE
	@FXML
	public void showMore() {

		this.listSeance.removeAll(this.listSeance);
		date.setCellValueFactory(new PropertyValueFactory<RecapShowEtudiant, String>("date"));
		heure.setCellValueFactory(new PropertyValueFactory<RecapShowEtudiant, String>("heure"));
		professeur.setCellValueFactory(new PropertyValueFactory<RecapShowEtudiant, String>("professeur"));
		groupes.setCellValueFactory(new PropertyValueFactory<RecapShowEtudiant, String>("groupes"));
		salle.setCellValueFactory(new PropertyValueFactory<RecapShowEtudiant, String>("salle"));
		site.setCellValueFactory(new PropertyValueFactory<RecapShowEtudiant, String>("site"));

		listSeance = this.getDataSeance();
		recapCoursTable.setItems(listSeance);
		recapCoursTable.setVisible(true);
		searchInfos();
	}

	// BOUTON SHOW LESS
	@FXML
	public void showLess() {
		recapCoursTable.setVisible(false);
		this.listSeance.removeAll(this.listSeance);
	}

	// MODIF VALEUR LABEL QUAND CLIQUE SUR UNE CASE
	private void showCoursDetails(RecapEtudiant r) {
		if (r != null) {
			coursSelection.setText(r.getMatiere());
		} else {
			coursSelection.setText("");
		}
	}

	// SEARCH TABLE
	private void searchMatiere() {
		FilteredList<RecapEtudiant> filteredMatiere = new FilteredList<>(this.list, b -> true);
		searchMatiere.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredMatiere.setPredicate(recapEtudiant -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				// SI TROUVE UNE MATIERE
				if (recapEtudiant.getMatiere().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else {
					return false;
				}
			});
		});

		SortedList<RecapEtudiant> sortedMatiere = new SortedList<>(filteredMatiere);
		sortedMatiere.comparatorProperty().bind(recapTable.comparatorProperty());
		recapTable.setItems(sortedMatiere);
	}

	private void searchInfos() {

		FilteredList<RecapShowEtudiant> filteredInfos = new FilteredList<>(this.listSeance, b -> true);
		searchInfos.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredInfos.setPredicate(recapShowEtudiant -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				// SI TROUVE UN PROF
				if (recapShowEtudiant.getProfesseur().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (recapShowEtudiant.getGroupes().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else {
					return false;
				}
			});
		});

		SortedList<RecapShowEtudiant> sortedInfos = new SortedList<>(filteredInfos);
		sortedInfos.comparatorProperty().bind(recapCoursTable.comparatorProperty());
		recapCoursTable.setItems(sortedInfos);
	}

	// REMPLISSAGE DES TABLEAUX
	private ObservableList<RecapEtudiant> getDataRecap() {

		Charg charg = new Charg();
		this.u = charg.ChargId();

		CoursTable uT = new CoursTable(Login.getInstance());
		RecapEtudiantTable rT = new RecapEtudiantTable(Login.getInstance());

		// arraylist de seances distinctes/uniques
		this.e = rT.findAllDistinct(this.u.getId());

		for (int i = 0; i < this.e.size(); i++) {

			// cherche un cours parmi les seances
			this.c = uT.find(this.e.get(i).getCours_idRecap());

			// fction date premier
			this.premierCours = rT.findPremierSeance(this.c.getNom());
			// fction date dernier
			this.dernierCours = rT.findDernierSeance(this.c.getNom());
			// fction nbCours
			this.nbrCours = rT.findNbHeure(this.u.getId(), this.c.getId());
			this.totalH = this.totalH + this.nbrCours;

			this.duree = String.valueOf(this.nbrCours) + " h";

			this.list.add(new RecapEtudiant(this.c.getNom(), this.premierCours, this.dernierCours, this.duree,
					this.nbrCours));
		}

		totalHeures.setText(String.valueOf(this.totalH));
		return this.list;

	}

	private ObservableList<RecapShowEtudiant> getDataSeance() {
		// VARIABLES
		ArrayList<Seance> enew;
		Utilisateur unew;

		ArrayList<Integer> id = new ArrayList<Integer>();
		String heure;
		String nom = "";
		String groupes;
		String salle;
		String site;

		// Charge id utilisateur
		Charg charg = new Charg();
		unew = charg.ChargId();

		// recherche des seances
		RecapShowEtudiantTable sT = new RecapShowEtudiantTable(Login.getInstance());
		enew = sT.findAll(unew.getId(), coursSelection.getText());

		for (int i = 0; i < enew.size(); i++) {

			// find les id de l'nseignant
			id = sT.findEnseignantId(enew.get(i).getId());
			for (int j = 0; j < id.size(); j++) {

				if (id.size() > 1) {
					nom += sT.findEnseignantNom(id.get(j), enew.get(i).getType_id(), coursSelection.getText()) + " ";
				} else {
					nom = sT.findEnseignantNom(id.get(j), enew.get(i).getType_id(), coursSelection.getText());
				}
			}
			// find Groupes
			groupes = sT.findGroupes(enew.get(i).getId());
			// find heure debut et fin
			heure = enew.get(i).getHeure_debut() + " à " + enew.get(i).getHeure_fin();
			// find salle
			salle = sT.findSalle(enew.get(i).getId());
			// find site
			String mots[] = salle.split(" ");
			site = sT.findSite(mots[0]);

			this.listSeance.add(new RecapShowEtudiant(enew.get(i).getDate(), heure, nom, groupes, salle, site));

		}
		return this.listSeance;
	}
}
