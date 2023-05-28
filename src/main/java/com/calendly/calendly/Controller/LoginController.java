package com.calendly.calendly.Controller;
import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
public class LoginController {
    @FXML
    private PasswordField PasswordField;

    @FXML
    private TextField UsernameField;

    @FXML
    private Button accediButton;

    //Funzione dedicata all'accesso dell'utente  ~ Marco
    @FXML
    void Accedi(ActionEvent event) {
        //Questa è solo come prova per l'accesso, qui si dovrebbe andare a cercare all'interno del database ~ Marco
        if (UsernameField.getText().equals("Marco") && PasswordField.getText().equals("Marco")){
            SceneHandler.getInstance().launchHome();
        }
    }

}
