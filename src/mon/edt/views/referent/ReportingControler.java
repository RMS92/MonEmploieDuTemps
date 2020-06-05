package mon.edt.views.referent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import mon.edt.connection.Login;
import mon.edt.model.Salle;
import mon.edt.table.EdtReferentTable;

public class ReportingControler implements Initializable{

	@FXML
	private BarChart<?, ?> barChart; 
	
	@FXML
	private CategoryAxis y;
	
	@FXML
	private NumberAxis x;
	
	private ArrayList<Salle> salles;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		loadBarChart();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadBarChart() {
		
		EdtReferentTable rT = new EdtReferentTable(Login.getInstance());
		this.salles = new ArrayList<Salle>(); 
		this.salles = rT.finAllSalle();
		
		XYChart.Series set1 = new XYChart.Series<>();
		
		for(int i = 0; i < this.salles.size(); i++) {
			System.out.println(this.salles.get(i).getNom());
			set1.getData().add(new XYChart.Data(this.salles.get(i).getNom(), this.salles.get(i).getCapacite()));
		}
		set1.setName("Capacité d'élèves maximale pour une salle");
		barChart.getData().add(set1);
		
	}

}
