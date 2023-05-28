package com.calendly.calendly.Controller;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WelcomePageController {

    @FXML
    private Pane pane;

    @FXML
    private Button continueButton;

    @FXML
    private StackPane stackPane;

    @FXML
    void actionContinueButton(ActionEvent event) {
        //TODO quando premuto, passa alla schermata di creazione dell'account proprietario + animazione
        SceneHandler.getInstance().launchLogin();
    }

    //--------------------------
    private Stage stage;

    public void init(Stage stage) {
        if (stage == null)
            return;
        this.stage = stage;
        Scene scene = stackPane.getScene();
        configPane(scene);
        configContinueButton(scene);
        configStackPane(scene);
    }


    private void configPane(Scene scene) {


    }

    private void configStackPane(Scene scene) {

        stackPane.prefWidthProperty().bind(scene.widthProperty().divide(3).multiply(1.5));
        stackPane.prefHeightProperty().bind(scene.heightProperty().divide(3).multiply(2.5));

        stage.setOnShown(windowEvent -> {
            double x = stage.getWidth()/2  -  stackPane.getPrefWidth()/2;
            double y = stage.getHeight()/2  -  stackPane.getPrefHeight()/2;
            stackPane.setLayoutX(x);
            stackPane.setLayoutY(y);
        });

        scene.widthProperty().addListener((obs, oldValue, newValue) -> {
            double x = scene.getWidth()/2  -  stackPane.getWidth()/2;
            stackPane.setLayoutX(x);
        });
        scene.heightProperty().addListener((observableValue, oldValue, newValue) -> {
            double y = scene.getHeight()/2  -  stackPane.getHeight()/2;
            stackPane.setLayoutY(y);
        });

        stackPane.setAlignment(Pos.CENTER);
    }


    private void configContinueButton(Scene scene) {
        continueButton.prefWidthProperty().bind(stackPane.widthProperty().divide(3).multiply(2.5));
        continueButton.prefHeightProperty().bind(stackPane.heightProperty().divide(5).multiply(0.3));


        double y = scene.getHeight()/3 - stackPane.getHeight()/2*0.5;
        System.out.println(continueButton.getLayoutY());
        continueButton.setLayoutY(y);

        /*

        scene.heightProperty().addListener(((observableValue, oldValue, newValue) -> {
            double y = scene.getHeight()/3 - stackPane.getHeight()/2;
            continueButton.setLayoutY(50);
            System.out.println(y);
            System.out.println(" --- ");
        }));
         */
    }



}
