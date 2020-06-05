package mon.edt.session;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import mon.edt.connection.Login;
import mon.edt.model.Utilisateur;
import mon.edt.table.UtilisateurTable;

public class Charg {

	private Utilisateur u, current;

	private ObjectInputStream ois;

	public Charg() {

	}

	public Utilisateur ChargId() {

		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("sauv.txt"))));

			this.u = ((Utilisateur) ois.readObject());
			ois.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		UtilisateurTable uT = new UtilisateurTable(Login.getInstance());
		this.current = uT.find(this.u.getId());

		return current;

	}
}
