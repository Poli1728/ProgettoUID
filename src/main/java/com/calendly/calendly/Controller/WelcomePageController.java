package com.calendly.calendly.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class WelcomePageController {

    @FXML
    private Pane pane;

    @FXML
    private AnchorPane ancorPane;

    @FXML
    private Button continueButton;

    @FXML
    private StackPane stackPane;

    @FXML
    void actionContinueButton(ActionEvent event) {
        //TODO quando premuto, passa alla schermata di creazione dell'account proprietario
    }

}
