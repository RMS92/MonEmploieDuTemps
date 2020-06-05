package mon.edt.views.etudiant;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import mon.edt.connection.Login;
import mon.edt.model.Utilisateur;
import mon.edt.session.Charg;
import mon.edt.table.EtudiantTable;

public class ReportingControler implements Initializable {

	@FXML
	private Pane pieChart;
	@FXML
	private Pane pieChart2;
	//
	private Utilisateur u;
	private ArrayList<Integer> coursIds;
	private ArrayList<String> coursNoms;
	private ArrayList<Integer> nbCours;
	//
	private ArrayList<Integer> enseignantIds;
	private ArrayList<String> enseignantNoms;
	private ArrayList<Integer> nbCours2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		findNbCours();
		loadPieChart();

		findNbEnseignant();
		loadPieChart2();
		
	}

	private void loadPieChart() {
		pieChart.getChildren().clear();
		ObservableList<PieChart.Data> listPieChart = FXCollections.observableArrayList();

		for (int i = 0; i < this.nbCours.size(); i++) {
			listPieChart.add(new PieChart.Data(
					this.coursNoms.get(i) + ": " + String.valueOf(this.nbCours.get(i)) + "h", this.nbCours.get(i)));
		}

		PieChart cours = new PieChart(listPieChart);
		cours.setTitle("Proportion de vos cours sur l'ensemble de l'année");
		pieChart.getChildren().add(cours);
	}

	private void loadPieChart2() {
		pieChart2.getChildren().clear();
		ObservableList<PieChart.Data> listPieChart2 = FXCollections.observableArrayList();

		for (int i = 0; i < this.nbCours2.size(); i++) {
			listPieChart2.add(new PieChart.Data(
					this.enseignantNoms.get(i) + ": " + String.valueOf(this.nbCours2.get(i)) + "h", this.nbCours2.get(i)));
		}

		PieChart cours2 = new PieChart(listPieChart2);
		cours2.setTitle("Proportion de cours de vos enseignant sur l'ensemble de l'année");
		pieChart2.getChildren().add(cours2);
	}

	private void findNbCours() {

		// Charge id utilisateur
		Charg charg = new Charg();
		this.u = charg.ChargId();

		EtudiantTable eT = new EtudiantTable(Login.getInstance());

		// find cours id d'un utilisateur
		this.coursIds = new ArrayList<Integer>();
		this.coursIds = eT.findCoursIdEtudiant(this.u.getId());

		this.nbCours = new ArrayList<Integer>();
		this.coursNoms = new ArrayList<String>();

		for (int i = 0; i < this.coursIds.size(); i++) {
			this.nbCours.add(eT.findNbCoursReporting(this.coursIds.get(i), this.u.getId()));
			// System.out.println(eT.findNbCoursReporting(this.coursIds.get(i),
			// this.u.getId()));

			this.coursNoms.add(eT.findCoursNomEtudiant(this.coursIds.get(i)));
			// System.out.println(eT.findCoursNomEtudiant(this.coursIds.get(i)));

		}
	}

	// piechart2

	private void findNbEnseignant() {

		// Charge id utilisateur
		Charg charg = new Charg();
		this.u = charg.ChargId();

		EtudiantTable eT = new EtudiantTable(Login.getInstance());

		// find enseignants id d'un utilisateur
		this.enseignantIds = new ArrayList<Integer>();
		this.enseignantIds = eT.findEnseeignantIdEtudiant(this.u.getId());

		this.nbCours2 = new ArrayList<Integer>();
		this.enseignantNoms = new ArrayList<String>();

		for (int i = 0; i < this.enseignantIds.size(); i++) {
			this.enseignantNoms.add(eT.findNomEnseignant(this.enseignantIds.get(i)));
			//System.out.println(eT.findNomEnseignant(this.enseignantIds.get(i)));

			this.nbCours2.add(eT.findNbCoursEnseignant(this.enseignantIds.get(i), this.u.getId()));
			//System.out.println(eT.findNbCoursEnseignant(this.enseignantIds.get(i), this.u.getId()));

		}
	}

}
