package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.GestoreData;
import com.calendly.calendly.Model.GestoreStatistiche;
import com.calendly.calendly.Model.Statistiche;
import com.calendly.calendly.View.MyFont;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StatisticheController{

    @FXML
    private Label labelStatistiche;

    @FXML
    private BarChart<String, Number> chartAnnuale;

    @FXML
    private BarChart<String, Number> chartMensile;

    @FXML
    private BarChart<String, Number> chartSettimanale;

    private void appuntamenti(String p,StringBuilder mese, StringBuilder anno) throws SQLException {
        XYChart.Series<String, Number> appuntamenti = new XYChart.Series<String, Number>();
        appuntamenti.setName("Appuntamenti");
        ArrayList<Statistiche> lista = null;
        int m = Integer.parseInt(mese.toString());
        int a = Integer.parseInt(anno.toString());
        switch(p){
            case "Settimanale" -> {
                lista = GestoreStatistiche.getInstance().statistiche(GestoreStatistiche.getInstance().getSettimanale(), m,a);
                for (Statistiche i : lista) {
                    appuntamenti.getData().add(new XYChart.Data<String, Number>(i.getData(), i.getNumeroApp()));
                }
                chartSettimanale.getData().add(appuntamenti);
            }
            case "Mensile" -> {
                try {
                    lista = GestoreStatistiche.getInstance().statistiche(GestoreStatistiche.getInstance().getMensile(),m,a);
                    for(Statistiche i : lista){
                        appuntamenti.getData().add(new XYChart.Data<String, Number>(i.getData(), i.getNumeroApp()));
                    }
                } catch (SQLException e) {}
                chartMensile.getData().add(appuntamenti);
            }
            case "Annuale" -> {
                try {
                    lista = GestoreStatistiche.getInstance().statistiche(GestoreStatistiche.getInstance().getAnnuale(),m,a);
                    for(Statistiche i : lista){
                        appuntamenti.getData().add(new XYChart.Data<String, Number>(i.getData(), i.getNumeroApp()));
                    }
                } catch (SQLException e) {}
                chartAnnuale.getData().add(appuntamenti);
            }
        }
    }

    @FXML
    public void initialize() {
        labelStatistiche.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        String data = String.valueOf(java.time.LocalDateTime.now());
        StringBuilder anno = GestoreData.getInstance().annoCorrente(data);
        StringBuilder mese = GestoreData.getInstance().meseCorrente(data);
        try {
            appuntamenti("Settimanale", mese, anno);
            appuntamenti("Mensile", mese, anno);
            appuntamenti("Annuale", mese, anno);
        } catch (SQLException e) {}
    }
}
