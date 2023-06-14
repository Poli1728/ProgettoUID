package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.GestoreDB;
import com.calendly.calendly.Model.ReusableDBResultsConverter;
import com.calendly.calendly.Model.Servizio;
import com.calendly.calendly.View.CardContainer;
import com.calendly.calendly.View.MyInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.LinkedList;

public class ServiziViewController {

    @FXML
    private Button addButton;

    @FXML
    private AnchorPane ancorPane;

    @FXML
    private Button removeButton;

    @FXML
    private HBox search_hbox;

    @FXML
    private VBox vboxEsterno;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label labelServizi;

    @FXML
    private ComboBox filtroBox;

    @FXML
    void actionAddButton(ActionEvent event) {

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
        labelServizi.setFont(Font.font(MyInfo.getInstance().getFont(), MyInfo.getInstance().getSizeLabel()));
        filtroBox.getItems().addAll("Id", "Nome", "Cognome", "Ruolo", "Salario");

        LinkedList<Servizio> res;

        try {
            res = ReusableDBResultsConverter.getInstance().getServizi(GestoreDB.getInstance().leggiEntità(GestoreDB.entità.Servizi));
        } catch (SQLException e) {
            //todo alert errore nel contattare il database
            throw new RuntimeException(e);
        }

        CardContainer.getInstance().setCardContainer(res, vboxEsterno);
    }


}
