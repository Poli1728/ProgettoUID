package com.calendly.calendly.Controller;

import com.calendly.calendly.View.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClientiController {

    @FXML
    private Button addButton;

    @FXML
    private Button cercaButton;

    @FXML
    private TextField cercaField;

    @FXML
    private Button editButton;

    @FXML
    private Label labelClienti;

    @FXML
    private Button removeButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vboxEsterno;

    @FXML
    void aggiungiCliente(ActionEvent event) {

    }

    @FXML
    void cerca(ActionEvent event) {

    }

    @FXML
    void modificaCliente(ActionEvent event) {

    }

    @FXML
    void rimuoviCliente(ActionEvent event) {

    }

    @FXML
    void initialize(){
        vboxEsterno.setSpacing(15);

        for (int i = 0; i < 10; i++) {
            Card obj1 = new Card(Card.cardType.EMPLOYEE, 4, vboxEsterno);
            Card obj2 = new Card(Card.cardType.EMPLOYEE, 5, vboxEsterno);

            HBox hbox1 = new HBox(10 , obj1, obj2);

            hbox1.setFillHeight(true);
            hbox1.setAlignment(Pos.BASELINE_CENTER);

            vboxEsterno.getChildren().add(hbox1);

        }
    }

}
