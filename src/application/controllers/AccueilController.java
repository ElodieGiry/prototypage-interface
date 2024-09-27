package application.controllers;

import application.Main;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import application.controllers.SearchController;
import application.models.Voyage;
import com.opencsv.bean.CsvToBeanBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AccueilController implements Initializable{
	@FXML private Pane contact;
	@FXML private TextField txtRecherche;
	@FXML private DatePicker debut_sejour;
	@FXML private DatePicker fin_sejour;
	@FXML private Text txtWarning;

	@FXML private Button connexion, profil;

	private ArrayList<Voyage> listAllVoyages;
	private ArrayList<Voyage> listVoyagesResults;

	@FXML private ImageView img1,img2,img3,img4,img5;
	int idUtilisateurConnecte = 13;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		contact.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
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

		// get all voyages
		listAllVoyages = this.getAllVoyages();

		// init datepickers
		LocalDate today = LocalDate.now();
		updateDatePicker(debut_sejour, today);
		updateDatePicker(fin_sejour, today);

		debut_sejour.valueProperty().addListener((ov, oldValue, newValue) -> {
			updateDatePicker(fin_sejour, debut_sejour.getValue());
			if (fin_sejour.getValue().isBefore(debut_sejour.getValue())){
				fin_sejour.setValue(debut_sejour.getValue());
			}
		});
	}



        

	@FXML
	protected void onSearchButtonClick(ActionEvent event) throws IOException {
		if (txtRecherche.getText().length() > 1) {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/search-view.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			SearchController searchController = (SearchController)fxmlLoader.getController();
			listVoyagesResults = searchVoyagesResults();
			searchController.setListAllVoyages(listAllVoyages);
			searchController.setListVoyagesResults(listVoyagesResults);
			searchController.setTxtRecherche2(txtRecherche);
			searchController.setDebut_sejour2(debut_sejour);
			searchController.setFin_sejour2(fin_sejour);
			searchController.init();

			Scene scene = new Scene(root);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} else {
			displayWarning();
		}
	}

	public ArrayList<Voyage> getAllVoyages(){
		String fileName = "src/application/assets/voyages.csv";

		ArrayList<Voyage> voyages = null;
		try {
			voyages = (ArrayList<Voyage>) new CsvToBeanBuilder(new FileReader(fileName)).withSeparator(';')
					.withType(Voyage.class)
					.build()
					.parse();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//        for (Voyage v: voyages) {System.out.println(v.getVille() + " : " + v.getContreparties());}
		return voyages;
	}

	public ArrayList<Voyage> searchVoyagesResults(){
		System.out.println(this.txtRecherche.getText());
		if (debut_sejour.getValue() != null)	System.out.println(this.debut_sejour.getValue());
		if (fin_sejour.getValue() != null)	System.out.println(this.fin_sejour.getValue());

		// FOR DEMO: if input= "all", results if all 10000 voyages
		if (txtRecherche.getText().equals("all")){
			return this.listAllVoyages;
		}

		ArrayList<Voyage> results = new ArrayList<Voyage>();
		DateTimeFormatter df = new DateTimeFormatterBuilder()
			    // case insensitive to parse JAN and FEB
			    .parseCaseInsensitive()
			    // add pattern
			    .appendPattern("dd/MM/yyyy")
			    // create formatter (use English Locale to parse month names)
			    .toFormatter(Locale.FRENCH);
		for (Voyage v: listAllVoyages){
			if ((v.getVille().contains(txtRecherche.getText()))
					&& (debut_sejour.getValue() == null || !debut_sejour.getValue().isBefore(LocalDate.parse(v.getDateArrivee())) )
					&& (fin_sejour.getValue() == null || !fin_sejour.getValue().isAfter(LocalDate.parse(v.getDateDepart())) ) ){
				results.add(v);
			}
		}
		System.out.println(results.size());
		return results;
	}

	public void displayWarning(){
		txtWarning.setVisible(true);
		new Thread( new Runnable() {
			public void run()  {
				try  { Thread.sleep( 3000 ); }
				catch (InterruptedException ie)  {}
				txtWarning.setVisible(false);
			}
		} ).start();
	}

	public void updateDatePicker(DatePicker dp, LocalDate startLocalDate){
		// restriction sur date picker
		dp.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				setDisable(empty || date.compareTo(startLocalDate) < 0 );
			}
		});
	}
	
	public void goToAccueil(MouseEvent event) throws IOException {
		try {

			Parent Accueil = FXMLLoader.load(getClass().getResource("/application/views/Accueil.fxml"));
			Scene AccueilScene = new Scene(Accueil);

			Stage settStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			settStage.setScene(AccueilScene);
			settStage.show();
			System.out.println("Vous êtes sur la page d'accueil");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connexion() {
		Scene scene = connexion.getScene();
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/views/Connexion.fxml"));
			scene.setRoot(root);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void profilVoyageur() {
		Scene scene = profil.getScene();
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("views/ProfilVoyageur.fxml"));
			scene.setRoot(root);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void onMenuButtonProfilClicked(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/ProfilVoyageur.fxml"));
		Parent root = (Parent) loader.load();
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		ProfilVoyageurController secController = loader.getController();
		secController.idUtilisateur = idUtilisateurConnecte;
		secController.miseEnPlaceProfil();
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