package com.calendly.calendly.View;

import com.calendly.calendly.Controller.ImpostazioniController;
import com.calendly.calendly.Model.GestoreDB;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

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
    }

    private String tema;
    private int notifica;
    private int contatore;
    private int sizeTxt;
    private int sizeLabel;
    private String font;

    private MyInfo(){}

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

    public void setFont(boolean scelta) throws IOException {
        if (scelta){
            font = "Dyslexie";
        }else{
            font = "Quicksand Medium";
        }
    }

    public String getFont(){return this.font;}

    public InputStream getFontDyslexia() throws IOException {
        return Objects.requireNonNull(ImpostazioniController.class.getClassLoader().getResource("DyslexieRegular.ttf")).openStream();
    }

    public String getFontQuicksand(){
        return "Quicksand Medium";
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
