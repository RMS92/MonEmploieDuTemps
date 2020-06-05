package mon.edt.views.admin;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mon.edt.connection.Login;
import mon.edt.model.Groupe;
import mon.edt.model.Salle;
import mon.edt.model.Seance;
import mon.edt.model.Utilisateur;
import mon.edt.table.GroupeTable;
import mon.edt.table.ModifsAdminTable;
import mon.edt.table.SeanceTable;
import mon.edt.table.UtilisateurTable;

public class EdtModifs3Controler implements Initializable {

	@FXML
	TextField dateField;
	@FXML
	TextField semaineField;
	@FXML
	TextField heure_debutField;
	@FXML
	TextField heure_finField;
	@FXML
	TextField promoField;
	@FXML
	TextField groupeField;
	@FXML
	TextField enseignantField;
	@FXML
	TextField salleField;
	@FXML
	TextField etatField;
	@FXML
	TextField coursField;
	@FXML
	TextField type_coursField;

	@FXML
	Label errors;

	private Groupe g;
	private Utilisateur uE;
	private Salle s;
	//
	private Seance seance;
	private ArrayList<Integer> idsU;

	String date;
	String semaine;
	String heure_debut;
	String heure_fin;
	String promo;
	String groupe;
	String enseignant;
	String salle;
	String etat;
	String cours;
	String type_cours;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void creerSeance() {

		recupInfos();
		// System.out.println(this.date);
		// Blindage vide
		if (this.date.equals("")) {
			errors.setText("Veuillez-remplir le champ date !");
		} else if (this.heure_debut.equals("")) {
			errors.setText("Veuillez-remplir le champ d'heure de debut !");
		} else if (this.heure_fin.equals("")) {
			errors.setText("Veuillez-remplir le champ d'heure de fin !");
		} else if (this.promo.equals("")) {
			errors.setText("Veuillez-remplir le champ promotion");
		} else if (this.groupe.equals("")) {
			errors.setText("Veuillez-remplir le champ groupe");
		} else if (this.enseignant.equals("")) {
			errors.setText("Veuillez-remplir le champ enseignant");
		} else if (this.salle.equals("")) {
			errors.setText("Veuillez-remplir le champ salle");
		} else if (this.etat.equals("")) {
			errors.setText("Veuillez-remplir le champ état");
		} else if (this.cours.equals("")) {
			errors.setText("Veuillez-remplir le champ cours");
		} else if (this.type_cours.equals("")) {
			errors.setText("Veuillez-remplir le champ type_cours");
		}
		// Blindage nimporte quoi
		else if (this.date.equals("12/01/2020") || this.date.equals("19/01/2020")) {
			errors.setText("Le cours ne peut pas etre placé un dimanche !");
		} else if (!this.semaine.equals("1") && !this.semaine.equals("2")) {
			errors.setText("Veuillez-saisir une semaine existante ! ");
		} else if (this.heure_debut.equals("7") || this.heure_debut.equals("17")) {
			errors.setText("La séance ne contient pas les bonnes horaires !");
		} else if (this.heure_fin.equals("8") || this.heure_fin.equals("18")) {
			errors.setText("La séance ne contient pas les bonnes horaires !");
		} else if (!this.promo.equals("Ing1") && !this.promo.equals("Ing2") && !this.promo.equals("Ing3")) {
			System.out.println(this.promo);
			errors.setText("Veuillez-saisir une promotion existante");
		} else if (!this.groupe.equals("Gr01") && !this.groupe.equals("Gr02") && !this.groupe.equals("Gr04")
				&& !this.groupe.equals("Gr05") && !this.groupe.equals("Gr07") && !this.groupe.equals("Gr08")
				&& !this.groupe.equals("Gr01 Gr02") && !this.groupe.equals("Gr04 Gr05")
				&& !this.groupe.equals("Gr07 Gr08")) {
			errors.setText("Veuillez-saisir un groupe existant !");
		} else if (!this.enseignant.equals("Segado") && !this.enseignant.equals("Crambes")
				&& !this.enseignant.equals("Cerbah") && !this.enseignant.equals("Lopes")
				&& !this.enseignant.equals("Guillemot") && !this.enseignant.equals("Coudray")
				&& !this.enseignant.equals("Maupile") && !this.enseignant.equals("Reese")
				&& !this.enseignant.equals("Murillo") && !this.enseignant.equals("Muller")
				&& !this.enseignant.equals("Rendler")
				&& !this.enseignant.equals("Segado Rendler") && !this.enseignant.equals("Segado Coudray Crambes")) {
			errors.setText("Veuillez-saisir un enseignant existant !");
		} else if (!this.salle.equals("P445") && !this.salle.equals("P315") && !this.salle.equals("P407")
				&& !this.salle.equals("SC201") && !this.salle.equals("P415") && !this.salle.equals("P446")
				&& !this.salle.equals("P405(labo)") && !this.salle.equals("EM009") && !this.salle.equals("EM010")
				&& !this.salle.equals("EM009 EM010") && !this.salle.equals("P445 P446") && !this.salle.equals("EM009 EM010 SC201")) {
			errors.setText("Veuillez-saisir une salle existante !");
		} else if (!this.etat.equals("1") && !this.etat.equals("2") && !this.etat.equals("3")) {
			errors.setText("Veuillez-saisir un etat existant !");
		} else if (!this.cours.equals("1") && !this.cours.equals("2") && !this.cours.equals("3")
				&& !this.cours.equals("4") && !this.cours.equals("5") && !this.cours.equals("6")
				&& !this.cours.equals("7") && !this.cours.equals("8") && !this.cours.equals("9")
				&& !this.cours.equals("10") && !this.cours.equals("11") && !this.cours.equals("12")) {
			errors.setText("Veuillez-saisir un cours existant !");
		} else if (!this.type_cours.equals("1") && !this.type_cours.equals("2") && !this.type_cours.equals("3")
				&& !this.type_cours.equals("4") && !this.type_cours.equals("5") && !this.type_cours.equals("6")) {
			errors.setText("Veuillez-saisir un type de cours existant !");
		}

		else {

			// find la seance
			GroupeTable gT = new GroupeTable(Login.getInstance());
			this.g = gT.findGroupeValider(this.groupe, this.promo, this.date, this.heure_debut, this.heure_fin);

			if (this.g.getId() == 0) {

				UtilisateurTable uT = new UtilisateurTable(Login.getInstance());
				this.uE = uT.findEnseignantValider(this.enseignant, this.date, this.heure_debut, this.heure_fin);

				if (this.uE.getId() == 0) {

					this.s = gT.findSalleValider(this.salle, this.date, this.heure_debut, this.heure_fin);

					if (this.s.getId() == 0) {

						// errors.setText("Ok");
						ModifsAdminTable mT = new ModifsAdminTable(Login.getInstance());

						// recup
						int heure_debut = Integer.valueOf(this.heure_debut);
						int semaine = Integer.valueOf(this.semaine);
						int etat = Integer.valueOf(this.etat);
						int cours_id = Integer.valueOf(this.cours);
						int type_cours = Integer.valueOf(this.type_cours);

						// Insert seance
						mT.insertSeance(semaine, this.date, heure_debut, this.heure_fin, etat, cours_id, type_cours);

						// Insert seance enseignant
						SeanceTable sT = new SeanceTable(Login.getInstance());
						int idSeance = sT.findLastSeanceID();

						String mots3[] = this.enseignant.split(" ");
						ArrayList<Integer> idE = new ArrayList<Integer>();
						
						for(int i = 0; i < mots3.length; i++) {
							idE = uT.findIds(mots3[i]);
						}
						
						for (int i = 0; i < idE.size(); i++) {

							mT.insertSeanceEnseignant(idSeance, idE.get(i));
						}
						
						//
						String mots[] = this.groupe.split(" ");

						// Insert seance etudiant
						this.idsU = new ArrayList<Integer>();

						for (int i = 0; i < mots.length; i++) {
							this.idsU = uT.findIdsNom(mots[i]);
						}

						for (int i = 0; i < this.idsU.size(); i++) {

							mT.insertSeanceEtudiant(idSeance, this.idsU.get(i));
						}

						// Insert seance groupe
						ArrayList<Integer> idG = new ArrayList<Integer>();

						for (int i = 0; i < mots.length; i++) {
							// System.out.println(mots[i]);
							idG = gT.findGroupeId(mots[i]);
						}

						for (int i = 0; i < idG.size(); i++) {
							// System.out.println(idG.get(i));
							mT.insertSeanceGroupe(idSeance, idG.get(i));
						}
						// Insert seance salle
						String mots2[] = this.salle.split(" ");
						ArrayList<Integer> idS = new ArrayList<Integer>();

						for (int i = 0; i < mots2.length; i++) {
							idS = gT.findSalleId(mots2[i]);
						}

						for(int i = 0; i < idS.size(); i++) {
							mT.insertSeanceSalle(idSeance, idS.get(i));
						}

						errors.setText("La séance a bien été ajouté aux " + this.promo + " " + this.groupe);

					} else {
						errors.setText("La salle " + this.salle + " est déjà occupée le " + this.date + " de "
								+ this.heure_debut + "h à " + this.heure_fin + "h !");
					}

				} else {
					errors.setText("L'enseignant " + this.enseignant + " a déjà cours le " + this.date + " de "
							+ this.heure_debut + "h à " + this.heure_fin + "h !");
				}

			} else {
				errors.setText("Le groupe " + this.promo + " " + this.groupe + " a déjà cours le " + this.date + " de "
						+ this.heure_debut + "h à " + this.heure_fin + "h !");
			}

		}

	}

	private void recupInfos() {

		this.date = dateField.getText();
		this.semaine = semaineField.getText();
		this.heure_debut = heure_debutField.getText();
		this.heure_fin = heure_finField.getText();
		this.promo = promoField.getText();
		this.groupe = groupeField.getText();
		this.enseignant = enseignantField.getText();
		this.salle = salleField.getText();
		this.etat = etatField.getText();
		this.cours = coursField.getText();
		this.type_cours = type_coursField.getText();
	}

}
