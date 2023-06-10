package com.calendly.calendly.Controller;
import com.calendly.calendly.Model.GestoreDB;
import com.calendly.calendly.SceneHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

public class LoginController {
    @FXML
    private AnchorPane ancorPane;
    @FXML
    private Pane pane;
    @FXML
    private Label loginLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button accediButton;



    //Funzione dedicata all'accesso dell'utente ~ Marco
    @FXML
    void accedi(ActionEvent event) {
        SceneHandler.getInstance().launchDashboard();

        /*try {
            if(GestoreDB.getInstance().login(usernameField.getText(), passwordField.getText())){
                SceneHandler.getInstance().launchDashboard();
            }
        } catch (SQLException e) {}*/
    }


    @FXML
    void initialize() {
        usernameField.setOnKeyReleased(this::handleKeyReleasedUsernameField);
        passwordField.setOnKeyReleased(this::handleKeyReleasedPasswordField);
    }

    public void init() {
        configOBJS();
    }

    private void handleKeyReleasedUsernameField(KeyEvent event) {
        canBeAccediButtonVisible();
    }

    private void handleKeyReleasedPasswordField(KeyEvent event) {
        canBeAccediButtonVisible();
    }

    private void canBeAccediButtonVisible() {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty())
            accediButton.setDisable(true);
        else
            accediButton.setDisable(false);
    }


    private void configPane() {
        callPaneResizeRelocate();
        ancorPane.layoutBoundsProperty().addListener((observable -> {
            callPaneResizeRelocate();
        }));
    }

    private void callPaneResizeRelocate() {
        double width  = pane.getWidth();
        double height = pane.getHeight();

        double x = ancorPane.getWidth()/2 - width/2;
        double y = ancorPane.getHeight()/2 - height/2;

        pane.resizeRelocate(x, y, width, height);
    }

    private void configOBJS() {

        Platform.runLater(() -> {
            configPane();
        });


    }

}
