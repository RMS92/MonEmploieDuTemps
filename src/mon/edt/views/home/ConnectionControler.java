package mon.edt.views.home;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mon.edt.MainClass;
import mon.edt.connection.Login;
import mon.edt.model.Utilisateur;
import mon.edt.session.Sauv;
import mon.edt.table.UtilisateurTable;

public class ConnectionControler implements Initializable{
	
	private Utilisateur u;
	
	private int droit;
    
	private Label closeLabel;
	
	@FXML
	private Label errorsLabel;
	
	@FXML
	private TextField emailField;
	
	@FXML
	private TextField passwordField;
	
	@FXML
	private Button btnSignin;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	
	public void handleButtonAction(MouseEvent event) {
		if(event.getSource() == closeLabel) {
			System.exit(0);
		}
		
		if(event.getSource() == btnSignin) {
			
			String url = "";
			if (login() == 1) {
				url = "views/admin/Layouts.fxml";
                redirection(event,url);
            }
			else if (login() == 2) {
				url = "views/referent/Layouts.fxml";
                redirection(event,url);
            }
			else if (login() == 3) {
				url = "views/prof/Layouts.fxml";
                redirection(event,url);
            }
			else if (login() == 4) {
				url = "views/etudiant/Layouts.fxml";
                redirection(event,url);
            }
			else {
				errorsLabel.setText("Identifiants ou mot de passe incorrects !");
			}
		}
	}
	
	public int getIdUtil() {
		return this.u.getId();
	}
	
	private int login() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String email = emailField.getText();
		String password = passwordField.getText();
		
		try {
			
			ps = Login.getInstance().prepareStatement("SELECT * FROM utilisateur "
					+ "WHERE email = ? "
					+ "AND password = ?");
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();
			
			if(!rs.next()) {
				
				errorsLabel.setTextFill(Color.TOMATO);
				errorsLabel.setText("Identifiants ou mot de passe incorrects !");
				return -1;
				
			}else {
				//recupure le type de droit
				this.droit = rs.getInt("droit");			
                
				//Cherche et sauvegarde l'id de lutilisateur dans sauv.txt
				UtilisateurTable uT = new UtilisateurTable(Login.getInstance());
				this.u = uT.find(rs.getInt("id"));
				Sauv s = new Sauv();
				s.sauvUtilisateur(this.u.getId());
											
				errorsLabel.setTextFill(Color.GREEN);
				errorsLabel.setText("Login successful !");
				
				return this.droit;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	private void redirection(MouseEvent event, String url) {
		try {

            //add you loading or delays - ;-)
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            
            Scene scene = new Scene(FXMLLoader.load(MainClass.class.getResource(url)));
            
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
	}
}

