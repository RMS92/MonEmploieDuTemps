package mon.edt.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Login {

	private volatile static Connection connect;

	private static String url = "jdbc:mysql://localhost:3306/projetJava?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";

	private static String user = "root";

	private static String passwd = "";

	// Optimisation de la connection
	public static Connection getInstance() {
		if (connect == null) {
			synchronized (Login.class) {
				if (connect == null) {
					try {
						connect = DriverManager.getConnection(url, user, passwd);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return connect;
	}
}
