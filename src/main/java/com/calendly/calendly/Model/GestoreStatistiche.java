package com.calendly.calendly.Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestoreStatistiche {

    private static final GestoreStatistiche instance = new GestoreStatistiche();

    public static GestoreStatistiche getInstance() {
        return instance;
    }
    private GestoreStatistiche(){}
    private enum Periodi{Giornaliero,Settimanale, Mensile, Annuale}

    public Periodi getSettimanale(){
        return Periodi.Settimanale;
    }
    public Periodi getMensile(){
        return Periodi.Mensile;
    }
    public Periodi getAnnuale(){
        return Periodi.Annuale;
    }
    public Periodi getGiornaliero(){
        return Periodi.Giornaliero;
    }

    public ArrayList<Statistiche> statistiche( Periodi p ,int mese, int anno, String data) throws SQLException{
        ArrayList<Statistiche> info = new ArrayList<Statistiche>();
        switch (p){
            case Giornaliero -> {
                info.add(new Statistiche(data, GestoreDB.getInstance().conta(data, 0)));
            }
            case Settimanale -> {
                for(int i = 6; i>-1; i--){
                    String s = GestoreData.getInstance().generaDataSottratta(i);
                    info.add(new Statistiche(s, GestoreDB.getInstance().conta(s, 0)));
                }
            }
            case Mensile -> {
                int n;
                switch(mese){
                    case 1,3,5,7,8,10,12 -> n = 31;
                    case 4,6,9,11 -> n = 30;
                    case 2 -> n = 28;
                    default -> n=0;
                }
                String m = GestoreData.getInstance().generaMese(mese);
                for(int i = 1; i<=n; i++){
                    String dataGenerata = GestoreData.getInstance().generaDataMese(i, m, anno);
                    info.add(new Statistiche(dataGenerata.substring(0,dataGenerata.length()-5), GestoreDB.getInstance().conta(dataGenerata,0)));
                }
            }
            case Annuale -> {
                for(int i = 1; i<=12; i++){
                    String dataGenerata = GestoreData.getInstance().generaDataAnno(i, anno);
                    info.add(new Statistiche(dataGenerata.substring(2), GestoreDB.getInstance().conta(dataGenerata, 0)));
                }
            }
        }
        return info;
    }

}
