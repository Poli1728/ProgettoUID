package com.calendly.calendly.Controller;

import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

    public void init(Stage stage) {
        configPane(stage);
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


    private void configPane(Stage stage) {
        if (pane.getWidth() == 0 && pane.getHeight() == 0) {
            AnchorPane.setLeftAnchor(pane, (ancorPane.getWidth() - pane.getPrefWidth()) / 2);
            AnchorPane.setTopAnchor(pane, (ancorPane.getHeight() - pane.getPrefHeight()) / 2);
        }

        ancorPane.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
                AnchorPane.setLeftAnchor(pane, (ancorPane.getWidth() - pane.getWidth())/2);
                AnchorPane.setTopAnchor(pane, (ancorPane.getHeight() - pane.getHeight())/2);
        });
    }


}
