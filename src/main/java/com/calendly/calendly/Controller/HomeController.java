package com.calendly.calendly.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class HomeController {
    @FXML
    private Button ProfiloButton; //Inutile, mi sono dimenticato di toglierlo ~ Marco

    @FXML
    private Pane ViewPane;
    // Pane dove carichiamo le varie view per la dashboard ecc ecc ~ Marco

    // Le funzioni qui sotto fanno tutte la stessa cosa, quindi vedrò di fare una sola funzione ~ Marco
    @FXML
    void ApriAppuntamenti(ActionEvent event) {

    }

    @FXML
    void ApriDashboard(ActionEvent event) {

    }

    @FXML
    void ApriEventi(ActionEvent event) {

    }

    @FXML
    void ApriImpostazioni(ActionEvent event) {

    }

    @FXML
    void ApriProfili(ActionEvent event) {

    }

    @FXML
    void ApriProfilo(ActionEvent event) {

    }

    @FXML
    void ApriStatistiche(ActionEvent event) {

    }

    @FXML
    void Esci(ActionEvent event) {
        System.exit(0);
    }

}
