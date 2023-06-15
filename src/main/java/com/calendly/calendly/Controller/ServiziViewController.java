package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.GestoreDB;
import com.calendly.calendly.Model.GestoreDbThreaded;
import com.calendly.calendly.Model.ReusableDBResultsConverter;
import com.calendly.calendly.Model.Servizio;
import com.calendly.calendly.View.CardContainer;
import com.calendly.calendly.View.Dialog;
import com.calendly.calendly.View.MyInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private TextField cercaField;

    @FXML
    private Button searchButton;

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
        Dialog.getInstance().requestDialog(Dialog.from.SERVIZI, Dialog.actions.AGGIUNGI, -1, ancorPane);

    }

    @FXML
    void actionFiltroBox(ActionEvent event) {

    }

    @FXML
    void actionRemoveButton(ActionEvent event) {
        Dialog.getInstance().requestDialog(Dialog.from.SERVIZI, Dialog.actions.RIMUOVI, -1, ancorPane);

    }

    @FXML
    void actionSearchButton(ActionEvent event) {

    }


    @FXML
    void initialize() throws IOException {
        vboxEsterno.setSpacing(15);
        if(MyInfo.getInstance().getFont().equals("Dyslexie")){
            labelServizi.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeLabel()));
            cercaField.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),cercaField.getFont().getSize()-1));
            searchButton.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(), searchButton.getFont().getSize()-1));
            addButton.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(), addButton.getFont().getSize()-1));
            removeButton.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(), removeButton.getFont().getSize()-1));
        }else{
            labelServizi.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeLabel()));
            cercaField.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(), cercaField.getFont().getSize()));
            searchButton.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(), searchButton.getFont().getSize()));
            addButton.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(), addButton.getFont().getSize()));
            removeButton.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(), removeButton.getFont().getSize()));
        }
        filtroBox.getItems().addAll("Id", "Nome", "Cognome", "Ruolo", "Salario");

        LinkedList<Servizio> res;

        res = ReusableDBResultsConverter.getInstance().getServizi((ArrayList<String>) GestoreDbThreaded.getInstance().runQuery(1, GestoreDB.entità.Servizi, null));


        CardContainer.getInstance().setCardContainer(res, vboxEsterno);
    }


}
