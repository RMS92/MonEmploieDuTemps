package mon.edt.views.admin;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.management.loading.PrivateClassLoader;

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
import mon.edt.model.Groupe;
import mon.edt.model.Salle;
import mon.edt.model.Seance;
import mon.edt.model.Utilisateur;
import mon.edt.table.CoursTable;
import mon.edt.table.GroupeTable;
import mon.edt.table.ModifsAdminTable;
import mon.edt.table.SeanceTable;
import mon.edt.table.UtilisateurTable;

public class EdtModifs4Controler implements Initializable{

	@FXML
	TableView<Seance> coursTable;
	@FXML
	TableColumn<Seance, String> idColumn;
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
	TextField semaineField;
	@FXML
	TextField dateField;
	@FXML
	TextField heure_debutField;
	@FXML
	TextField heure_finField;
	@FXML
	TextField salleField;
	// Cours
	@FXML
	private Label idSelection;
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
	//
	private Groupe g;
	private Utilisateur uE;
	private Salle salle;
	//partie modifs
	private String semaine;
	private String date;
	private String heure_d;
	private String heure_f;
	private String salleR;
	
	private ObservableList<Seance> coursList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		remplirTabCours();
	}
	
	@FXML
	public void deplacerSeance() {
		recupInfos();
		//blindage des entréées
		
		if (this.date.equals("")) {
			errors.setText("Veuillez-remplir le champ date !");
		}else if (this.heure_d.equals("")) {
			errors.setText("Veuillez-remplir le champ d'heure de debut !");
		} else if (this.heure_f.equals("")) {
			errors.setText("Veuillez-remplir le champ d'heure de fin !");
		}else if (this.date.equals("12/01/2020") || this.date.equals("19/01/2020")) {
			errors.setText("Le cours ne peut pas etre placé un dimanche !");
		}else if (!this.semaine.equals("1") && !this.semaine.equals("2")) {
			errors.setText("Veuillez-saisir une semaine existante ! ");
		} else if (this.heure_d.equals("7") || this.heure_d.equals("17")) {
			errors.setText("La séance ne contient pas les bonnes horaires !");
		} else if (this.heure_f.equals("8") || this.heure_f.equals("18")) {
			errors.setText("La séance ne contient pas les bonnes horaires !");
		} else if (!this.salleR.equals("P445") && !this.salleR.equals("P315") && !this.salleR.equals("P407")
				&& !this.salleR.equals("SC201") && !this.salleR.equals("P415") && !this.salleR.equals("P446")
				&& !this.salleR.equals("P405(labo)") && !this.salleR.equals("EM009") && !this.salleR.equals("EM010")
				&& !this.salleR.equals("EM009 EM010") && !this.salleR.equals("P445 P446") && !this.salleR.equals("EM009 EM010 SC201")) {
			errors.setText("Veuillez-saisir une salle existante !");
		}else {
			
			//Recherches
			int id = Integer.valueOf(idSelection.getText());
			String groupe, promo = "";
			
			CoursTable cT = new CoursTable(Login.getInstance());
			groupe = cT.findGroupe(id);
			promo = cT.findPromo(groupe);
			//System.out.println(groupe);
			//System.out.println(promo);
			
			GroupeTable gT = new GroupeTable(Login.getInstance());
			this.g = gT.findGroupeValider(groupe, promo, this.date, this.heure_d, this.heure_f);
			
			if(this.g.getId() == 0) {
				
				String enseignant = cT.findProf(id);
				System.out.println(enseignant);
				
				UtilisateurTable uT = new UtilisateurTable(Login.getInstance());
				this.uE = uT.findEnseignantValider(enseignant, this.date, this.heure_d, this.heure_f);
				
				if (this.uE.getId() == 0) {
					
					this.salleR = salleField.getText();
					System.out.println(this.salleR);
					
					this.salle = gT.findSalleValider(this.salleR, this.date, this.heure_d, this.heure_f);
					if (this.salle.getId() == 0) {

						errors.setText("Ok");
						ModifsAdminTable mT = new ModifsAdminTable(Login.getInstance());
						//uptadte cours
						mT.updateSeance(this.semaine, this.date, this.heure_d, this.heure_f, id);
						
						//delete les salles
						mT.deleteSalle(id);
						
						//insert les bonnes salles
						ArrayList<Integer> salleIds = new ArrayList<Integer>();
						salleIds = gT.findSalleId(this.salleR);
						mT.insertBonnesSalle(id, salleIds.get(0));
						
						errors.setText("La séance a bien été déplacée le " + this.date + " de " + this.heure_d + " à " + this.heure_f);
						
					}else {
						errors.setText("La salle " + this.salleR + " est déjà occupée le " + this.date + " de "
								+ this.heure_d + "h à " + this.heure_f + "h !");
					}
					
					
				}else {
					errors.setText("L'enseignant " + enseignant + " a déjà cours le " + this.date + " de "
							+ this.heure_d + "h à " + this.heure_f + "h !");
				}
				
			}else {
				errors.setText("Le groupe " + groupe + " a déjà cours !");
			}
		}	
		
	}

	private void remplirTabCours() {

		idColumn.setCellValueFactory(new PropertyValueFactory<Seance, String>("id"));
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
			idSelection.setText(String.valueOf(s.getId()));
			coursDateSelection.setText(s.getDate());
			coursMatiereSelection.setText(s.getNomRecap());
			coursHeureDebutSelection.setText(s.getHeure_debut());
			coursHeureFinSelection.setText(s.getHeure_fin());
			coursEtatSelection.setText(String.valueOf(s.getEtat()));
		} else {
			idSelection.setText("");
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
	
	private void recupInfos() {
		this.semaine = semaineField.getText();
		this.date = dateField.getText();
		this.heure_d = heure_debutField.getText();
		this.heure_f = heure_finField.getText();
		this.salleR = salleField.getText();
	}

}
