package application.controllers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProfilVoyageurController {
	private Stage stage;
	private Scene scene;
	
	int idUtilisateur = 36;
	
	@FXML Text textPrenomNom;
	@FXML Text textPays;
	@FXML Text textVille;
	@FXML Text textAPropos;
	
	@FXML Text textIdUtilisateur;
	@FXML TextArea textAreaAPropos;
	@FXML TextField textFieldPays;
	@FXML TextField textFieldVille;
	@FXML TextField textFieldNom;
	@FXML TextField textFieldPrenom;
	@FXML CheckBox checkBoxHote;
	
	
	public void switchToModifierProfil(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/ModificationProfilVoyageur.fxml"));
		Parent root = (Parent) loader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root,1280,720);
		stage.setScene(scene);
		stage.show();
		
		ProfilVoyageurController secController = loader.getController();
		secController.idUtilisateur = idUtilisateur;
		secController.miseEnPlaceModifierProfil();
	}
	
	// Methode � appeler � l'appuis sur le bouton profil
	public void miseEnPlaceProfil() {
		try {
			// Lit le fichier profil.csv
	        FileReader filereader = new FileReader("src/application/assets/profil.csv");
	        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
	        CSVReader csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();
	        String[] nextRecord = null;
	        
	        // Met en place les informations du profil de l'utilisateur en fonction de idUtilisateur
	        int i = 0;
	        while (i != idUtilisateur) {
	        	nextRecord = csvReader.readNext();
	        	i++;
	        }
	       
	        textPrenomNom.setText(nextRecord[2] + " " + nextRecord[1]);
	        textPays.setText(nextRecord[4]);
	    	textVille.setText(nextRecord[3]);
	    	textAPropos.setText(nextRecord[6]);
	    	textAPropos.setWrappingWidth(350);
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void miseEnPlaceModifierProfil() {
		try {
			
	        FileReader filereader = new FileReader("src/application/assets/profil.csv");
	        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
	        CSVReader csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();
	        String[] nextRecord = null;
	        int i = 0;
	        while (i != idUtilisateur) {
	        	nextRecord = csvReader.readNext();
	        	i++;
	        }
	        textIdUtilisateur.setText(nextRecord[0]);
	        textFieldNom.setText(nextRecord[1]);
	        textFieldPrenom.setText(nextRecord[2]);
	        textFieldVille.setText(nextRecord[3]);
	        textFieldPays.setText(nextRecord[4]);
	        if(nextRecord[5].equals("O")) checkBoxHote.setSelected(true);
	        else checkBoxHote.setSelected(false);
	    	textAreaAPropos.setText(nextRecord[6]);
	    	
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void onValiderModificationClicked(ActionEvent event) throws IOException, CsvValidationException {
		
		// Lis le fichier csv
		FileReader filereader = new FileReader("src/application/assets/profil.csv");
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();
        String[] nextRecord = null;
        
        // Reecrit le fichier csv avec les informations modifiees
        int i = 0;
        ArrayList<String[]> profils = new ArrayList<String[]>();
        while (i+1 < idUtilisateur) {
        	nextRecord = csvReader.readNext();
        	profils.add(nextRecord);
        	i++;
        }
        String hote = "N";
        if(checkBoxHote.isSelected())
         hote = "O";
        String[] profilModifie = {textIdUtilisateur.getText(), textFieldNom.getText(), textFieldPrenom.getText()
        						 , textFieldVille.getText(), textFieldPays.getText(), hote, textAreaAPropos.getText().replace("\n", " ")};
        profils.add(profilModifie);
        i++;
        while ((nextRecord = csvReader.readNext()) != null) {
        	if(i != idUtilisateur)
        		profils.add(nextRecord);
        	i++;
        }
        
        FileWriter outputfile = new FileWriter("src/application/assets/profil.csv");
        
        CSVWriter writer = new CSVWriter(outputfile, ';',
                                         CSVWriter.NO_QUOTE_CHARACTER,
                                         CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                         CSVWriter.DEFAULT_LINE_END);
  
        writer.writeAll(profils);
  
        writer.close();
        
        //Change de fenetre
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/ProfilVoyageur.fxml"));
		Parent root = (Parent) loader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		ProfilVoyageurController secController = loader.getController();
		secController.idUtilisateur = idUtilisateur;
		secController.miseEnPlaceProfil();
	}
	
}
