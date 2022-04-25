package application.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class DetailSejourHoteController {
	
	@FXML
	private Button btnValiderSejourHote;
	@FXML
	private Button btnConcept;
	@FXML 
	private TextField localisation;
	@FXML
	private TextField dureeQuotidienne, contrainte, nbVoyageur;
	
	@FXML 
	private DatePicker periodeDeb, periodeFin;
	@FXML 
	private RadioButton cabane, chambre, tente, transfertOui, transfertNon;
	@FXML 
	private RadioButton jardinage, bricolage, babysitting, coursAnglais, promenerChien;
	@FXML 
	private RadioButton petitDej, demiPension, pensionComplete;
	@FXML 
	private RadioButton baladeCheval, blanchissage, piscine, wifi, parking;
	
	private String localisation1, periodeDeb1, periodeFin1;
	
	private String typeLogement, transfert;
	private String service="";
	private String dureeQuotidienne1="";
	private String contrainte1 ="";
	private String nbVoyageur1="";
	private String restauration="";
	private String contrepartie="";

	
	public void validerSejourHote() {
		Scene scene = btnValiderSejourHote.getScene();
		try {
			
			//on r�cup�re les valeurs des champs text saisis
			localisation1 = localisation.getText();
			
			if (!(periodeDeb.getValue() == null)) {
				periodeDeb1 = periodeDeb.getValue().toString();
				periodeDeb1 = periodeDeb1.substring(8,10) + "/"+
				              periodeDeb1.substring(5,7) + "/" +
				              periodeDeb1.substring(0,4);
			}
			else {
				periodeDeb1="";
			}
			
			if (!(periodeFin.getValue() == null)) {
				periodeFin1 = periodeFin.getValue().toString();
				periodeFin1 = periodeFin1.substring(8,10) + "/"+
						periodeFin1.substring(5,7) + "/" +
						periodeFin1.substring(0,4);
			}
			else {
				periodeFin1="";
			}
			
			//on r�cup�re les valeurs des radiobutton type logement saisis
			if (cabane.isSelected()) {
				typeLogement="Cabane";
			}
			else if (chambre.isSelected()) {
				typeLogement="Chambre";
			}
			else if (tente.isSelected()) {
				typeLogement="Tente";
			}
			
			//on r�cup�re les valeurs des radiobutton services saisis
			if (jardinage.isSelected()) {
				service="Jardinage";
			}
			if (babysitting.isSelected()) {
				if(!service.equals("")) {
					service=service+", Babysitting";
				}
				else {
					service="Babysitting";
				}
			}
			if (bricolage.isSelected()) {
				if(!service.equals("")) {
					service=service+", Bricolage";
				}
				else {
					service="Bricolage";
				}
			}
			if (coursAnglais.isSelected()) {
				if(!service.equals("")) {
					service=service+", Cours anglais";
				}
				else {
					service="Cours anglais";
				}
			}
			if (promenerChien.isSelected()) {
				if(!service.equals("")) {
					service=service+", Promener chiens";
				}
				else {
					service="Promener chiens";
				}
			}
			
			//on r�cup�re la valeur dur�e quotidienne saisie
			if (!dureeQuotidienne.getText().equals("")) {
				dureeQuotidienne1=dureeQuotidienne.getText();
			}
			
			//on r�cup�re la valeur contrainte saisie
			if (!contrainte.getText().equals("")) {
				contrainte1=contrainte.getText();
			}
			
			//on r�cup�re la valeur nbVoyageur saisie
			if (!nbVoyageur.getText().equals("")) {
				nbVoyageur1=nbVoyageur.getText();
			}
			
			//on r�cup�re les valeurs des radiobutton restauration saisis
			if (petitDej.isSelected()) {
				restauration="Petit d�jeuner";
			}
			if (demiPension.isSelected()) {
				if(!restauration.equals("")) {
					restauration=restauration+", Demi pension";
				}
				else {
					restauration="Demi pension";
				}
			}
			if (pensionComplete.isSelected()) {
				if(!restauration.equals("")) {
					restauration=restauration+", Pension compl�te";
				}
				else {
					restauration="Pension compl�te";
				}
			}
			
			//on r�cup�re les valeurs des radiobutton contreparties saisis
			if (baladeCheval.isSelected()) {
				contrepartie="Balade � cheval";
			}
			if (blanchissage.isSelected()) {
				if(!contrepartie.equals("")) {
					contrepartie=contrepartie+", Blanchissage";
				}
				else {
					contrepartie="Blanchissage";
				}
			}
			if (piscine.isSelected()) {
				if(!contrepartie.equals("")) {
					contrepartie=contrepartie+", Piscine";
				}
				else {
					contrepartie="Piscine";
				}
			}
			if (wifi.isSelected()) {
				if(!contrepartie.equals("")) {
					contrepartie=contrepartie+", Wifi";
				}
				else {
					contrepartie="Wifi";
				}
			}
			if (parking.isSelected()) {
				if(!contrepartie.equals("")) {
					contrepartie=contrepartie+", Parking";
				}
				else {
					contrepartie="Parking";
				}
			}
			
			//on r�cup�re les valeurs des radiobutton transfert saisis
			if (transfertOui.isSelected()) {
				transfert="Avec transfert";
			}
			else if (transfertNon.isSelected()) {
				transfert="Sans transfert";
			}
			
			System.out.println(localisation1);
			System.out.println(periodeDeb1);
			System.out.println(periodeFin1);
			System.out.println(typeLogement);
			System.out.println(service);
			System.out.println(dureeQuotidienne1);
			System.out.println(contrainte1);
			System.out.println(nbVoyageur1);
			System.out.println(restauration);
			System.out.println(contrepartie);
			System.out.println(transfert);

			String tmpIdVoyage ="10000";
			String tmpIdHote ="70";
			String tmpIdVoyageur="0";
			String newVoyage =tmpIdVoyage + ";" + tmpIdHote + ";" + nbVoyageur1 + ";" + localisation1 + ";" + dureeQuotidienne1 + ";"+typeLogement+ ";"+service+ ";"
			+contrainte1+ ";"+contrepartie + ";"+transfert+ ";"+restauration+ ";"+periodeDeb1 + ";"+periodeFin1+ ";"+tmpIdVoyageur + "\n";
			
			System.out.println("newVoyage="+newVoyage);
			
				//PrintStream l_out =(new FileOutputStream("src/application/assets/voyages10.csv"),true); 
				FileWriter fw = new FileWriter("src/application/assets/voyages10.csv",true); 
			
				fw.write(newVoyage); 
	 
				fw.flush(); 
				fw.close(); 
				fw=null;
								
				//AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../views/ListeSejourHoteBarreRecherche.fxml"));
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../views/search-view.fxml"));
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
}