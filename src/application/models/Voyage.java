package application.models;

import com.opencsv.bean.CsvBindByPosition;

import java.time.chrono.ChronoLocalDate;

public class Voyage {

    @CsvBindByPosition(position = 0)
    private String ville;

    @CsvBindByPosition(position = 1)
    private String heure;

    @CsvBindByPosition(position = 2)
    private String type;

    @CsvBindByPosition(position = 3)
    private String contreparties;

    @CsvBindByPosition(position = 4)
    private String contraintes;

    @CsvBindByPosition(position = 5)
    private String services;

    @CsvBindByPosition(position = 6)
    private String transfert;

    @CsvBindByPosition(position = 7)
    private String restauration;

    @CsvBindByPosition(position = 8)
    private String dateArrivee;

    @CsvBindByPosition(position = 9)
    private String dateDepart;
    
    @CsvBindByPosition(position = 11)
    private int idHote;
    
    @CsvBindByPosition(position = 13)
    private int idVoyage;
    
    //  getters, setters, toString

    public String getIdHote() {
        return heure;
    }
    
    public String getVille() {
        return ville;
    }

    public String getHeure() {
        return heure;
    }

    public String getType() {
        return type;
    }

    public String getContreparties() {
        return contreparties;
    }

    public String getContraintes() {
        return contraintes;
    }

    public String getServices() {
        return services;
    }

    public String getTransfert() {
        return transfert;
    }

    public String getRestauration() {
        return restauration;
    }

    public String getDateArrivee() {
        return dateArrivee;
    }

    public String getDateDepart() {
        return dateDepart;
    }
    
    public int getIdHote1() {
        return idHote;
    }
    
    public int getIdVoyage() {
        return idVoyage;
    }
}
