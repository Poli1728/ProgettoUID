package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.Cliente;
import com.calendly.calendly.Model.GestoreDB;
import com.calendly.calendly.Model.ReusableDBResultsConverter;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.View.CardContainer;
import com.calendly.calendly.View.Dialog;
import com.calendly.calendly.View.MyFont;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ClientiController {

    @FXML
    private Button addButton;

    @FXML
    private Button cercaButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField field;

    @FXML
    private ComboBox<String> filtro;

    @FXML
    private Label labelClienti;

    @FXML
    private Button removeButton;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox searchHbox;

    @FXML
    private VBox vboxEsterno;

    private void generaCard(boolean cerca, String filtro, String valore){
        /*LinkedList<Cliente> res = null;

        try {
            if(!cerca){
                res = ReusableDBResultsConverter.getInstance().getClienti(GestoreDB.getInstance().leggiEntità(GestoreDB.entità.Clienti));
            }else{
                ArrayList<String> clienti = new ArrayList<String>();
                clienti.add(GestoreDB.getInstance().cercaTramiteValore(GestoreDB.entità.Clienti, filtro, valore));
                res = ReusableDBResultsConverter.getInstance().getClienti(clienti);
            }
        } catch (SQLException e) {}

        CardContainer.getInstance().setCardContainer(res, vboxEsterno);*/
    }

    @FXML
    void actionAggiungiCliente(ActionEvent event) {
        //Dialog.getInstance().requestDialog(Dialog.from.CLIENTI, Dialog.actions.AGGIUNGI, -1, ancorPane);
    }

    @FXML
    void actionCerca(ActionEvent event) {
        if(filtro.getValue() == null){
            SceneHandler.getInstance().generaAlert("Non hai selezionato il filtro.", false);
        }else if(field.getText().equals("")){
            SceneHandler.getInstance().generaAlert("Non hai inserito il valore da cercare.", false);
        }else{
            generaCard(true, filtro.getValue(), field.getText());
        }
    }

    @FXML
    void actionRimuoviCliente(ActionEvent event) {
        //Dialog.getInstance().requestDialog(Dialog.from.CLIENTI, Dialog.actions.RIMUOVI, -1, ancorPane);
    }

    @FXML
    void initialize(){
        vboxEsterno.setSpacing(15);
        labelClienti.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        filtro.getItems().addAll("CF", "Nome", "Cognome", "Email", "Numero");

        generaCard(false, "", "");

    }

}
