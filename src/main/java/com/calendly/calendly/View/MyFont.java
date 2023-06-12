package com.calendly.calendly.View;

import com.calendly.calendly.Model.GestoreDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class MyFont {

    private static final MyFont instance = new MyFont();

    public static MyFont getInstance() {
        return instance;
    }

    public void prendiDati() throws SQLException {
        ArrayList<String> template = GestoreDB.getInstance().leggiEntità(GestoreDB.getInstance().getTemplate());
        String [] info = template.get(0).split(";");
        sizeTxt = Integer.parseInt(info[2]);
        sizeLabel = Integer.parseInt(info[2])+14;
        font = info[1];
        tema = info[0];
    }

    private String tema = "DARK";

    private MyFont(){}

    private enum tipiFont{Quicksand, Cantarell, OpenDyslexic}

    public tipiFont getQuicksand(){
        return tipiFont.Quicksand;
    }

    public tipiFont getCantarell(){
        return tipiFont.Cantarell;
    }
    public tipiFont getDyslexic(){
        return tipiFont.OpenDyslexic;
    }

    private String font = "Quicksand Medium";

    private int sizeTxt = 14;
    private int sizeLabel = 28;

    public void setFont(tipiFont t){
        if (t.toString().equals("Quicksand")){
            font = t.toString() + " Medium";
        }else{
            font = t.toString()+"-Regular.otf";
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
