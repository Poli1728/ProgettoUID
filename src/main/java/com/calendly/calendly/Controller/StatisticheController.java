package com.calendly.calendly.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class StatisticheController implements Initializable {

    @FXML
    private BarChart<?, ?> chartAnnuale;

    @FXML
    private BarChart<?, ?> chartMensile;

    @FXML
    private BarChart<String, Number> chartSettimanale;

    @FXML
    void creaPdf(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Number> appuntamenti = new XYChart.Series<String, Number>();
        appuntamenti.setName("Appuntamenti");
        Date data = new Date();
        System.out.println(data.toString());

        appuntamenti.getData().add(new XYChart.Data<String, Number>("2023", 10));
        chartSettimanale.getData().add(appuntamenti);

    }
}
