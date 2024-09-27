package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AccueilController implements Initializable{
	@FXML private Pane contact;
	@FXML private TextField txtRecherche;
	@FXML private DatePicker debut_sejour;
	@FXML private DatePicker fin_sejour;
	@FXML private Text txtWarning;
	@FXML private Button proposerVoyage, connexion;

	private ArrayList<Voyage> listAllVoyages;
	private ArrayList<Voyage> listVoyagesResults;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		contact.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		
		System.out.println("dans initialize ");
		
		// get all voyages
		listAllVoyages = this.getAllVoyages();
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
		System.out.println("dans getAllVoyages ");
		String fileName = "src/application/assets/voyages5.csv";

		ArrayList<Voyage> voyages = null;
		try {
			voyages = (ArrayList<Voyage>) new CsvToBeanBuilder(new FileReader(fileName)).withSeparator(';')
					.withType(Voyage.class)
					.build()
					.parse();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        for (Voyage v: voyages) {System.out.println(v.getVille() + " : " + v.getContreparties());}
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
	
	public void proposerVoyage() {
		Scene scene = proposerVoyage.getScene();
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("views/DetailSejourHote.fxml"));
			scene.setRoot(root);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connexion() {
		Scene scene = connexion.getScene();
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("views/Connexion.fxml"));
			scene.setRoot(root);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}