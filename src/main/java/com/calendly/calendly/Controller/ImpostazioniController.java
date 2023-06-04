package com.calendly.calendly.Controller;

import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ImpostazioniController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private TextField indirizzoField;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField numeroField;

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
    void modificaInformazioni(ActionEvent event) {
        if (!emailField.getText().equals("")){
            ProfiloController.setEmail(emailField.getText());
        }
        if (!nomeField.getText().equals("")){
            ProfiloController.setNome(nomeField.getText());
        }
        if (!indirizzoField.getText().equals("")){
            ProfiloController.setIndirizzo(indirizzoField.getText());
        }
        if (!numeroField.getText().equals("")){
            if (numeroField.getText().matches("[0-9]+")) {
                ProfiloController.setNumero(numeroField.getText());
            }
        }

    }
    @FXML
    void scegliTema(ActionEvent event) {
        if (temiComboBox.getSelectionModel().isSelected(0)){
            SceneHandler.getInstance().setTheme(Settings.theme.DARK);
        }else if (temiComboBox.getSelectionModel().isSelected(1)){
            SceneHandler.getInstance().setTheme(Settings.theme.LIGHT);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        temiComboBox.getItems().add("Scuro");
        temiComboBox.getItems().add("Chiaro");
    }
}
