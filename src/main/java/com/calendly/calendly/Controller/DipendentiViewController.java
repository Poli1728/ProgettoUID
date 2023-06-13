package com.calendly.calendly.Controller;

import com.calendly.calendly.View.CardContainer;
import com.calendly.calendly.Model.Dipendente;
import com.calendly.calendly.Model.GestoreDB;
import com.calendly.calendly.Model.ReusableDBResultsConverter;
import com.calendly.calendly.View.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.LinkedList;

public class DipendentiViewController {
    @FXML
    private Button addButton;

    @FXML
    private AnchorPane ancorPane;

    @FXML
    private TextField cercaField;

    @FXML
    private Button editButton;

    @FXML
    private ComboBox<String> filtroBox;

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
        Dialog.getInstance().requestDialog(Dialog.from.DIPENDENTI, Dialog.actions.AGGIUNGI, -1, ancorPane);
    }

    @FXML
    void actionEditButton(ActionEvent event) {
        Dialog.getInstance().requestDialog(Dialog.from.DIPENDENTI, Dialog.actions.MODIFICA, -1, ancorPane);
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

        filtroBox.getItems().addAll("ID", "Nome", "Cognome", "Ruolo", "Salario");

        LinkedList<Dipendente> res;

        try {
            res = ReusableDBResultsConverter.getInstance().getDipendenti(GestoreDB.getInstance().leggiEntità(GestoreDB.entità.Dipendenti));
        } catch (SQLException e) {
            //todo alert errore nel contattare il database
            throw new RuntimeException(e);
        }

        CardContainer.getInstance().setCardContainer(res, vboxEsterno);

    }

}
