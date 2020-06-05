package mon.edt.views.admin;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class EdtModifsControler implements Initializable {

	private Node centerPane;
	@FXML
	private BorderPane rootPane;
	@FXML
	private EdtModifs1Controler edtModifs1Controller;
	@FXML
	private EdtModifs2Controler edtModifs2Controller;
	@FXML
	private EdtModifs3Controler edtModifs3Controller;
	@FXML
	private EdtModifs4Controler edtModifs4Controller;
	@FXML
	private Label closeLabel;
	@FXML
	private Label modif1;
	@FXML
	private Label modif2;
	@FXML
	private Label modif3;
	@FXML
	private Label modif4;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

	@FXML
	public void handleButtonAction(MouseEvent event) {
		if (event.getSource() == closeLabel) {
			System.exit(0);
		}
		if (event.getSource() == modif1) {
			chargerModif1();
		}
		if (event.getSource() == modif2) {
			chargerModif2();
		}
		if(event.getSource() == modif3) {
			chargerModif3();
		}
		if(event.getSource() == modif4) {
			chargerModif4();
		}
	}

	private void chargerModif1() {

		final FXMLLoader loader = new FXMLLoader(getClass().getResource("EdtModifs1Admin.fxml"));

		try {
			centerPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		edtModifs1Controller = loader.getController();
		rootPane.setCenter(centerPane);
	}

	private void chargerModif2() {

		final FXMLLoader loader = new FXMLLoader(getClass().getResource("EdtModifs2Admin.fxml"));

		try {
			centerPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		edtModifs2Controller = loader.getController();
		rootPane.setCenter(centerPane);
	}
	
	private void chargerModif3() {

		final FXMLLoader loader = new FXMLLoader(getClass().getResource("EdtModifs3Admin.fxml"));

		try {
			centerPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		edtModifs3Controller = loader.getController();
		rootPane.setCenter(centerPane);
	}
	private void chargerModif4() {

		final FXMLLoader loader = new FXMLLoader(getClass().getResource("EdtModifs4Admin.fxml"));

		try {
			centerPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		edtModifs4Controller = loader.getController();
		rootPane.setCenter(centerPane);
	}

}
