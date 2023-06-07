package com.calendly.calendly.Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestoreStatistiche {

    private static final GestoreStatistiche instance = new GestoreStatistiche();

    public static GestoreStatistiche getInstance() {
        return instance;
    }
    private GestoreStatistiche(){}
    public enum Periodi{Settimanale, Mensile, Annuale}
    public ArrayList<Statistiche> statistiche( Periodi p ,int mese, int anno) throws SQLException{
        ArrayList<Statistiche> info = new ArrayList<Statistiche>();
        switch (p){
            case Settimanale -> {
                for(int i = 6; i>-1; i--){
                    String s = GestoreData.getInstance().generaDataSottratta(i);
                    info.add(new Statistiche(s, GestoreDB.getInstance().numeroApp(s)));
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
                    String data = GestoreData.getInstance().generaDataMese(i, m, anno);
                    info.add(new Statistiche(data.substring(0,data.length()-5), GestoreDB.getInstance().numeroApp(data)));
                }
            }
            case Annuale -> {
                for(int i = 1; i<=12; i++){
                    String data = GestoreData.getInstance().generaDataAnno(i, anno);
                    info.add(new Statistiche(data.substring(2), GestoreDB.getInstance().numeroApp(data)));
                }
            }
        }
        return info;
    }

}
