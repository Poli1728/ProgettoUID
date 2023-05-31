package com.calendly.calendly.Controller;

import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ImpostazioniController {

    @FXML
    private ComboBox<?> temiComboBox;

    @FXML
    void cambiaPassword(ActionEvent event) {

    }

    @FXML
    void esci(ActionEvent event) {
        SceneHandler.getInstance().launchLogin();
    }

    @FXML
    void mostraInformazioni(ActionEvent event) {

    }

}
