package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.Appuntamento;
import com.calendly.calendly.Model.GestoreAppuntamenti;
import com.calendly.calendly.Model.GestoreDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AppuntamentiController implements Initializable {
    @FXML
    private TextField cercaField;

    @FXML
    private TableColumn<Appuntamento, Date> colonnaData;

    @FXML
    private TableColumn<Appuntamento, String> colonnaDipendente;

    @FXML
    private TableColumn<Appuntamento, Integer> colonnaId;

    @FXML
    private TableColumn<Appuntamento, String> colonnaEmail;

    @FXML
    private TableColumn<Appuntamento, String> colonnaNomeCognome;

    @FXML
    private TableColumn<Appuntamento, String> colonnaNumero;
    @FXML
    private TableColumn<Appuntamento, Double> colonnaPrezzo;

    @FXML
    private TableColumn<Appuntamento, String> colonnaServizio;

    @FXML
    private TableView<Appuntamento> table;

    @FXML
    private ComboBox<String> filtroBox;

    private void setCellValue(){
        colonnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colonnaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colonnaPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        colonnaDipendente.setCellValueFactory(new PropertyValueFactory<>("dipendente"));
        colonnaNomeCognome.setCellValueFactory(new PropertyValueFactory<>("identificativo"));
        colonnaNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colonnaServizio.setCellValueFactory(new PropertyValueFactory<>("servizio"));
    }

    private void aggiungiItems(){
        filtroBox.getItems().add("Id");
        filtroBox.getItems().add("Email");
        filtroBox.getItems().add("Nome");
        filtroBox.getItems().add("Cognome");
        filtroBox.getItems().add("Numero");
        filtroBox.getItems().add("Data");
        filtroBox.getItems().add("Dipendente");
        filtroBox.getItems().add("Servizio");
        filtroBox.getItems().add("Prezzo");
    }

    @FXML
    void aggiungiAppuntamento(ActionEvent event) throws IOException {
    }

    @FXML
    void modificaAppuntamento(ActionEvent event) throws IOException {

    }

    @FXML
    void rimuoviAppuntamento(ActionEvent event) throws IOException {
    }

    @FXML
    void cerca(ActionEvent event) throws SQLException {
        if(filtroBox.getValue() == null){
            System.out.println("Ciao");
            //Si richiama l'alert
        } else if (cercaField.getText().equals("")) {
            System.out.println("Puzzi");
            //Si richiama l'alert
        } else {
            table.getItems().clear();
            String filtro = "";
            switch (filtroBox.getValue()) {
                case "Id" -> filtro = "A.Id";
                case "Email" -> filtro = "C.Email";
                case "Nome" -> filtro = "C.Nome";
                case "Cognome" -> filtro = "C.Cognome";
                case "Numero" -> filtro = "C.Numero";
                case "Data" -> filtro = "A.Data";
                case "Dipendente" -> filtro = "D.Username";
                case "Servizio" -> filtro = "S.Tipo";
                case "Prezzo" -> filtro = "S.Prezzo";
            }
            ObservableList<Appuntamento> app = FXCollections.observableArrayList(GestoreAppuntamenti.getInstance().listaAppuntamenti(true,filtro, "'"+cercaField.getText()+"'"));
            table.setItems(app);
            setCellValue();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aggiungiItems();
        table.getItems().clear();
        try {
            ArrayList<Appuntamento> app = GestoreAppuntamenti.getInstance().listaAppuntamenti(false, "", "");
            ObservableList<Appuntamento> observableApp = FXCollections.observableArrayList(app);
            table.setItems(observableApp);
            setCellValue();

        } catch (SQLException e) {}
    }
}
