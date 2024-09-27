package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListeSejourHoteBarreRechercheController {

	@FXML
	private Button btnProposerAutreSejour;
	@FXML
	private Button btnConcept;
	
	@FXML private Pane contact;
	int idUtilisateurConnecte = 1;
	public void initialize(URL arg0, ResourceBundle arg1) {
		contact.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}

	public void proposerAutreSejour() {
		Scene scene = btnProposerAutreSejour.getScene();
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../views/DetailSejourHote.fxml"));
			scene.setRoot(root);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void concept() {
		Scene scene = btnConcept.getScene();
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../views/ConceptAdeona.fxml"));
			scene.setRoot(root);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 public void onMenuButtonProfilClicked(ActionEvent event) {
			
		}
	public void onMenuButtonMessagerieClicked(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/ListeDiscussions.fxml"));
		Parent root = (Parent) loader.load();
		DiscussionController secController = loader.getController();
		
		secController.idUtilisateurConnecte = idUtilisateurConnecte;
		secController.setUpMessagerie();
		
		Stage stage = new Stage();
		stage.setTitle("Messagerie");
		stage.initModality(Modality.APPLICATION_MODAL);  
		stage.setScene(new Scene(root));
		stage.show();
	}
	public void onMenuButtonDeconnexionClicked(ActionEvent event) {
		
	}
	
}
