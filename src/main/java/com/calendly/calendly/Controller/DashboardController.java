package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.*;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import com.calendly.calendly.View.MyFont;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    private Label labelDati;
    @FXML
    private Text txtGuadagno;

    @FXML
    private Text txtGuadagnoCalcolare;

    @FXML
    private Text txtNumero;

    @FXML
    private Text txtNumeroCalcolare;

    @FXML
    private TableView<Appuntamento> tableGiornliera;

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

    private void impostaTemi(){
        labelDashboard.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        labelDati.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        txtGuadagno.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        txtNumero.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        txtGuadagnoCalcolare.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        txtNumeroCalcolare.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
    }

    private String generaNotificaTesto(String data) throws SQLException {
        ArrayList<String> app = GestoreDB.getInstance().creaLista(true, "Data", data);
        StringBuilder s = new StringBuilder();
        s.append("Nome ").append(" Cognome ").append(" Numero        ").append("Servizio\n");
        for(String i : app){
            String [] info = i.split(";");
            s.append(info[2]).append(" ").append(info[3]).append("   ").append(info[4]).append(" ").append(info[7]).append("\n");
        }
        return s.toString();
    }

    // aggiunge gli elementi all'interno della table

    private void impostaTable(String data){
        try {
            ArrayList<Appuntamento> app = GestoreAppuntamenti.getInstance().listaAppuntamenti(true, "Data", data);
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
            txtNumeroCalcolare.setText(i.getNumeroApp().toString());
        }
        XYChart.Series<String, Number> guadagni = new XYChart.Series<String, Number>();
        guadagni.setName("Ammontare (€)");
        Guadagno guadagno = new Guadagno(data, GestoreDB.getInstance().calcolaGuadagno(data));
        guadagni.getData().add(new XYChart.Data<String, Number>(guadagno.getData(), guadagno.getGuadagno()));
        txtGuadagnoCalcolare.setText(guadagno.getGuadagno().toString()+"€");
        chartGiornaliero.getData().add(appuntamenti);
        chartGiornaliero.getData().add(guadagni);
    }

    // inizializza tutto

    @FXML
    void initialize() throws SQLException {
        String data = GestoreData.getInstance().generaDataOdierna();
        impostaTemi();
        impostaTable(data);
        impostaChart(data);
        if(Settings.NOTIFICA){
            String appuntamentiNotifica = generaNotificaTesto(data);
            SceneHandler.getInstance().generaAlert(appuntamentiNotifica,true);
        }
    }

}
