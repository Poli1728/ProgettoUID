package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.GestoreDB;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class ImpostazioniController implements Initializable {

    @FXML
    private PasswordField username;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<String> temiComboBox;

    @FXML
    void cambiaPassword(ActionEvent event) {
        //GestoreDB.getInstance().cambiaPassword(password.getText(),)
    }

    @FXML
    void cambiaUsername(ActionEvent event) {

    }

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
