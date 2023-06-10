package com.calendly.calendly.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DashboardController {

    @FXML
    private Button addButton;

    @FXML
    private BarChart<?, ?> chartGiornaliero;

    @FXML
    private TableColumn<?, ?> colonnaData;

    @FXML
    private TableColumn<?, ?> colonnaData1;

    @FXML
    private TableColumn<?, ?> colonnaDipendente;

    @FXML
    private TableColumn<?, ?> colonnaEmail;

    @FXML
    private TableColumn<?, ?> colonnaEmail1;

    @FXML
    private TableColumn<?, ?> colonnaId;

    @FXML
    private TableColumn<?, ?> colonnaId1;

    @FXML
    private TableColumn<?, ?> colonnaNomeCognome;

    @FXML
    private TableColumn<?, ?> colonnaNomeCognome1;

    @FXML
    private TableColumn<?, ?> colonnaNumero;

    @FXML
    private TableColumn<?, ?> colonnaNumero1;

    @FXML
    private TableColumn<?, ?> colonnaPrezzo;

    @FXML
    private TableColumn<?, ?> colonnaServizio;

    @FXML
    private Button editButton;

    @FXML
    private Label labelClienti;

    @FXML
    private Label labelDashboard;

    @FXML
    private TableView<?> tableClienti;

    @FXML
    private TableView<?> tableGiornliera;

    @FXML
    void aggiungiCliente(ActionEvent event) {

    }

    @FXML
    void modificaCliente(ActionEvent event) {

    }

}
