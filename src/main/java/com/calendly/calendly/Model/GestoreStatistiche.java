package com.calendly.calendly.Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestoreStatistiche {

    private static final GestoreStatistiche instance = new GestoreStatistiche();

    public static GestoreStatistiche getInstance() {
        return instance;
    }
    private GestoreStatistiche(){}

    public ArrayList<Statistiche> statisticheSettimanali(String data){
        ArrayList<Statistiche> info = new ArrayList<Statistiche>();

        return info;
    }

    public ArrayList<Statistiche> statisticheMensili(int mese, int anno) throws SQLException {
        ArrayList<Statistiche> info = new ArrayList<Statistiche>();
        int n;
        switch(mese){
            case 1,3,5,7,8,10,12 -> n = 31;
            case 4,6,9,11 -> n = 30;
            case 2 -> n = 28;
            default -> n=0;
        }
        String m;
        if(mese<10){
            m = "0"+String.valueOf(mese);
        }else{
            m = String.valueOf(mese);
        }
        for(int i = 1; i<=n; i++){
            String data;
            if (i<10) {
                data = "0"+String.valueOf(i)+"/"+ m +"/"+ String.valueOf(anno) ;
            }else{
                data = String.valueOf(i)+"/"+ m +"/"+ String.valueOf(anno) ;
            }
            info.add(new Statistiche(data, GestoreDB.getInstance().numeroApp(data)));
        }
        return info;
    }

    public ArrayList<Statistiche> statisticheAnnuali(int anno) throws SQLException {
        ArrayList<Statistiche> info = new ArrayList<Statistiche>();
        for(int i = 1; i<=12; i++){
            String data;
            if (i<10) {
                 data = "%/0"+ String.valueOf(i) +"/"+ String.valueOf(anno) ;
            }else{
                data = "%/"+ String.valueOf(i) +"/"+ String.valueOf(anno) ;
            }
            info.add(new Statistiche(data.substring(2), GestoreDB.getInstance().numeroApp(data)));
        }
        return info;
    }

}
