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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import mon.edt.connection.Login;
import mon.edt.model.Seance;
import mon.edt.table.ModifsAdminTable;
import mon.edt.table.SeanceTable;

public class EdtModifs2Controler implements Initializable {

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
	@FXML
	TableColumn<Seance, Integer> etatColumn;
	//
	@FXML
	TextField etatField;
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
	private Label coursEtatSelection;
	@FXML
	private Label errors;

	// cours
	private ArrayList<Integer> idsSeance;
	private Seance s;

	private ObservableList<Seance> coursList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		remplirTabCours();
	}

	@FXML
	public void changeEtat() {

		String recupEtat = etatField.getText();
		int etatRecup = Integer.valueOf(recupEtat);
		// blindage
		// recup
		String dateRecup = coursDateSelection.getText();
		// String matiereRecup = coursMatiereSelection.getText();
		String heure_debutRecup = coursHeureDebutSelection.getText();
		String heure_finRecup = coursHeureFinSelection.getText();

		if (etatRecup == 0 || etatRecup == 1 || etatRecup == 2) {
			errors.setText("");
			ModifsAdminTable mT = new ModifsAdminTable(Login.getInstance());
			mT.updateEtat(dateRecup, heure_debutRecup, heure_finRecup, etatRecup);
		}else {
			errors.setText("Valeur incorrect !");
		}

	}

	private void remplirTabCours() {

		dateColumn.setCellValueFactory(new PropertyValueFactory<Seance, String>("date"));
		matiereColumn.setCellValueFactory(new PropertyValueFactory<Seance, String>("nomRecap"));
		dateDebutColumn.setCellValueFactory(new PropertyValueFactory<Seance, String>("heure_debut"));
		dateFinColumn.setCellValueFactory(new PropertyValueFactory<Seance, String>("heure_fin"));
		etatColumn.setCellValueFactory(new PropertyValueFactory<Seance, Integer>("etat"));

		coursList = this.getDataCours();
		coursTable.setItems(coursList);

		// SHOW VALEURE LABEL QUAND CLIQUE
		coursTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showCoursDetails(newValue));
	}

	// SHOW VALEUR LABEL QUAND CLIQUE SUR UNE CASE
	private void showCoursDetails(Seance s) {
		if (s != null) {
			coursDateSelection.setText(s.getDate());
			coursMatiereSelection.setText(s.getNomRecap());
			coursHeureDebutSelection.setText(s.getHeure_debut());
			coursHeureFinSelection.setText(s.getHeure_fin());
			coursEtatSelection.setText(String.valueOf(s.getEtat()));
		} else {
			coursDateSelection.setText("");
			coursMatiereSelection.setText("");
			coursHeureDebutSelection.setText("");
			coursHeureFinSelection.setText("");
			coursEtatSelection.setText("");
		}
	}

	private ObservableList<Seance> getDataCours() {

		SeanceTable sT = new SeanceTable(Login.getInstance());
		this.idsSeance = new ArrayList<Integer>();

		this.idsSeance = sT.findAllIds();
		for (int i = 0; i < this.idsSeance.size(); i++) {

			this.s = new Seance();
			this.s = sT.findSeance(this.idsSeance.get(i));
			this.coursList.add(this.s);
			// System.out.println(this.s.getEtat());
		}

		return this.coursList;
	}

}
