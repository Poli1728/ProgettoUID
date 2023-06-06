package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.GestoreStatistiche;
import com.calendly.calendly.Model.Statistiche;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StatisticheController implements Initializable {
    enum Periodi{Settimanale, Mensile, Annuale}

    private void appuntamenti(Periodi p,StringBuilder giorno, StringBuilder mese, StringBuilder anno){
        XYChart.Series<String, Number> appuntamenti = new XYChart.Series<String, Number>();
        appuntamenti.setName("Appuntamenti");
        ArrayList<Statistiche> lista = null;
        switch(p){
            case Settimanale -> {
                String data = giorno.toString()+"/"+mese.toString()+"/"+anno.toString();
                lista = GestoreStatistiche.getInstance().statisticheSettimanali(data);
                for (Statistiche i : lista) {
                    appuntamenti.getData().add(new XYChart.Data<String, Number>(i.getData(), i.getNumeroApp()));
                }
                chartSettimanale.getData().add(appuntamenti);
            }
            case Mensile -> {
                try {
                    lista = GestoreStatistiche.getInstance().statisticheMensili(Integer.parseInt(mese.toString()),Integer.parseInt(anno.toString()));
                    for(Statistiche i : lista){
                        appuntamenti.getData().add(new XYChart.Data<String, Number>(i.getData(), i.getNumeroApp()));
                    }
                } catch (SQLException e) {}
                chartMensile.getData().add(appuntamenti);
            }
            case Annuale -> {
                try {
                    lista = GestoreStatistiche.getInstance().statisticheAnnuali(Integer.parseInt(anno.toString()));
                    for(Statistiche i : lista){
                        appuntamenti.getData().add(new XYChart.Data<String, Number>(i.getData(), i.getNumeroApp()));
                    }
                } catch (SQLException e) {}
                chartAnnuale.getData().add(appuntamenti);
            }
        }
    }

    @FXML
    private BarChart<String, Number> chartAnnuale;

    @FXML
    private BarChart<String, Number> chartMensile;

    @FXML
    private BarChart<String, Number> chartSettimanale;

    @FXML
    void creaPdf(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String data = String.valueOf(java.time.LocalDateTime.now());
        StringBuilder anno = new StringBuilder();
        StringBuilder mese = new StringBuilder();
        StringBuilder giorno = new StringBuilder();
        for(int i = 0; i<data.length();i++){
            switch (i){
                case 0,1,2,3 -> anno.append(data.charAt(i));
                case 5,6 -> mese.append(data.charAt(i));
                case 8,9 -> giorno.append(data.charAt(i));
            }
        }
        appuntamenti(Periodi.Settimanale,giorno, mese, anno);
        appuntamenti(Periodi.Mensile,giorno, mese, anno);
        appuntamenti(Periodi.Annuale,giorno, mese, anno);
    }
}
