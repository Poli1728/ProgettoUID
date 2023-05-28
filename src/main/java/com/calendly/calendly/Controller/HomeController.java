package com.calendly.calendly.Controller;

import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomeController {
    @FXML
    private Button ProfiloButton; //Inutile, mi sono dimenticato di toglierlo ~ Marco

    @FXML
    private Pane ViewPane;
    // Pane dove carichiamo le varie view per la dashboard ecc ecc ~ Marco

    // Le funzioni qui sotto fanno tutte la stessa cosa, quindi vedrò di fare una sola funzione ~ Marco
    @FXML
    void ApriAppuntamenti(ActionEvent event) throws IOException {
        SceneHandler.getInstance().creaPane("fxml/Appuntamenti");
    }

    @FXML
    void ApriDashboard(ActionEvent event) throws IOException {
        SceneHandler.getInstance().creaPane("fxml/Dashboard");
    }

    @FXML
    void ApriEventi(ActionEvent event) throws IOException {
        SceneHandler.getInstance().creaPane("fxml/Eventi");
    }

    @FXML
    void ApriImpostazioni(ActionEvent event) throws IOException {
        SceneHandler.getInstance().creaPane("fxml/Impostazioni");
    }

    @FXML
    void ApriProfili(ActionEvent event) throws IOException {
        SceneHandler.getInstance().creaPane("fxml/Dipendenti");
    }

    @FXML
    void ApriProfilo(ActionEvent event) throws IOException {
        SceneHandler.getInstance().creaPane("fxml/Profilo");
    }

    @FXML
    void ApriStatistiche(ActionEvent event) throws IOException {
        SceneHandler.getInstance().creaPane("fxml/Statistiche");
    }

    @FXML
    void Esci(ActionEvent event) {
        System.exit(0);
    }

}
