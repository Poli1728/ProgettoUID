package com.calendly.calendly.View;

import com.calendly.calendly.Model.GestoreDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class MyInfo {

    private static final MyInfo instance = new MyInfo();

    public static MyInfo getInstance() {
        return instance;
    }

    public void prendiDati() throws SQLException {
        ArrayList<String> template = GestoreDB.getInstance().leggiEntità(GestoreDB.getInstance().getTemplate());
        String [] info = template.get(0).split(";");
        sizeTxt = Integer.parseInt(info[2]);
        sizeLabel = Integer.parseInt(info[2])+14;
        font = info[1];
        tema = info[0];
        notifica = Integer.parseInt(info[3]);
        contatore = 0;
        System.out.println(info[3]);
    }

    private String tema;
    private int notifica;
    private int contatore;

    private MyInfo(){}

    private enum tipiFont{Quicksand, Verdana}

    public tipiFont getQuicksand(){
        return tipiFont.Quicksand;
    }

    public tipiFont getVerdana(){return tipiFont.Verdana;}

    private String font = "Quicksand Medium";

    public int getNotifica() {
        return notifica;
    }

    public void setNotifica(int notifica) {
        this.notifica = notifica;
    }

    public int getContatore() {
        return contatore;
    }

    public void setContatore(int contatore) {
        this.contatore = contatore;
    }

    private int sizeTxt = 14;
    private int sizeLabel = 28;

    public void setFont(tipiFont t){
        if (t.toString().equals("Quicksand")){
            font = t.toString() + " Medium";
        }else{
            font = t.toString();
        }
    }

    public String getFont(){
        return font;
    }

    public int getSizeTxt(){
        return sizeTxt;
    }
    public int getSizeLabel(){
        return sizeLabel;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}
