package com.calendly.calendly.Controller;

import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class AppuntamentiController {
    @FXML
    private Pane pane;
    @FXML
    private TableColumn<?, ?> colonnaDipendente;

    @FXML
    private TableColumn<?, ?> colonnaEmail;

    @FXML
    private TableColumn<?, ?> colonnaOrario;

    @FXML
    private TableColumn<?, ?> colonnaServizio;

    @FXML
    private TableColumn<?, ?> colonnaUtente;

    @FXML
    private TableView<?> table;

    //Aggiunge il pane che permette di aggiungere, modificare e rimuovere
    private void modificaPane(String s) throws IOException {
        pane.getChildren().clear();
        Pane aggiungi = SceneHandler.getInstance().creaPane("fxml/Appuntamenti"+s);
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

    void initialize(){
        //Qui va l'inserimento nella tabella
    }

}
