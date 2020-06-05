package mon.edt.views.prof;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import mon.edt.connection.Login;
import mon.edt.model.Utilisateur;
import mon.edt.session.Charg;
import mon.edt.table.EnseignantTable;
import mon.edt.table.EtudiantTable;

public class ReportingControler implements Initializable {

	@FXML
	private Pane pieChart;
	
	private Utilisateur u;
	//
	private ArrayList<Integer> groupeIds;
	private ArrayList<String> nomsGrp;
	private ArrayList<Integer> nbCours;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		findNbCours();
		loadPieChart();
	}

	private void loadPieChart() {
		pieChart.getChildren().clear();
		ObservableList<PieChart.Data> listPieChart = FXCollections.observableArrayList();

		for (int i = 0; i < this.nbCours.size(); i++) {
			listPieChart.add(new PieChart.Data(
					this.nomsGrp.get(i) + ": " + String.valueOf(this.nbCours.get(i)) + "h", this.nbCours.get(i)));
		}

		PieChart cours = new PieChart(listPieChart);
		cours.setTitle("Proportion de cours de vos groupes sur l'ensemble de l'année");
		pieChart.getChildren().add(cours);
	}
	
	private void findNbCours() {

		// Charge id utilisateur
		Charg charg = new Charg();
		this.u = charg.ChargId();

		EnseignantTable eT = new EnseignantTable(Login.getInstance());

		// find cours id d'un utilisateur
		this.groupeIds = new ArrayList<Integer>();
		this.groupeIds = eT.findGrpIdEnseignant(this.u.getId());

		this.nbCours = new ArrayList<Integer>();
		this.nomsGrp = new ArrayList<String>();

		for (int i = 0; i < this.groupeIds.size(); i++) {
			this.nbCours.add(eT.findNbCoursEnseignant(this.u.getId(), this.groupeIds.get(i)));
			// System.out.println(eT.findNbCoursReporting(this.coursIds.get(i),
			// this.u.getId()));

			this.nomsGrp.add(eT.findNomGrp(this.groupeIds.get(i)));
			// System.out.println(eT.findCoursNomEtudiant(this.coursIds.get(i)));

		}
	}
}
