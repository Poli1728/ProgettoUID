package com.calendly.calendly.Controller;

import com.calendly.calendly.View.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ServiziViewController {

    @FXML
    private Button addButton;

    @FXML
    private AnchorPane ancorPane;

    @FXML
    private TextField cercaField;

    @FXML
    private Button editButton;

    @FXML
    private ComboBox<?> filtroBox;

    @FXML
    private Label labelDipendenti;

    @FXML
    private Button removeButton;

    @FXML
    private Button searchButton;

    @FXML
    private HBox search_hbox;

    @FXML
    private VBox vboxEsterno;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    void actionAddButton(ActionEvent event) {

    }

    @FXML
    void actionEditButton(ActionEvent event) {

    }

    @FXML
    void actionFiltroBox(ActionEvent event) {

    }

    @FXML
    void actionRemoveButton(ActionEvent event) {

    }

    @FXML
    void actionSearchButton(ActionEvent event) {

    }


    @FXML
    void initialize() {
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
