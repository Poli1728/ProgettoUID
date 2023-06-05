package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.Appuntamento;
import com.calendly.calendly.Model.GestoreAppuntamenti;
import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AppuntamentiController implements Initializable {
    @FXML
    private Pane pane;

    @FXML
    private TableColumn<?, ?> colonnaData;

    @FXML
    private TableColumn<?, ?> colonnaDipendente;

    @FXML
    private TableColumn<?, ?> colonnaEmail;

    @FXML
    private TableColumn<?, ?> colonnaId;

    @FXML
    private TableColumn<?, ?> colonnaNomeCognome;

    @FXML
    private TableColumn<?, ?> colonnaNumero;

    @FXML
    private TableColumn<?, ?> colonnaServizio;

    @FXML
    private TableView<Appuntamento> table;

    //Aggiunge il pane che permette di aggiungere, modificare e rimuovere
    private void modificaPane(String s) throws IOException {
        pane.getChildren().clear();
        FXMLLoader loader = SceneHandler.getInstance().creaPane("fxml/Appuntamenti"+s);
        Pane aggiungi = loader.load();
        pane.getChildren().add(aggiungi);
    }

    @FXML
    void aggiungiAppuntamento(ActionEvent event) throws IOException {
        modificaPane("Aggiungi");
    }

    @FXML
    void modificaAppuntamento(ActionEvent event) throws IOException {
        modificaPane("Modifica");
    }

    @FXML
    void rimuoviAppuntamento(ActionEvent event) throws IOException {
        modificaPane("Rimuovi");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList<Appuntamento> app = GestoreAppuntamenti.getInstance().listaAppuntamenti();
        } catch (SQLException e) {}
        
    }
}
