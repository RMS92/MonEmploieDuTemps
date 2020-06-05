package mon.edt.session;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import mon.edt.model.Utilisateur;

public class Sauv {

	private ObjectOutputStream oos;

	public Sauv() {

	}

	public void sauvUtilisateur(int id) {
		try {
			oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("sauv.txt"))));

			oos.writeObject(new Utilisateur(id));
			oos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
