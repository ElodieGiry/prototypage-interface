package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


import application.Main;
import javafx.event.ActionEvent;
import application.models.ConnexionParam;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConceptAdeonaController {
	
	@FXML
	private Button btnProposerSejour;
	
	@FXML
	private Button btnVoirSejour;
	
	@FXML private ImageView img1,img2,img3,img4,img5;
	
	int idUtilisateurConnecte = 1;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image image1 = new Image("https://www.horizon-provence.com/pont-avignon/photos/pont-saint-benezet08.jpg");
        ImageView img1 = new ImageView();
        img1.setImage(image1);
        Image image2 = new Image("https://www.rustica.fr/images/247913j-l760-h550.jpg");
        img2.setImage(image2);
        Image image3 = new Image("https://www.josera.fr/media/magefan_blog/Pferd_Ausritt_shutterstock_1883230375_Ratgeber-Headerbild_1905x1040.jpg");
        img3.setImage(image3);
        Image image4 = new Image("https://www.cabanes-de-france.com/wp-content/uploads/2018/06/clarisse-creaphotos-7.jpg");
        img4.setImage(image4);
        Image image5 = new Image("https://resize2.prod.docfr.doc-media.fr/rcrop/480,280,center-middle/img/var/doctissimo/storage/images/fr/www/famille/maison/jardinage/661232-2-fre-FR/jardinage.jpg");
        img5.setImage(image5);
	}

	public void proposerSejour() {
		Scene scene = btnProposerSejour.getScene();
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../views/DetailSejourHote.fxml"));
			scene.setRoot(root);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void voirSejour() {
		Scene scene = btnVoirSejour.getScene();
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../views/search-view.fxml"));
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

