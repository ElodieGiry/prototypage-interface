package application.controllers;

import application.Main;
import application.models.ConnexionParam;
import application.models.Voyage;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.opencsv.bean.CsvToBeanBuilder;

public class SearchController {

    @FXML
    private TextField txtRecherche2;
    @FXML
    private DatePicker debut_sejour2;
    @FXML
    private DatePicker fin_sejour2;
    @FXML
    private Text txtWarning2;

    @FXML
    private VBox tripListView = null;
    @FXML
    private ImageView imgMap;
    @FXML
    private ScrollPane scrollPaneResults;
    @FXML
    private Text txtResults;
    private boolean isAddingCards = false;
    private int startIndexToAddCards;
    
    @FXML private Text idHoteConnecte,typeConnexion;

    private ArrayList<Voyage> listAllVoyages;
    private ArrayList<Voyage> listVoyagesResults2;
    int idUtilisateurConnecte = 1;
    public void init() {
        setMap();
        displaySearchResults();

        // mise à jour liste de voyage automatique sur modification de caractère
        txtRecherche2.textProperty().addListener((observable, oldValue, newValue) -> {
            searchVoyages();
        });

        // init datepickers
        LocalDate today = LocalDate.now();
        
        updateDatePicker(debut_sejour2, today);
        updateDatePicker(fin_sejour2, today);

        debut_sejour2.valueProperty().addListener((ov, oldValue, newValue) -> {
            updateDatePicker(fin_sejour2, debut_sejour2.getValue());
            if (fin_sejour2.getValue().isBefore(debut_sejour2.getValue())) {
                fin_sejour2.setValue(debut_sejour2.getValue());
            }
            searchVoyages();
        });

        fin_sejour2.valueProperty().addListener((ov, oldValue, newValue) -> {
            searchVoyages();
        });


        // todo: initialize map effects
    }
    
    @FXML
    public void initialize() {
    
    	ConnexionParam param = ConnexionParam.getInstance();
        idHoteConnecte.setText(param.getId());
        typeConnexion.setText(param.getTypeConnexion());
        
        System.out.println("init :  identifiant = "+param.getId());
		System.out.println("typeConnexion = "+param.getTypeConnexion());
		//Afficher tous les voyages (sans filtre) du voyageur/hote
		
		// get all voyages (csv)
		listAllVoyages = this.getAllVoyages();
		
		listVoyagesResults2 = searchVoyagesSansFiltre(); //avec recherche id hote
		
		String nbSejourString = String.valueOf(listVoyagesResults2.size());
		
		//nbSejour.setText(nbSejourString);
		
		System.out.println("listVoyagesResults2.size() = "+listVoyagesResults2.size());
		addCardToTripListView(listVoyagesResults2.size(), 0);
		/*
		listVoyagesResults2 = searchVoyagesResults2();
        setListVoyagesResults(listVoyagesResults2);
        displaySearchResults();
        */
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
    
public ArrayList<Voyage> searchVoyagesSansFiltre(){
    	
    	ArrayList<Voyage> results = new ArrayList<Voyage>();
    	
    	System.out.println("size voyage = "+listAllVoyages.size());

        for (Voyage v: listAllVoyages){
        	System.out.println("v.getIdHote() = "+v.getIdHote() + ", idHoteConnecte = "+idHoteConnecte.getText());
        	if(v.getIdHote().equals(idHoteConnecte.getText())) {
        		System.out.println("+1");
        		results.add(v);
        	}
        	else {
        		System.out.println("ko");
        	}
        }
        System.out.println(results.size());
        return results;
    }

    @FXML
    protected void onSearchButtonClick2(ActionEvent event) throws IOException {
        searchVoyages();
    }

    public void searchVoyages() {
        if (txtRecherche2.getText().length() > 1) {
            listVoyagesResults2 = searchVoyagesResults2();
            setListVoyagesResults(listVoyagesResults2);
            displaySearchResults();
        } else {
            displayWarning();
        }
    }

    public void displaySearchResults() {
        scrollPaneResults.setVvalue(0);
        displayResultsQty();
        initializeTripListView();
    }

    public void displayResultsQty() {
        // gère le nombre de résultats
        if (listVoyagesResults2.size() == 0) txtResults.setText("Aucun voyage...");
        else txtResults.setText(listVoyagesResults2.size() + " voyages peuvent vous satisfaire");
        imgMap.setVisible(listVoyagesResults2.size() != 0);
        scrollPaneResults.setVisible(listVoyagesResults2.size() != 0);
    }

    public void initializeTripListView() {
        startIndexToAddCards = 0;
        tripListView.getChildren().clear();
        if (listVoyagesResults2.size() > 0) {
            int numberOfCardsToAdd = 10;
            if (listVoyagesResults2.size() < 10) numberOfCardsToAdd = listVoyagesResults2.size();
            addCardToTripListView(numberOfCardsToAdd, startIndexToAddCards);
        }
    }

    public void addCardToTripListView(int numberOfCards, int startIndex) {
        Node[] cardsToAdd = new Node[numberOfCards];
        // initialize graphics
        for (int i = 0; i < cardsToAdd.length; i++) {
            try {
                final int j = i;
                cardsToAdd[i] = FXMLLoader.load(Main.class.getResource("views/trip-card-view.fxml"));
                cardsToAdd[i].setStyle("-fx-border-color: lightgrey");

                // bind data
                ImageView iv = (ImageView) cardsToAdd[i].lookup("#image");
                Label titre = (Label) cardsToAdd[i].lookup("#titre");
                Label contrepartie = (Label) cardsToAdd[i].lookup("#contrepartie");
                Label logement = (Label) cardsToAdd[i].lookup("#logement");
                Label heures = (Label) cardsToAdd[i].lookup("#heures");
                Label indexForTest = (Label) cardsToAdd[i].lookup("#indexForTest");

                File file = new File("src/application/assets/images/ville/" + listVoyagesResults2.get(startIndex + i).getVille() + ".png");
                Image image = new Image(file.toURI().toString());
                iv.setImage(image);
                titre.setText(listVoyagesResults2.get(startIndex + i).getContreparties() + " � " + listVoyagesResults2.get(startIndex + i).getVille());
                contrepartie.setText(listVoyagesResults2.get(startIndex + i).getContreparties());
                logement.setText(listVoyagesResults2.get(startIndex + i).getType());
                heures.setText(listVoyagesResults2.get(startIndex + i).getHeure() + " h/j");
                indexForTest.setText("(" + (startIndex + i + 1) + ")");

                // styles
//                cardsToAdd[i].setStyle("");

                // add some effect
                cardsToAdd[i].setOnMouseEntered(event -> {
                    cardsToAdd[j].setStyle("-fx-border-color: grey;" +
                            "-fx-background-color: white;" +
                            "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
                });
                cardsToAdd[i].setOnMouseExited(event -> {
                    cardsToAdd[j].setStyle("-fx-border-color: lightgrey");
                });

                // add onClickListener
                int voyageIndex = startIndex + i;
                cardsToAdd[i].setOnMouseClicked((new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        System.out.println("VoyageIndex: " + voyageIndex);
                        navigateToDetailVoyageView(listVoyagesResults2.get(voyageIndex), event);
                    }
                }));

                // add items
                tripListView.getChildren().add(cardsToAdd[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        startIndexToAddCards = startIndex + numberOfCards;
    }

    @FXML
    void onScrollFinished(ScrollEvent event) {
        if (!isAddingCards) {
            isAddingCards = true;
            if (scrollPaneResults.getVvalue() == 1 && tripListView.getChildren().size() < listVoyagesResults2.size()) {
                System.out.println("adding new cards");
                //define number of cards to add
                int numberOfCardsToAdd = 10;
                if (tripListView.getChildren().size() + 10 > listVoyagesResults2.size()) {
                    numberOfCardsToAdd = listVoyagesResults2.size() - tripListView.getChildren().size();
                }
                addCardToTripListView(numberOfCardsToAdd, startIndexToAddCards);
            }
            isAddingCards = false;
        }
    }

    public void setMap() {
        File file = new File("src/application/assets/map.png");
        Image image = new Image(file.toURI().toString());
        imgMap.setImage(image);
    }

    public void setListVoyagesResults(ArrayList<Voyage> listVoyagesResults2) {
        this.listVoyagesResults2 = listVoyagesResults2;
    }

    public void setListAllVoyages(ArrayList<Voyage> listAllVoyages) {
        this.listAllVoyages = listAllVoyages;
    }

    public void setTxtRecherche2(TextField txtDestination) {
        this.txtRecherche2.setText(txtDestination.getText());
    }

    public void setDebut_sejour2(DatePicker debut_sejour2) {
        this.debut_sejour2.setValue(debut_sejour2.getValue());
    }

    public void setFin_sejour2(DatePicker fin_sejour2) {
        this.fin_sejour2.setValue(fin_sejour2.getValue());
    }

    public ArrayList<Voyage> searchVoyagesResults2() {
        System.out.println(this.txtRecherche2.getText());
        if (debut_sejour2.getValue() != null) System.out.println(this.debut_sejour2.getValue());
        if (fin_sejour2.getValue() != null) System.out.println(this.fin_sejour2.getValue());

        // FOR DEMO: if input= "all", results if all 10000 voyages
        if (txtRecherche2.getText().equals("all")) {
            return this.listAllVoyages;
        }

        ArrayList<Voyage> results = new ArrayList<Voyage>();

        for (Voyage v : listAllVoyages) {
            if ((v.getVille().contains(txtRecherche2.getText()) || v.getContreparties().contains(txtRecherche2.getText()) || v.getType().contains(txtRecherche2.getText()))
                    && (debut_sejour2.getValue() == null || !debut_sejour2.getValue().isBefore(LocalDate.parse(v.getDateArrivee())))
                    && (fin_sejour2.getValue() == null || !fin_sejour2.getValue().isAfter(LocalDate.parse(v.getDateDepart())))) {
                results.add(v);
            }
        }
        System.out.println(results.size());
        return results;
    }

    public void displayWarning() {
        txtWarning2.setVisible(true);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ie) {
                }
                txtWarning2.setVisible(false);
            }
        }).start();
    }

    public void updateDatePicker(DatePicker dp, LocalDate startLocalDate) {
        // restriction sur date picker
        dp.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(startLocalDate) < 0);
            }
        });
    }

    public void navigateToDetailVoyageView(Voyage v, MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/voyage-detail-view.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DetailVoyageController detailVoyageController = (DetailVoyageController) fxmlLoader.getController();
        detailVoyageController.setVoyage(v);
        detailVoyageController.init();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void goToAccueil(MouseEvent event) throws IOException {
        try {
            Parent Accueil = FXMLLoader.load(Main.class.getResource("views/Accueil.fxml"));
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
