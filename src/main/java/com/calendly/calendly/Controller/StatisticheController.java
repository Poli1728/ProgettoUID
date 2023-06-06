package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.GestoreStatistiche;
import com.calendly.calendly.Model.Statistiche;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import java.text.SimpleDateFormat;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class StatisticheController implements Initializable {

    private void appuntamentiS(String data){
        XYChart.Series<String, Number> appuntamentiSettimanali = new XYChart.Series<String, Number>();
        appuntamentiSettimanali.setName("Appuntamenti");
        ArrayList<Statistiche> settimanali = GestoreStatistiche.getInstance().statisticheSettimanali(data);
        /*for(Statistiche i : settimanali){
            appuntamentiSettimanali.getData().add(new XYChart.Data<String, Number>(i.getData(), i.getNumeroApp()));
        }*/
        chartSettimanale.getData().add(appuntamentiSettimanali);
    }

    private void appuntamentiM(StringBuilder mese, StringBuilder anno){
        XYChart.Series<String, Number> appuntamentiMensili = new XYChart.Series<String, Number>();
        appuntamentiMensili.setName("Appuntamenti");
        ArrayList<Statistiche> mensile = null;
        try {
            mensile = GestoreStatistiche.getInstance().statisticheMensili(Integer.parseInt(mese.toString()), Integer.parseInt(anno.toString()));
            for(Statistiche i : mensile){
                appuntamentiMensili.getData().add(new XYChart.Data<String, Number>(i.getData(), i.getNumeroApp()));
            }
        } catch (SQLException e) {}
        chartMensile.getData().add(appuntamentiMensili);
    }

    private void appuntamentiA (StringBuilder anno){
        XYChart.Series<String, Number> appuntamentiAnnuali = new XYChart.Series<String, Number>();
        appuntamentiAnnuali.setName("Appuntamenti");
        ArrayList<Statistiche> annuali = null;
        try {
            annuali = GestoreStatistiche.getInstance().statisticheAnnuali(Integer.parseInt(anno.toString()));
            for(Statistiche i : annuali){
                appuntamentiAnnuali.getData().add(new XYChart.Data<String, Number>(i.getData(), i.getNumeroApp()));
            }
        } catch (SQLException e) {}
        chartAnnuale.getData().add(appuntamentiAnnuali);
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
        appuntamentiS(data);
        appuntamentiM(mese, anno);
        appuntamentiA(anno);
    }
}
