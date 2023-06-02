package com.calendly.calendly.Controller;

import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ImpostazioniController implements Initializable {

    @FXML
    private ComboBox<String> temiComboBox;

    @FXML
    void cambiaPassword(ActionEvent event) {
        String nuovaPassword = "";

    }

    @FXML
    void esci(ActionEvent event) {
        SceneHandler.getInstance().launchLogin();
    }

    @FXML
    void mostraInformazioni(ActionEvent event) {

    }
    @FXML
    void scegliTema(ActionEvent event) {
        if (temiComboBox.get){

        }
        SceneHandler.getInstance().setTheme();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        temiComboBox.getItems().add("Scuro");
        temiComboBox.getItems().add("Chiaro");
    }
}
