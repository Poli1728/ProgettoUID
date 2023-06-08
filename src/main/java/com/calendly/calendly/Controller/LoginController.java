package com.calendly.calendly.Controller;
import com.calendly.calendly.SceneHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
        //if (usernameField.getText().equals("Marco") && passwordField.getText().equals("Marco")){
        SceneHandler.getInstance().launchDashboard();
        //}
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
            setXLocationInsidePane();

            loginLabel.layoutYProperty().bind(pane.heightProperty().divide(6));
            usernameLabel.layoutYProperty().bind(loginLabel.layoutYProperty().add(loginLabel.getHeight()).add(50));
            usernameField.layoutYProperty().bind(usernameLabel.layoutYProperty().add(usernameLabel.getHeight()).add(20));
            passwordLabel.layoutYProperty().bind(usernameField.layoutYProperty().add(usernameField.getHeight()).add(20));
            passwordField.layoutYProperty().bind(passwordLabel.layoutYProperty().add(passwordLabel.getHeight()).add(20));
            accediButton.layoutYProperty().bind(pane.heightProperty().subtract(accediButton.getHeight()).subtract(accediButton.getHeight()*0.7));
        });

        pane.layoutBoundsProperty().addListener(observable -> {
            setXLocationInsidePane();
        });

    }

    private void setXLocationInsidePane() {

        int MAX_WIDTH = 250;
        int MAX_HEIGHT = 70;

        loginLabel.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
        usernameLabel.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
        usernameField.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
        passwordLabel.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
        passwordField.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
        accediButton.setMaxSize(MAX_WIDTH, MAX_HEIGHT);


        double width  = pane.getWidth()  / 3 * 2.5;
        double height = pane.getHeight() / 5 * 0.3;

        loginLabel.setPrefSize(width, height);
        usernameLabel.setPrefSize(width, height);
        usernameField.setPrefSize(width, height);
        passwordLabel.setPrefSize(width, height);
        passwordField.setPrefSize(width, height);
        accediButton.setPrefSize(width, height);


        width = accediButton.getWidth();
        double x = pane.getWidth()/2  - width/2;

        loginLabel.setLayoutX(x);
        usernameLabel.setLayoutX(x);
        usernameField.setLayoutX(x);
        passwordLabel.setLayoutX(x);
        passwordField.setLayoutX(x);
        accediButton.setLayoutX(x);
    }

}
