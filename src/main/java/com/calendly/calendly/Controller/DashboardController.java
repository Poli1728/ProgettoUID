package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.*;
import com.calendly.calendly.View.MyFont;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardController {

    @FXML
    private BarChart<String, Number> chartGiornaliero;

    @FXML
    private TableColumn<Appuntamento, Date> colonnaData;

    @FXML
    private TableColumn<Appuntamento, String> colonnaDipendente;

    @FXML
    private TableColumn<Appuntamento, String> colonnaEmail;

    @FXML
    private TableColumn<Appuntamento, Integer> colonnaId;

    @FXML
    private TableColumn<Appuntamento, String> colonnaNomeCognome;

    @FXML
    private TableColumn<Appuntamento, String> colonnaNumero;

    @FXML
    private TableColumn<Appuntamento, Double> colonnaPrezzo;

    @FXML
    private TableColumn<Appuntamento, String> colonnaServizio;

    @FXML
    private Label labelDashboard;

    @FXML
    private TableView<Appuntamento> tableGiornliera;

    @FXML
    private Text text;

    // associa i valori della singola colonna alla variabile presente all'interno di appuntamento

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

    // aggiunge gli elementi all'interno della table

    private void impostaTable(String data){
        try {
            ArrayList<Appuntamento> app = GestoreAppuntamenti.getInstance().listaAppuntamenti(true, "A.Data", data);
            ObservableList<Appuntamento> observableApp = FXCollections.observableArrayList(app);
            tableGiornliera.setItems(observableApp);
            setCellValue();
        } catch (SQLException e) {}
    }

    //aggiunge gli elementi all'interno del chart

    private void impostaChart(String data) throws SQLException {
        XYChart.Series<String, Number> appuntamenti = new XYChart.Series<String, Number>();
        appuntamenti.setName("Appuntamenti");
        ArrayList<Statistiche> lista = null;
        lista = GestoreStatistiche.getInstance().statistiche(GestoreStatistiche.getInstance().getGiornaliero(), 0, 0, data);
        for (Statistiche i : lista) {
            appuntamenti.getData().add(new XYChart.Data<String, Number>(i.getData(), i.getNumeroApp()));
        }
        chartGiornaliero.getData().add(appuntamenti);
    }

    // inizializza tutto

    @FXML
    void initialize() throws SQLException {
        String data = String.valueOf(java.time.LocalDateTime.now());
        StringBuilder anno = GestoreData.getInstance().annoCorrente(data);
        StringBuilder mese = GestoreData.getInstance().meseCorrente(data);
        StringBuilder giorno = GestoreData.getInstance().giornoCorrente(data);
        data = giorno.toString()+"/"+mese.toString()+"/"+anno.toString();
        labelDashboard.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        text.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        impostaTable(data);
        impostaChart(data);
    }

}
