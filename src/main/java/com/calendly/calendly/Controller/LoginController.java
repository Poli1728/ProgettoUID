package com.calendly.calendly.Controller;
import com.calendly.calendly.SceneHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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



    //Funzione dedicata all'accesso dell'utente ~ Marco
    @FXML
    void accedi(ActionEvent event) {
        //Questa è solo come prova per l'accesso, qui si dovrebbe andare a cercare all'interno del database ~ Marco
        if (usernameField.getText().equals("Marco") && passwordField.getText().equals("Marco")){
            SceneHandler.getInstance().launchHome();
        }
    }


    @FXML
    void initialize() {
        usernameField.setOnKeyReleased(this::handleKeyReleasedUsernameField);
        passwordField.setOnKeyReleased(this::handleKeyReleasedPasswordField);
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



    private Stage stage;

    public void init(Stage stage) {
        if (stage == null)
            return;
        this.stage = stage;
        Scene scene = ancorPane.getScene();

        configPane(scene);
        configAccediButton(scene);
        configLoginLabelsAndFields(scene);

    }

    private void configPane(Scene scene) {

        //todo considerare la max size quando si assegna layout x e y
        //tipo: if (x > MAXSIZE)
        //          width = MAXSIZE

        pane.setMaxSize(200, 300);

        pane.prefWidthProperty().bind(scene.widthProperty().divide(3).multiply(1.5));
        pane.prefHeightProperty().bind(scene.heightProperty().divide(3).multiply(2.5));

        double xx = stage.getWidth()/2  -  pane.getPrefWidth()/2;
        double yy = stage.getHeight()/2  -  pane.getPrefHeight()/2;
        pane.setLayoutX(xx);
        pane.setLayoutY(yy);

        scene.widthProperty().addListener((obs, oldValue, newValue) -> {
            double x = scene.getWidth()/2  -  pane.getWidth()/2;
            pane.setLayoutX(x);
        });
        scene.heightProperty().addListener((observableValue, oldValue, newValue) -> {
            double y = scene.getHeight()/2  -  pane.getHeight()/2;
            pane.setLayoutY(y);
        });

    }

    private void configLoginLabelsAndFields(Scene scene) {

        Platform.runLater(() -> {
            loginLabel.layoutXProperty().bind(pane.widthProperty().divide(2).subtract(loginLabel.getPrefWidth()/2));
            loginLabel.layoutYProperty().bind(pane.heightProperty().divide(3));

            double value = 0.5;

            usernameLabel.prefWidthProperty().bind(accediButton.widthProperty());
            usernameLabel.prefHeightProperty().bind(accediButton.heightProperty());
            usernameLabel.layoutXProperty().bind(accediButton.layoutXProperty());
            usernameLabel.layoutYProperty().bind(loginLabel.layoutYProperty().add(loginLabel.getHeight()).add(50));
            System.out.println(loginLabel.getPrefHeight());


            usernameField.prefWidthProperty().bind(accediButton.widthProperty());
            usernameField.prefHeightProperty().bind(accediButton.heightProperty());
            usernameField.layoutXProperty().bind(accediButton.layoutXProperty());
            usernameField.layoutYProperty().bind(usernameLabel.layoutYProperty().add(usernameLabel.getHeight()).add(20));

            System.out.println(usernameLabel.getLayoutX() + " " +usernameLabel.getHeight() + " " + 20);
            System.out.println(usernameField.getLayoutY());

            passwordLabel.prefWidthProperty().bind(accediButton.widthProperty());
            passwordLabel.prefHeightProperty().bind(accediButton.heightProperty());
            passwordLabel.layoutXProperty().bind(accediButton.layoutXProperty());
            passwordLabel.layoutYProperty().bind(usernameField.layoutYProperty().add(usernameField.getHeight()).add(40));

            passwordField.prefWidthProperty().bind(accediButton.widthProperty());
            passwordField.prefHeightProperty().bind(accediButton.heightProperty());
            passwordField.layoutXProperty().bind(accediButton.layoutXProperty());
            passwordField.layoutYProperty().bind(passwordLabel.layoutYProperty().add(passwordLabel.getHeight()).add(20));

        });

    }

    private enum dir {WIDTH, HEIGHT};
    private void configAccediButton(Scene scene) {
        //todo considerare la max size quando si assegna layout x e y
        //tipo: if (x > MAXSIZE)
        //          width = MAXSIZE

        accediButton.setMaxSize(250, 70);

        accediButton.prefWidthProperty().bind(pane.widthProperty().divide(3).multiply(2.5));
        accediButton.prefHeightProperty().bind(pane.heightProperty().divide(5).multiply(0.3));

        Platform.runLater(() ->  {
            double xx = pane.getWidth()/2 - accediButton.getWidth()/2;
            double yy = pane.getHeight() - accediButton.getHeight() - accediButton.getHeight()*0.7;
            accediButton.setLayoutX(xx);
            accediButton.setLayoutY(yy);
        });

        scene.widthProperty().addListener((obs, oldValue, newValue) -> {
            double x = pane.getWidth()/2 - accediButton.getWidth()/2;
            accediButton.setLayoutX(x);
        });
        scene.heightProperty().addListener((observableValue, oldValue, newValue) -> {
            double y = pane.getHeight() - accediButton.getHeight() - accediButton.getHeight()*0.7;
            accediButton.setLayoutY(y);
        });
    }

    private double accediButtonSize(dir direc) {
        double prefSize;
        double maxSize;
        if (direc == dir.WIDTH) {
            prefSize = pane.getWidth() / 3 * 2.5;
            maxSize = accediButton.getMaxWidth();
        } else {
            prefSize = pane.getHeight() / 5 * 0.3;
            maxSize = accediButton.getMaxHeight();
        }

        if (prefSize >= maxSize) {
            return maxSize;
        }

        return prefSize;
    }

}
