package mon.edt.views.prof;

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
import javafx.scene.input.MouseEvent;
import mon.edt.connection.Login;
import mon.edt.model.Seance;
import mon.edt.model.Utilisateur;
import mon.edt.session.Charg;
import mon.edt.table.EdtEnseignantTable;
import mon.edt.table.EdtEtudiantTable;
import mon.edt.utils.EdtColonne;

public class EdtGrilleControler implements Initializable{
	
	@FXML
	TableView<EdtColonne> lundiTable;	
	@FXML
	TableColumn<EdtColonne, String> lundiColumn;
	@FXML
	TableView<EdtColonne> mardiTable;	
	@FXML
	TableColumn<EdtColonne, String> mardiColumn;	
	@FXML
	TableView<EdtColonne> mercrediTable;	
	@FXML
	TableColumn<EdtColonne, String> mercrediColumn;	
	@FXML
	TableView<EdtColonne> jeudiTable;	
	@FXML
	TableColumn<EdtColonne, String> jeudiColumn;	
	@FXML
	TableView<EdtColonne> vendrediTable;	
	@FXML
	TableColumn<EdtColonne, String> vendrediColumn;	
	@FXML
	TableView<EdtColonne> samediTable;	
	@FXML
	TableColumn<EdtColonne, String> samediColumn;	
	//DATES
	@FXML
	Label dateLundi;
	@FXML
	Label dateMardi;
	@FXML
	Label dateMercredi;
	@FXML
	Label dateJeudi;
	@FXML
	Label dateVendredi;
	@FXML
	Label dateSamedi;
	//SEMAINES
	@FXML
	Label semaine1;
	@FXML
	Label semaine2;
	
	private ObservableList<EdtColonne> listLundi = FXCollections.observableArrayList();
	private ObservableList<EdtColonne> listMardi = FXCollections.observableArrayList();
	private ObservableList<EdtColonne> listMercredi = FXCollections.observableArrayList();
	private ObservableList<EdtColonne> listJeudi = FXCollections.observableArrayList();
	private ObservableList<EdtColonne> listVendredi = FXCollections.observableArrayList();
	private ObservableList<EdtColonne> listSamedi = FXCollections.observableArrayList();
	
	private Utilisateur u;
	private ArrayList<Seance> e;
	private ArrayList<String> date;
	private ArrayList<Integer> id;
	private ArrayList<String> semaineDate;
	
	private String semaine;
	private String heure_debut;
	private String heure_fin;
	private String matiere;
	private String nomEnseignant;
	private String nomGroupe;
	private String nomPromo;
	private String salle;
	private String type;
	private int etat;
	private String etatfinal;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		initTable(semaine1);
		
		//INITIALISATION TABLEAUX
	    remplirTab(semaine1.getText());
	}
	
	@FXML
	public void chargeSemaine(MouseEvent event) {
		if(event.getSource() == semaine1) {
			initTable(semaine1);
			
			this.listLundi.removeAll(this.listLundi);
			this.listMardi.removeAll(this.listMardi);
			this.listMercredi.removeAll(this.listMercredi);
			this.listJeudi.removeAll(this.listJeudi);
			this.listVendredi.removeAll(this.listVendredi);
			this.listSamedi.removeAll(this.listSamedi);
			
			remplirTab(semaine1.getText());
			
		}
		if(event.getSource() == semaine2) {
			initTable(semaine2);
			
			this.listLundi.removeAll(this.listLundi);
			this.listMardi.removeAll(this.listMardi);
			this.listMercredi.removeAll(this.listMercredi);
			this.listJeudi.removeAll(this.listJeudi);
			this.listVendredi.removeAll(this.listVendredi);
			this.listSamedi.removeAll(this.listSamedi);
			
			remplirTab(semaine2.getText());
		}
	}
	
	private void initTable(Label label) {
		Charg charg = new Charg();
		this.u = charg.ChargId();
		
		//recherche des seances 
		semaine = label.getText();
		int recupSemaine = Integer.parseInt(semaine);
		EdtEnseignantTable eT = new EdtEnseignantTable(Login.getInstance());
		this.e = eT.findAll(this.u.getId(), recupSemaine);
		date = this.getDates(recupSemaine);
		dateLundi.setText(date.get(0));
		dateMardi.setText(date.get(1));
		dateMercredi.setText(date.get(2));
		dateJeudi.setText(date.get(3));
		dateVendredi.setText(date.get(4));
		dateSamedi.setText(date.get(5));
	}
	
	private void remplirTab(String semaine) {
		int semaineRecup = Integer.valueOf(semaine);
				
		lundiColumn.setCellValueFactory(new PropertyValueFactory<EdtColonne, String>("infos"));
	    listLundi = this.getDataColonneLundi(semaineRecup);
		lundiTable.setItems(listLundi);
		
		mardiColumn.setCellValueFactory(new PropertyValueFactory<EdtColonne, String>("infos"));
	    listMardi = this.getDataColonneMardi(semaineRecup);
		mardiTable.setItems(listMardi);
		
		mercrediColumn.setCellValueFactory(new PropertyValueFactory<EdtColonne, String>("infos"));
	    listMercredi = this.getDataColonneMercredi(semaineRecup);
		mercrediTable.setItems(listMercredi);
		
		jeudiColumn.setCellValueFactory(new PropertyValueFactory<EdtColonne, String>("infos"));
	    listJeudi = this.getDataColonneJeudi(semaineRecup);
		jeudiTable.setItems(listJeudi);
		
		vendrediColumn.setCellValueFactory(new PropertyValueFactory<EdtColonne, String>("infos"));
	    listVendredi = this.getDataColonneVendredi(semaineRecup);
		vendrediTable.setItems(listVendredi);
		
		samediColumn.setCellValueFactory(new PropertyValueFactory<EdtColonne, String>("infos"));
	    listSamedi = this.getDataColonneSamedi(semaineRecup);
		samediTable.setItems(listSamedi);
	}
	
	private ArrayList<String> getDates(int semaine) {
		this.semaineDate = new ArrayList<String>();
		if(semaine == 1) {
			this.semaineDate.add("06/01/2020");
			this.semaineDate.add("07/01/2020");
			this.semaineDate.add("08/01/2020");
			this.semaineDate.add("09/01/2020");
			this.semaineDate.add("10/01/2020");
			this.semaineDate.add("11/01/2020");
		}
		if(semaine == 2) {
			this.semaineDate.add("13/01/2020");
			this.semaineDate.add("14/01/2020");
			this.semaineDate.add("15/01/2020");
			this.semaineDate.add("16/01/2020");
			this.semaineDate.add("17/01/2020");
			this.semaineDate.add("18/01/2020");
		}
				
		return this.semaineDate;
	}
    
    private ObservableList<EdtColonne> getDataColonneLundi(int semaine) {
    	
    	//Charge id utilisateur
    	Charg charg = new Charg();
    	this.u = charg.ChargId();
    			
    	//recherche des seances 
    	EdtEnseignantTable eT = new EdtEnseignantTable(Login.getInstance());
    	this.e = eT.findAll(this.u.getId(), semaine);//SEMAINE
    	
    	for(int i = 0; i < this.e.size(); i++) {	
    		
    		if(this.e.get(i).getDate().equals(dateLundi.getText())) {
    			//HEURE DEBUT-FIN
    			heure_debut= this.e.get(i).getHeure_debut();
    			heure_fin = this.e.get(i).getHeure_fin();
    			//MATIERE
    			matiere = eT.findNomMatieres(this.u.getId(), this.e.get(i).getId());
    			//NOM ENSEIGNANT
    			id = new ArrayList<Integer>();
    			id = eT.findEnseignantId(this.e.get(i).getId());
    			nomEnseignant = "";
    			for(int j = 0; j < id.size(); j++) {
    				if(id.size() > 1) {
    			        nomEnseignant += eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere) + " ";		
    				}else {
    					nomEnseignant = eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere);
    				}
    			}
    			
    			//GROUPES
    			nomGroupe = eT.findGroupe(this.e.get(i).getId());
    			String mots[] = nomGroupe.split(" ");
    			//PROMO
    			nomPromo = eT.findPromotion(mots[0]);
    			//SALLE
    			salle = eT.findSalle(this.e.get(i).getId());
    			//type
    			type = eT.findType(this.e.get(i).getId());
    			
    			etat = eT.findEtat(this.e.get(i).getId());
    			
    			if(etat == 0 ) {
    				etatfinal = "Annulé";
    			}else if(etat == 1) {
    				etatfinal = "validé";
    			}else if(etat == 2) {
    				etatfinal = "En cours de validation";
    			}
    			
    			listLundi.add(new EdtColonne(heure_debut + " à " + heure_fin + "  " + matiere + "  " + 
    			nomEnseignant + "  " + nomPromo + " " + nomGroupe + "  " + salle + "  " + type + " --- " +
    					etatfinal));
    		}
    	}
    			
    	return this.listLundi;
    }
    
private ObservableList<EdtColonne> getDataColonneMardi(int semaine) {
    	
    	//Charge id utilisateur
    	Charg charg = new Charg();
    	this.u = charg.ChargId();
    			
    	//recherche des seances 
    	EdtEnseignantTable eT = new EdtEnseignantTable(Login.getInstance());
    	this.e = eT.findAll(this.u.getId(), semaine);//SEMAINE
    	
    	for(int i = 0; i < this.e.size(); i++) {	
    		
    		if(this.e.get(i).getDate().equals(dateMardi.getText())) {
    			//HEURE DEBUT-FIN
    			heure_debut= this.e.get(i).getHeure_debut();
    			heure_fin = this.e.get(i).getHeure_fin();
    			//MATIERE
    			matiere = eT.findNomMatieres(this.u.getId(), this.e.get(i).getId());
    			//NOM ENSEIGNANT
    			id = new ArrayList<Integer>();
    			id = eT.findEnseignantId(this.e.get(i).getId());
    			nomEnseignant = "";
    			for(int j = 0; j < id.size(); j++) {
    				if(id.size() > 1) {
    			        nomEnseignant += eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere) + " ";		
    				}else {
    					nomEnseignant = eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere);
    				}
    			}
    			
    			//GROUPES
    			nomGroupe = eT.findGroupe(this.e.get(i).getId());
    			String mots[] = nomGroupe.split(" ");
    			//PROMO
    			nomPromo = eT.findPromotion(mots[0]);
    			//SALLE
    			salle = eT.findSalle(this.e.get(i).getId());
    			//type
    			type = eT.findType(this.e.get(i).getId());
    			
    			etat = eT.findEtat(this.e.get(i).getId());
    			
    			if(etat == 0 ) {
    				etatfinal = "Annulé";
    			}else if(etat == 1) {
    				etatfinal = "validé";
    			}else if(etat == 2) {
    				etatfinal = "En cours de validation";
    			}
    			
    			listMardi.add(new EdtColonne(heure_debut + " à " + heure_fin + "  " + matiere + "  " + 
    			nomEnseignant + "  " + nomPromo + " " + nomGroupe + "  " + salle + "  " + type + " --- " +
    					etatfinal));
    		}
    	}
    			
    	return this.listMardi;
    }

private ObservableList<EdtColonne> getDataColonneMercredi(int semaine) {
	
	//Charge id utilisateur
	Charg charg = new Charg();
	this.u = charg.ChargId();
			
	//recherche des seances 
	EdtEnseignantTable eT = new EdtEnseignantTable(Login.getInstance());
	this.e = eT.findAll(this.u.getId(), semaine);//SEMAINE
	
	for(int i = 0; i < this.e.size(); i++) {	
		
		if(this.e.get(i).getDate().equals(dateMercredi.getText())) {
			//HEURE DEBUT-FIN
			heure_debut= this.e.get(i).getHeure_debut();
			heure_fin = this.e.get(i).getHeure_fin();
			//MATIERE
			matiere = eT.findNomMatieres(this.u.getId(), this.e.get(i).getId());
			//NOM ENSEIGNANT
			id = new ArrayList<Integer>();
			id = eT.findEnseignantId(this.e.get(i).getId());
			nomEnseignant = "";
			for(int j = 0; j < id.size(); j++) {
				if(id.size() > 1) {
			        nomEnseignant += eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere) + " ";		
				}else {
					nomEnseignant = eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere);
				}
			}
			
			//GROUPES
			nomGroupe = eT.findGroupe(this.e.get(i).getId());
			String mots[] = nomGroupe.split(" ");
			//PROMO
			nomPromo = eT.findPromotion(mots[0]);
			//SALLE
			salle = eT.findSalle(this.e.get(i).getId());
			//type
			type = eT.findType(this.e.get(i).getId());
			
			etat = eT.findEtat(this.e.get(i).getId());
			
			if(etat == 0 ) {
				etatfinal = "Annulé";
			}else if(etat == 1) {
				etatfinal = "validé";
			}else if(etat == 2) {
				etatfinal = "En cours de validation";
			}
			
			
			listMercredi.add(new EdtColonne(heure_debut + " à " + heure_fin + "  " + matiere + "  " + 
			nomEnseignant + "  " + nomPromo + " " + nomGroupe + "  " + salle + "  " + type + " --- " +
					etatfinal));
		}
	}
			
	return this.listMercredi;
}

private ObservableList<EdtColonne> getDataColonneJeudi(int semaine) {
	
	//Charge id utilisateur
	Charg charg = new Charg();
	this.u = charg.ChargId();
			
	//recherche des seances 
	EdtEnseignantTable eT = new EdtEnseignantTable(Login.getInstance());
	this.e = eT.findAll(this.u.getId(), semaine);//SEMAINE
	
	for(int i = 0; i < this.e.size(); i++) {	
		
		if(this.e.get(i).getDate().equals(dateJeudi.getText())) {
			//HEURE DEBUT-FIN
			heure_debut= this.e.get(i).getHeure_debut();
			heure_fin = this.e.get(i).getHeure_fin();
			//MATIERE
			matiere = eT.findNomMatieres(this.u.getId(), this.e.get(i).getId());
			//NOM ENSEIGNANT
			id = new ArrayList<Integer>();
			id = eT.findEnseignantId(this.e.get(i).getId());
			nomEnseignant = "";
			for(int j = 0; j < id.size(); j++) {
				if(id.size() > 1) {
			        nomEnseignant += eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere) + " ";		
				}else {
					nomEnseignant = eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere);
				}
			}
			
			//GROUPES
			nomGroupe = eT.findGroupe(this.e.get(i).getId());
			String mots[] = nomGroupe.split(" ");
			//PROMO
			nomPromo = eT.findPromotion(mots[0]);
			//SALLE
			salle = eT.findSalle(this.e.get(i).getId());
			//type
			type = eT.findType(this.e.get(i).getId());
			
			etat = eT.findEtat(this.e.get(i).getId());
			
			if(etat == 0 ) {
				etatfinal = "Annulé";
			}else if(etat == 1) {
				etatfinal = "validé";
			}else if(etat == 2) {
				etatfinal = "En cours de validation";
			}
			
			listJeudi.add(new EdtColonne(heure_debut + " à " + heure_fin + "  " + matiere + "  " + 
			nomEnseignant + "  " + nomPromo + " " + nomGroupe + "  " + salle + "  " + type + " --- " +
					etatfinal));
		}
	}
			
	return this.listJeudi;
}

private ObservableList<EdtColonne> getDataColonneVendredi(int semaine) {
	
	//Charge id utilisateur
	Charg charg = new Charg();
	this.u = charg.ChargId();
			
	//recherche des seances 
	EdtEnseignantTable eT = new EdtEnseignantTable(Login.getInstance());
	this.e = eT.findAll(this.u.getId(), semaine);//SEMAINE
	
	for(int i = 0; i < this.e.size(); i++) {	
		
		if(this.e.get(i).getDate().equals(dateVendredi.getText())) {
			//HEURE DEBUT-FIN
			heure_debut= this.e.get(i).getHeure_debut();
			heure_fin = this.e.get(i).getHeure_fin();
			//MATIERE
			matiere = eT.findNomMatieres(this.u.getId(), this.e.get(i).getId());
			//NOM ENSEIGNANT
			id = new ArrayList<Integer>();
			id = eT.findEnseignantId(this.e.get(i).getId());
			nomEnseignant = "";
			for(int j = 0; j < id.size(); j++) {
				if(id.size() > 1) {
			        nomEnseignant += eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere) + " ";		
				}else {
					nomEnseignant = eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere);
				}
			}
			
			//GROUPES
			nomGroupe = eT.findGroupe(this.e.get(i).getId());
			String mots[] = nomGroupe.split(" ");
			//PROMO
			nomPromo = eT.findPromotion(mots[0]);
			//SALLE
			salle = eT.findSalle(this.e.get(i).getId());
			//type
			type = eT.findType(this.e.get(i).getId());
			
			etat = eT.findEtat(this.e.get(i).getId());
			
			if(etat == 0 ) {
				etatfinal = "Annulé";
			}else if(etat == 1) {
				etatfinal = "validé";
			}else if(etat == 2) {
				etatfinal = "En cours de validation";
			}
			
			listVendredi.add(new EdtColonne(heure_debut + " à " + heure_fin + "  " + matiere + "  " + 
			nomEnseignant + "  " + nomPromo + " " + nomGroupe + "  " + salle + "  " + type + " --- " +
					etatfinal));
		}
	}
			
	return this.listVendredi;
}

private ObservableList<EdtColonne> getDataColonneSamedi(int semaine) {
	
	//Charge id utilisateur
	Charg charg = new Charg();
	this.u = charg.ChargId();
			
	//recherche des seances 
	EdtEnseignantTable eT = new EdtEnseignantTable(Login.getInstance());
	this.e = eT.findAll(this.u.getId(), semaine);//SEMAINE
	
	for(int i = 0; i < this.e.size(); i++) {	
		
		if(this.e.get(i).getDate().equals(dateSamedi.getText())) {
			//HEURE DEBUT-FIN
			heure_debut= this.e.get(i).getHeure_debut();
			heure_fin = this.e.get(i).getHeure_fin();
			//MATIERE
			matiere = eT.findNomMatieres(this.u.getId(), this.e.get(i).getId());
			//NOM ENSEIGNANT
			id = new ArrayList<Integer>();
			id = eT.findEnseignantId(this.e.get(i).getId());
			nomEnseignant = "";
			for(int j = 0; j < id.size(); j++) {
				if(id.size() > 1) {
			        nomEnseignant += eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere) + " ";		
				}else {
					nomEnseignant = eT.findEnseignantNom(id.get(j), this.e.get(i).getType_id(), matiere);
				}
			}
			
			//GROUPES
			nomGroupe = eT.findGroupe(this.e.get(i).getId());
			String mots[] = nomGroupe.split(" ");
			//PROMO
			nomPromo = eT.findPromotion(mots[0]);
			//SALLE
			salle = eT.findSalle(this.e.get(i).getId());
			//type
			type = eT.findType(this.e.get(i).getId());
			
			etat = eT.findEtat(this.e.get(i).getId());
			
			if(etat == 0 ) {
				etatfinal = "Annulé";
			}else if(etat == 1) {
				etatfinal = "validé";
			}else if(etat == 2) {
				etatfinal = "En cours de validation";
			}
			
			listSamedi.add(new EdtColonne(heure_debut + " à " + heure_fin + "  " + matiere + "  " + 
			nomEnseignant + "  " + nomPromo + " " + nomGroupe + "  " + salle + "  " + type + " --- " +
					etatfinal));
		}
	}
			
	return this.listSamedi;
}

}
