package mon.edt.views.referent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import mon.edt.connection.Login;
import mon.edt.model.Seance;
import mon.edt.model.Utilisateur;
import mon.edt.session.Charg;
import mon.edt.table.EdtEnseignantTable;
import mon.edt.table.EdtReferentTable;

public class EdtControler implements Initializable {

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
	// LUNDI
	@FXML
	Label l8;
	@FXML
	Label l9;
	@FXML
	Label l10;
	@FXML
	Label l11;
	@FXML
	Label l12;
	@FXML
	Label l13;
	@FXML
	Label l14;
	@FXML
	Label l15;
	@FXML
	Label l16;
	@FXML
	Label l17;
	// MARDI
	@FXML
	Label m8;
	@FXML
	Label m9;
	@FXML
	Label m10;
	@FXML
	Label m11;
	@FXML
	Label m12;
	@FXML
	Label m13;
	@FXML
	Label m14;
	@FXML
	Label m15;
	@FXML
	Label m16;
	@FXML
	Label m17;
	// MERCREDI
	@FXML
	Label me8;
	@FXML
	Label me9;
	@FXML
	Label me10;
	@FXML
	Label me11;
	@FXML
	Label me12;
	@FXML
	Label me13;
	@FXML
	Label me14;
	@FXML
	Label me15;
	@FXML
	Label me16;
	@FXML
	Label me17;
	// jeudi
	@FXML
	Label j8;
	@FXML
	Label j9;
	@FXML
	Label j10;
	@FXML
	Label j11;
	@FXML
	Label j12;
	@FXML
	Label j13;
	@FXML
	Label j14;
	@FXML
	Label j15;
	@FXML
	Label j16;
	@FXML
	Label j17;
	// VENDREDI
	@FXML
	Label v8;
	@FXML
	Label v9;
	@FXML
	Label v10;
	@FXML
	Label v11;
	@FXML
	Label v12;
	@FXML
	Label v13;
	@FXML
	Label v14;
	@FXML
	Label v15;
	@FXML
	Label v16;
	@FXML
	Label v17;
	// SAMEDI
	@FXML
	Label s8;
	@FXML
	Label s9;
	@FXML
	Label s10;
	@FXML
	Label s11;
	@FXML
	Label s12;

	// SEMAINES
	@FXML
	Label semaine1;
	@FXML
	Label semaine2;

	// RECHERCHES
	@FXML
	TextField search;
	@FXML
	TextField searchSalle;
	@FXML
	TextField searchPromo;

	// POUR MATIERES
	private Utilisateur u;
	private ArrayList<Seance> e;
	private ArrayList<Seance> s;
	// SEARCH LIST
	private ArrayList<Seance> ss;
	private ArrayList<String> date;
	private ArrayList<String> semaineDate;
	private ArrayList<Integer> id;
	private String nomMatiere;
	private String nomEnseignant;
	private String nomGroupe;
	private String nomPromo;
	private String semaine;
	private String salle;
	private String capacite;
	private String type;
	private int etat;

	private String currentSemaine;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// Charge id utilisateur
		this.currentSemaine = semaine1.getText();
		initTable(semaine1);
	}

	@FXML
	public void chargeSemaine(MouseEvent event) {
		if (event.getSource() == semaine1) {
			initTable(semaine1);
			this.currentSemaine = semaine1.getText();

		}
		if (event.getSource() == semaine2) {
			initTable(semaine2);
			this.currentSemaine = semaine2.getText();
		}
	}

	// CHERCHE MATIERE GROUPE ETC... 1ER BOUTON
	@FXML
	public void searchTable() {
		// recherche des seances
		initTousLabels();
		semaine = this.currentSemaine;
		System.out.println(this.currentSemaine);

		int recupSemaine = Integer.parseInt(semaine);
		EdtReferentTable eT = new EdtReferentTable(Login.getInstance());
		// this.e = eT.findAll(this.u.getId(), recupSemaine);
		date = this.getDates(recupSemaine);
		dateLundi.setText(date.get(0));
		dateMardi.setText(date.get(1));
		dateMercredi.setText(date.get(2));
		dateJeudi.setText(date.get(3));
		dateVendredi.setText(date.get(4));
		dateSamedi.setText(date.get(5));
		searchMatiere(recupSemaine);
	}

	/// CHERCHE TOUTES LES SALLES DE TOUS LES GROUPES
	@FXML
	public void searchSalle() {
		initTousLabels();
		semaine = this.currentSemaine;
		System.out.println(this.currentSemaine);

		int recupSemaine = Integer.parseInt(semaine);
		EdtReferentTable eT = new EdtReferentTable(Login.getInstance());
		// this.e = eT.findAll(this.u.getId(), recupSemaine);
		date = this.getDates(recupSemaine);
		dateLundi.setText(date.get(0));
		dateMardi.setText(date.get(1));
		dateMercredi.setText(date.get(2));
		dateJeudi.setText(date.get(3));
		dateVendredi.setText(date.get(4));
		dateSamedi.setText(date.get(5));
		searchSalle(recupSemaine);
	}

	/// CHERCHE TOUTES LES PROMOS
	@FXML
	public void searchPromos() {
		initTousLabels();
		semaine = this.currentSemaine;
		System.out.println(this.currentSemaine);

		int recupSemaine = Integer.parseInt(semaine);
		EdtReferentTable eT = new EdtReferentTable(Login.getInstance());
		// this.e = eT.findAll(this.u.getId(), recupSemaine);
		date = this.getDates(recupSemaine);
		dateLundi.setText(date.get(0));
		dateMardi.setText(date.get(1));
		dateMercredi.setText(date.get(2));
		dateJeudi.setText(date.get(3));
		dateVendredi.setText(date.get(4));
		dateSamedi.setText(date.get(5));
		searchPromos(recupSemaine);
	}

	// TROUVE MATIERES PREMIER BOUTON
	private void searchMatiere(int semaine) {
		String recupSearch;
		recupSearch = search.getText();

		// Charge id utilisateur
		Charg charg = new Charg();
		this.u = charg.ChargId();

		// recherche des seances
		EdtReferentTable eT = new EdtReferentTable(Login.getInstance());
		this.s = eT.findAllSearch(this.u.getId(), semaine, recupSearch);

		chargerCasesEdt(this.s, eT);
	}

	///// TROUVE MATIERES DEUXIEME BOUTON
	private void searchSalle(int semaine) {

		String recupSearch;
		recupSearch = searchSalle.getText();
		// Charge id utilisateur
		Charg charg = new Charg();
		this.u = charg.ChargId();

		// recherche des seances
		EdtReferentTable eT = new EdtReferentTable(Login.getInstance());
		this.ss = eT.findSearchSalle(semaine, recupSearch);

		chargerCasesEdt(this.ss, eT);
	}

    ///// TROUVE PROMOS TROISIEME BOUTON
	private void searchPromos(int semaine) {

		String recupSearch;
		recupSearch = searchPromo.getText();
		// Charge id utilisateur
		Charg charg = new Charg();
		this.u = charg.ChargId();

		// recherche des seances
		EdtReferentTable eT = new EdtReferentTable(Login.getInstance());
		this.ss = eT.findSearchPromos(semaine, recupSearch);

		chargerCasesEdt(this.ss, eT);
	}

	// TROUVE MATIERES EDT
	/*
	 * private void chargerEdt(int semaine) {
	 * 
	 * //Charge id utilisateur Charg charg = new Charg(); this.u = charg.ChargId();
	 * 
	 * //recherche des seances EdtReferentTable eT = new
	 * EdtReferentTable(Login.getInstance()); this.e = eT.findAll(this.u.getId(),
	 * semaine);
	 * 
	 * chargerCasesEdt(this.e, eT);
	 * 
	 * }
	 */

	// INIT TOUS LES LABELS ET CHARGE EDT
	private void initTable(Label label) {
		/*
		 * Charg charg = new Charg(); this.u = charg.ChargId();
		 */

		// recherche des seances
		initTousLabels();
		semaine = label.getText();
		int recupSemaine = Integer.parseInt(semaine);
		/*
		 * EdtReferentTable eT = new EdtReferentTable(Login.getInstance()); this.e =
		 * eT.findAll(this.u.getId(), recupSemaine);
		 */
		date = this.getDates(recupSemaine);
		dateLundi.setText(date.get(0));
		dateMardi.setText(date.get(1));
		dateMercredi.setText(date.get(2));
		dateJeudi.setText(date.get(3));
		dateVendredi.setText(date.get(4));
		dateSamedi.setText(date.get(5));

		// chargerEdt(recupSemaine);
	}

	private ArrayList<String> getDates(int semaine) {
		this.semaineDate = new ArrayList<String>();
		if (semaine == 1) {
			this.semaineDate.add("06/01/2020");
			this.semaineDate.add("07/01/2020");
			this.semaineDate.add("08/01/2020");
			this.semaineDate.add("09/01/2020");
			this.semaineDate.add("10/01/2020");
			this.semaineDate.add("11/01/2020");
		}
		if (semaine == 2) {
			this.semaineDate.add("13/01/2020");
			this.semaineDate.add("14/01/2020");
			this.semaineDate.add("15/01/2020");
			this.semaineDate.add("16/01/2020");
			this.semaineDate.add("17/01/2020");
			this.semaineDate.add("18/01/2020");
		}

		return this.semaineDate;
	}

	private void chargerMatiere(Label label, ArrayList<Seance> g, EdtReferentTable eT, int i) {
		// NOM MATIERE
		nomMatiere = eT.findNomMatieres(this.u.getId(), g.get(i).getId());

		// IDS ET NOM
		id = new ArrayList<Integer>();
		id = eT.findEnseignantId(g.get(i).getId());
		// INIT NOM
		nomEnseignant = "";
		for (int j = 0; j < id.size(); j++) {
			if (id.size() > 1) {
				nomEnseignant += eT.findEnseignantNom(id.get(j), g.get(i).getType_id(), nomMatiere) + " ";
			} else {
				nomEnseignant = eT.findEnseignantNom(id.get(j), g.get(i).getType_id(), nomMatiere);
			}
		}

		// GROUPES
		nomGroupe = eT.findGroupe(g.get(i).getId());
		String mots[] = nomGroupe.split(" ");
		// PROMO
		nomPromo = eT.findPromotion(mots[0]);
		// SALLE
		salle = eT.findSalle(g.get(i).getId());
		// CAPACITE
		capacite = eT.findCapacite(g.get(i).getId());
		// type
		type = eT.findType(g.get(i).getId());
		label.setText(nomMatiere + "\n" + nomEnseignant + " " + type + "\n" + nomPromo + " " + nomGroupe + " <" + salle
				+ " (" + capacite + ")" + ">");

		etat = eT.findEtat(g.get(i).getId());

		if (nomMatiere.equals("POO Java")) {
			label.setBackground(
					new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		if (nomMatiere.equals("Language C")) {
			label.setBackground(
					new Background(new BackgroundFill(Color.LIGHTGOLDENRODYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		if (nomMatiere.equals("Probabilités et statistiques")) {
			label.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		if (nomMatiere.equals("Analyse de Fourier")) {
			label.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		if (nomMatiere.equals("Electronique")) {
			label.setBackground(
					new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		if (nomMatiere.equals("Electromagnétique")) {
			label.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		if (nomMatiere.equals("Physique optique")) {
			label.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		if (nomMatiere.equals("Thermodynamique")) {
			label.setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		if (nomMatiere.equals("Amphi d'informations")) {
			label.setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		if (nomMatiere.equals("Droit du travail")) {
			label.setBackground(
					new Background(new BackgroundFill(Color.MEDIUMPURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		if (nomMatiere.equals("Anglais") || nomMatiere.equals("Espagnol")) {
			label.setBackground(new Background(new BackgroundFill(Color.CORAL, CornerRadii.EMPTY, Insets.EMPTY)));
		}

		/// ETAT
		if (etat == 0) {
			label.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
		} else if (etat == 2) {
			label.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		}

	}

	private void initLabel(Label label) {
		label.setText("");
		label.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	private void initTousLabels() {

		initLabel(dateLundi);
		initLabel(dateMardi);
		initLabel(dateMercredi);
		initLabel(dateJeudi);
		initLabel(dateVendredi);
		initLabel(dateSamedi);
		// LUNDI
		initLabel(l8);
		initLabel(l9);
		initLabel(l10);
		initLabel(l11);
		initLabel(l12);
		initLabel(l13);
		initLabel(l14);
		initLabel(l15);
		initLabel(l16);
		// MARDI
		initLabel(m8);
		initLabel(m9);
		initLabel(m10);
		initLabel(m11);
		initLabel(m12);
		initLabel(m13);
		initLabel(m14);
		initLabel(m15);
		initLabel(m16);
		// MERCREDI
		initLabel(me8);
		initLabel(me9);
		initLabel(me10);
		initLabel(me11);
		initLabel(me12);
		initLabel(me13);
		initLabel(me14);
		initLabel(me15);
		initLabel(me16);
		// jeudi
		initLabel(j8);
		initLabel(j9);
		initLabel(j10);
		initLabel(j11);
		initLabel(j12);
		initLabel(j13);
		initLabel(j14);
		initLabel(j15);
		initLabel(j16);
		// VENDREDI
		initLabel(v8);
		initLabel(v9);
		initLabel(v10);
		initLabel(v11);
		initLabel(v12);
		initLabel(v13);
		initLabel(v14);
		initLabel(v15);
		initLabel(v16);
		// SAMEDI
		initLabel(s8);
		initLabel(s9);
		initLabel(s10);
		initLabel(s11);
		initLabel(s12);
	}

	public void chargerCasesEdt(ArrayList<Seance> g, EdtReferentTable eT) {
		for (int i = 0; i < g.size(); i++) {
			System.out.println(g.get(i).getId());
			// LUNDI
			if (g.get(i).getDate().equals(dateLundi.getText())) {
				if (g.get(i).getHeure_debut().equals("8")) {
					chargerMatiere(l8, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("9")) {
					chargerMatiere(l9, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("10")) {
					chargerMatiere(l10, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("11")) {
					chargerMatiere(l11, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("12")) {
					chargerMatiere(l12, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("13")) {
					chargerMatiere(l13, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("14")) {
					chargerMatiere(l14, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("15")) {
					chargerMatiere(l15, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("16")) {
					chargerMatiere(l16, g, eT, i);
				}

			}

			// MARDI
			if (g.get(i).getDate().equals(dateMardi.getText())) {
				if (g.get(i).getHeure_debut().equals("8")) {
					chargerMatiere(m8, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("9")) {
					chargerMatiere(m9, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("10")) {
					chargerMatiere(m10, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("11")) {
					chargerMatiere(m11, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("12")) {
					chargerMatiere(m12, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("13")) {
					chargerMatiere(m13, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("14")) {
					chargerMatiere(m14, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("15")) {
					chargerMatiere(m15, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("16")) {
					chargerMatiere(m16, g, eT, i);
				}
			}

			// MERCREDI
			if (g.get(i).getDate().equals(dateMercredi.getText())) {
				if (g.get(i).getHeure_debut().equals("8")) {
					chargerMatiere(me8, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("9")) {
					chargerMatiere(me9, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("10")) {
					chargerMatiere(me10, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("11")) {
					chargerMatiere(me11, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("12")) {
					chargerMatiere(me12, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("13")) {
					chargerMatiere(me13, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("14")) {
					chargerMatiere(me14, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("15")) {
					chargerMatiere(me15, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("16")) {
					chargerMatiere(me16, g, eT, i);
				}
			}

			// JEUDI
			if (g.get(i).getDate().equals(dateJeudi.getText())) {
				if (g.get(i).getHeure_debut().equals("8")) {
					chargerMatiere(j8, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("9")) {
					chargerMatiere(j9, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("10")) {
					chargerMatiere(j10, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("11")) {
					chargerMatiere(j11, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("12")) {
					chargerMatiere(j12, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("13")) {
					chargerMatiere(j13, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("14")) {
					chargerMatiere(j14, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("15")) {
					chargerMatiere(j15, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("16")) {
					chargerMatiere(j16, g, eT, i);
				}
			}

			// VENDREDI
			if (g.get(i).getDate().equals(dateVendredi.getText())) {
				if (g.get(i).getHeure_debut().equals("8")) {
					chargerMatiere(v8, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("9")) {
					chargerMatiere(v9, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("10")) {
					chargerMatiere(v10, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("11")) {
					chargerMatiere(v11, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("12")) {
					chargerMatiere(v12, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("13")) {
					chargerMatiere(v13, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("14")) {
					chargerMatiere(v14, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("15")) {
					chargerMatiere(v15, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("16")) {
					chargerMatiere(v16, g, eT, i);
				}
			}

			// SAMEDI
			if (g.get(i).getDate().equals(dateSamedi.getText())) {
				if (g.get(i).getHeure_debut().equals("8")) {
					chargerMatiere(s8, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("9")) {
					chargerMatiere(s9, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("10")) {
					chargerMatiere(s10, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("11")) {
					chargerMatiere(s11, g, eT, i);
				}
				if (g.get(i).getHeure_debut().equals("12")) {
					chargerMatiere(s12, g, eT, i);
				}

			}
		}
	}

}
