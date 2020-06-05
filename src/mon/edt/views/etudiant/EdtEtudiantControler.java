package mon.edt.views.etudiant;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mon.edt.MainClass;
import mon.edt.connection.Login;
import mon.edt.model.Etudiant;
import mon.edt.model.Groupe;
import mon.edt.model.Utilisateur;
import mon.edt.session.Charg;
import mon.edt.table.EtudiantTable;

public class EdtEtudiantControler implements Initializable {

	private Utilisateur u;
	private Etudiant e;
	private Groupe g;
	private String promo;

	private Node centerPane;
	// Note : automatiquement on a accès au contrôleur avec
	// <idDuFXMLSecondaire>+"Controller";
	@FXML
	private EdtControler edtController;
	@FXML
	private EdtControler edtInverseController;
	@FXML
	private EdtGrilleControler edtGrilleController;
	@FXML
	private RecapControler recapController;
	@FXML
	private ReportingControler reportingController;
	@FXML
	private BorderPane rootPane;
	@FXML
	private MenuItem changeEdt;
	@FXML
	private Label nomLabel;
	@FXML
	private Label closeLabel;
	@FXML
	private Label numeroLabel;
	@FXML
	private Label groupeLabel;
	@FXML
	private Label promoLabel;
	@FXML
	private Label edtLabel;
	@FXML
	private Label edtInverseLabel;
	@FXML
	private Label edtGrilleLabel;
	@FXML
	private Label recapLabel;
	@FXML
	private Label reportingLabel;
	@FXML
	private Button seDeconnecter;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		// Chargement de l'edt par default
		chargerEdt();

		// chargement infos top page
		chargerInfos();

	}

	@FXML
	public void bouttonBarreEdt() {
		chargerEdt();
	}

	@FXML
	public void bouttonBarreRecap() {
		chargerRecap();
	}

	@FXML
	public void handleButtonAction(MouseEvent event) {
		if (event.getSource() == closeLabel) {
			System.exit(0);
		}
		if (event.getSource() == edtLabel) {
			chargerEdt();
		}
		if (event.getSource() == edtInverseLabel) {
			chargerEdtInverse();
		}
		if (event.getSource() == edtGrilleLabel) {
			chargerEdtGrille();
		}
		if (event.getSource() == recapLabel) {
			chargerRecap();
		}
		if (event.getSource() == reportingLabel) {
			chargerReporting();
		}
		
	}
	
	@FXML
	private void seDeconnecter(MouseEvent event) {
		try {

            //add you loading or delays - ;-)
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            
            Scene scene = new Scene(FXMLLoader.load(MainClass.class.getResource("views/home/Connection.fxml")));
            
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
	}

	private void chargerEdt() {

		final FXMLLoader loader = new FXMLLoader(getClass().getResource("EdtEtudiant.fxml"));

		try {
			centerPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		edtController = loader.getController();
		rootPane.setCenter(centerPane);
	}

	private void chargerEdtInverse() {

		final FXMLLoader loader = new FXMLLoader(getClass().getResource("EdtInverseEtudiant.fxml"));

		try {
			centerPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		edtInverseController = loader.getController();
		rootPane.setCenter(centerPane);
	}

	private void chargerEdtGrille() {

		final FXMLLoader loader = new FXMLLoader(getClass().getResource("EdtGrilleEtudiant.fxml"));

		try {
			centerPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		edtGrilleController = loader.getController();
		rootPane.setCenter(centerPane);
	}

	private void chargerRecap() {

		final FXMLLoader loader = new FXMLLoader(getClass().getResource("RecapEtudiant.fxml"));

		try {
			centerPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		recapController = loader.getController();
		rootPane.setCenter(centerPane);
	}
	
	private void chargerReporting() {
		
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportingEtdiant.fxml"));

		try {
			centerPane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		reportingController = loader.getController();
		rootPane.setCenter(centerPane);
	}

	private void chargerInfos() {
		// recuperation id
		Charg charg = new Charg();
		this.u = charg.ChargId();

		// recherche de l'etudiant correspondant
		EtudiantTable uE = new EtudiantTable(Login.getInstance());
		this.e = uE.find(this.u.getId());

		/// GROUPE
		this.g = uE.findGroupe(this.u.getId(), this.e.getGroupe_id());
		this.promo = uE.findPromo(this.u.getId());

		// setterLabel
		nomLabel.setText(this.u.getNom() + " " + this.u.getPrenom());
		numeroLabel.setText(String.valueOf(this.e.getNumero()));
		groupeLabel.setText(this.g.getNom());
		promoLabel.setText(this.promo);
	}

}
