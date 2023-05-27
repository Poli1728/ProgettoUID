package com.calendly.calendly.Controller;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class WelcomePageController {

    @FXML
    private Pane pane;

    //@FXML
    //private AnchorPane ancorPane;

    @FXML
    private Button continueButton;

    @FXML
    private StackPane stackPane;

    @FXML
    void actionContinueButton(ActionEvent event) {
        //TODO quando premuto, passa alla schermata di creazione dell'account proprietario
    }

    public void init() {
        configPane();
        configContinueButton();
        configStackPane();
    }

    private void configPane() {


    }

    private void configContinueButton() {

    }

    private void configStackPane() {
        double x = ((pane.getWidth() / 2));
        double y = ((pane.getHeight() /2) );
        System.out.println(x);
        System.out.println(y);
        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);
        stackPane.setAlignment(Pos.CENTER_LEFT);

        stackPane.prefHeightProperty().bind(Bindings.size(pane.getChildren()).multiply(500));
    }


}
