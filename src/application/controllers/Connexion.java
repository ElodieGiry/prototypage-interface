package application.controllers;

import java.io.IOException;

import application.models.ConnexionParam;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Connexion {
	
	@FXML
	private Button accueil, valider;
	@FXML
	private RadioButton hote, voyageur;
	@FXML
	private TextField id;
	
	private String typeConnexion;
	
	public void accueil() {
		Scene scene = accueil.getScene();
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../Accueil.fxml"));
			scene.setRoot(root);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void valider() {
		try {
			if(hote.isSelected()) {
				System.out.println("hote selectionné");
				typeConnexion="Hote";
				
				ConnexionParam param = ConnexionParam.getInstance();
		        param.setConnexion(id.getText(),typeConnexion);
				
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../views/ConceptAdeona.fxml"));
				Scene scene = valider.getScene();
				scene.setRoot(root);
				/*
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ConceptAdeona.fxml"));
				Parent root = (Parent) loader.load();
				DiscussionController secController = loader.getController();
				
				//secController.net = net;
				//secController.easyModel = easyModel;
				secController.id = hote;
				secController.setUpMessagerie();
				
				Stage stage = new Stage();
				stage.setTitle("Messagerie");
				stage.initModality(Modality.APPLICATION_MODAL);  
				stage.setScene(new Scene(root));
				stage.show();*/
			}
			else {
				typeConnexion="Voyageur";
				ConnexionParam param = ConnexionParam.getInstance();
		        param.setConnexion(id.getText(),typeConnexion);
		        
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../views/search-view.fxml"));
				Scene scene = valider.getScene();
				scene.setRoot(root);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}

