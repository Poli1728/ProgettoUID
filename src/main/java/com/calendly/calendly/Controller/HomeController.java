package com.calendly.calendly.Controller;

import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomeController {
    @FXML
    private Pane viewPane;
    // Pane dove carichiamo le varie view per la dashboard ecc ecc ~ Marco

    // Le funzioni qui sotto fanno tutte la stessa cosa, quindi vedrò di fare una sola funzione ~ Marco

    @FXML
    void apriAppuntamenti(ActionEvent event) throws IOException {
        viewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Appuntamenti");
        viewPane.getChildren().add(pane);
    }

    @FXML
    void apriDashboard(ActionEvent event) throws IOException {
        viewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Dashboard");
        viewPane.getChildren().add(pane);
    }

    @FXML
    void apriEventi(ActionEvent event) throws IOException {
        viewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Eventi");
        viewPane.getChildren().add(pane);
    }

    @FXML
    void apriImpostazioni(ActionEvent event) throws IOException {
        viewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Impostazioni");
        viewPane.getChildren().add(pane);
    }

    @FXML
    void apriProfili(ActionEvent event) throws IOException {
        viewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Dipendenti");
        viewPane.getChildren().add(pane);
    }

    @FXML
    void apriProfilo(ActionEvent event) throws IOException {
        viewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Profilo");
        viewPane.getChildren().add(pane);
    }

    @FXML
    void apriStatistiche(ActionEvent event) throws IOException {
        viewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Statistiche");
        viewPane.getChildren().add(pane);
    }

    @FXML
    void esci(ActionEvent event) {
        viewPane.getChildren().clear();
        SceneHandler.getInstance().launchLogin();
    }

    @FXML
    void initialize() {

    }



}
