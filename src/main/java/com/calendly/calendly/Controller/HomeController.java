package com.calendly.calendly.Controller;

import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomeController {
    @FXML
    private Pane ViewPane;
    // Pane dove carichiamo le varie view per la dashboard ecc ecc ~ Marco

    // Le funzioni qui sotto fanno tutte la stessa cosa, quindi vedrò di fare una sola funzione ~ Marco

    @FXML
    void apriAppuntamenti(ActionEvent event) throws IOException {
        ViewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Appuntamenti");
        ViewPane.getChildren().add(pane);
    }

    @FXML
    void apriDashboard(ActionEvent event) throws IOException {
        ViewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Dashboard");
        ViewPane.getChildren().add(pane);
    }

    @FXML
    void apriEventi(ActionEvent event) throws IOException {
        ViewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Eventi");
        ViewPane.getChildren().add(pane);
    }

    @FXML
    void apriImpostazioni(ActionEvent event) throws IOException {
        ViewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Impostazioni");
        ViewPane.getChildren().add(pane);
    }

    @FXML
    void apriProfili(ActionEvent event) throws IOException {
        ViewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Dipendenti");
        ViewPane.getChildren().add(pane);
    }

    @FXML
    void apriProfilo(ActionEvent event) throws IOException {
        ViewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Profilo");
        ViewPane.getChildren().add(pane);
    }

    @FXML
    void apriStatistiche(ActionEvent event) throws IOException {
        ViewPane.getChildren().clear();
        Pane pane = SceneHandler.getInstance().creaPane("fxml/Statistiche");
        ViewPane.getChildren().add(pane);
    }

    @FXML
    void esci(ActionEvent event) {
        ViewPane.getChildren().clear();
        SceneHandler.getInstance().launchLogin();
    }

}
