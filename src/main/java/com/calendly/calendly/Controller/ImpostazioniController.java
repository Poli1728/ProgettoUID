package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.GestoreDB;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class ImpostazioniController implements Initializable {

    @FXML
    private Slider slideBar;

    @FXML
    private ComboBox<String> temiComboBox;

    @FXML
    void esci(ActionEvent event) {
        SceneHandler.getInstance().launchLogin();
    }

    @FXML
    void scegliTema(ActionEvent event) {
        if (temiComboBox.getSelectionModel().isSelected(0)){
            SceneHandler.getInstance().setTheme(Settings.theme.DARK);
        }else if (temiComboBox.getSelectionModel().isSelected(1)){
            SceneHandler.getInstance().setTheme(Settings.theme.LIGHT);
        }
    }

    @FXML
    void attivaDislessia(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        temiComboBox.getItems().add("Scuro");
        temiComboBox.getItems().add("Chiaro");

    }
}
