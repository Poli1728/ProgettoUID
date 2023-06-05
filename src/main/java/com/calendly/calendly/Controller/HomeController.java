package com.calendly.calendly.Controller;

import com.calendly.calendly.SceneHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomeController {
    @FXML
    private Pane viewPane;
    // Pane dove carichiamo le varie view per la dashboard ecc ecc ~ Marco

    private void avviaPane(String s) throws IOException {
        viewPane.getChildren().clear();
        FXMLLoader loader = SceneHandler.getInstance().creaPane(s);
        Pane pane = (Pane) loader.load();

        pane.setPrefSize(viewPane.getWidth(), viewPane.getHeight());

        viewPane.layoutBoundsProperty().addListener(obs -> {
            pane.setPrefSize(viewPane.getWidth(), viewPane.getHeight());
        });

        viewPane.getChildren().add(pane);
    }

    @FXML
    void apriAppuntamenti(ActionEvent event) throws IOException {
        avviaPane("fxml/Appuntamenti");
    }

    @FXML
    void apriDashboard(ActionEvent event) throws IOException {
        avviaPane("fxml/Dashboard");
    }

    @FXML
    void apriServizi(ActionEvent event) throws IOException {
        avviaPane("fxml/Servizi");
    }

    @FXML
    void apriImpostazioni(ActionEvent event) throws IOException {
        avviaPane("fxml/Impostazioni");
    }

    @FXML
    void apriDipendenti(ActionEvent event) throws IOException {
        avviaPane("fxml/Dipendenti");
    }

    @FXML
    void apriProfilo(ActionEvent event) throws IOException {
        avviaPane("fxml/Profilo");
    }

    @FXML
    void apriStatistiche(ActionEvent event) throws IOException {
        avviaPane("fxml/Statistiche");
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
