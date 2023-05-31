package com.calendly.calendly.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;

public class StatisticheController {

    @FXML
    private BarChart<?, ?> chartAnnuale;

    @FXML
    private BarChart<?, ?> chartMensile;

    @FXML
    private BarChart<?, ?> chartSettimanale;

    @FXML
    void creaPdf(ActionEvent event) {

    }

}
