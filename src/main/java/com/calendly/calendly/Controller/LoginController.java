package com.calendly.calendly.Controller;
import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
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
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private TextField UsernameField;

    @FXML
    private Button accediButton;





    //Funzione dedicata all'accesso dell'utente ~ Marco
    @FXML
    void Accedi(ActionEvent event) {
        //Questa è solo come prova per l'accesso, qui si dovrebbe andare a cercare all'interno del database ~ Marco
        if (UsernameField.getText().equals("Marco") && PasswordField.getText().equals("Marco")){
            SceneHandler.getInstance().launchHome();
        }
    }

    private Stage stage;
    public void init(Stage stage) {
        if (stage == null)
            return;
        this.stage = stage;
        Scene scene = ancorPane.getScene();

        configPane(scene);
        configAccediButton(scene);

    }

    private void configPane(Scene scene) {

        //todo considerare la max size quando si assegna layout x e y
        //tipo: if (x > MAXSIZE)
        //          width = MAXSIZE

        pane.setMaxSize(500, 700);

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


    private void configAccediButton(Scene scene) {

        //todo considerare la max size quando si assegna layout x e y
        //tipo: if (x > MAXSIZE)
        //          width = MAXSIZE


        accediButton.setMaxSize(400, 70);

        accediButton.prefWidthProperty().bind(pane.widthProperty().divide(3).multiply(2.5));
        accediButton.prefHeightProperty().bind(pane.heightProperty().divide(5).multiply(0.3));

        double xx = pane.getPrefWidth()/2 - (pane.getPrefWidth() / 3 * 2.5)/2;
        double yy = pane.getPrefHeight() - (pane.getPrefHeight() / 5 * 0.3);

        accediButton.setLayoutX(xx);
        accediButton.setLayoutY(yy);

        /*   - non rimuovere -
        System.out.println(xx);
        System.out.println(yy);

        System.out.println(pane.getWidth() + " " + pane.getHeight());
        System.out.println(pane.getPrefWidth() + " " + pane.getPrefHeight());
        System.out.println(accediButton.getWidth() + " " + accediButton.getHeight());
        System.out.println(accediButton.getPrefWidth() + " " + accediButton.getPrefHeight());
        */

        scene.widthProperty().addListener((obs, oldValue, newValue) -> {
            double x = pane.getWidth()/2 - accediButton.getPrefWidth()/2;
            accediButton.setLayoutX(x);
        });
        scene.heightProperty().addListener((observableValue, oldValue, newValue) -> {
            double y = pane.getHeight() - accediButton.getHeight() - accediButton.getHeight()*0.7;
            accediButton.setLayoutY(y);
        });

    }

}
