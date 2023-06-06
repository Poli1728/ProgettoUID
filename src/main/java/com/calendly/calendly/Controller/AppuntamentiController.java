package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.Appuntamento;
import com.calendly.calendly.Model.GestoreAppuntamenti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    void cerca(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aggiungiItems();
        try {
            ArrayList<Appuntamento> app = GestoreAppuntamenti.getInstance().listaAppuntamenti();
            ObservableList<Appuntamento> observableApp = FXCollections.observableArrayList(app);
            table.setItems(observableApp);
            setCellValue();

        } catch (SQLException e) {}
    }
}
