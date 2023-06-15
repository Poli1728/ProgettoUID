package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.*;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.View.MyInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
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

    private void impostaTemi() throws IOException {
        if(MyInfo.getInstance().getFont().equals("Dyslexie")){
            labelDashboard.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeLabel()-1));
            labelDati.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeLabel()-1));
            txtGuadagno.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-1));
            txtNumero.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-1));
            txtGuadagnoCalcolare.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-1));
            txtNumeroCalcolare.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-1));
        }else{
            labelDashboard.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeLabel()));
            labelDati.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeLabel()));
            txtGuadagno.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
            txtNumero.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
            txtGuadagnoCalcolare.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
            txtNumeroCalcolare.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
        }
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
        String [] parametri = {data};
        Guadagno guadagno = new Guadagno(data, (int)GestoreDbThreaded.getInstance().runQuery(10,null, parametri));
        guadagni.getData().add(new XYChart.Data<String, Number>(guadagno.getData(), guadagno.getGuadagno()));
        txtGuadagnoCalcolare.setText(guadagno.getGuadagno().toString()+"€");
        chartGiornaliero.getData().add(appuntamenti);
        chartGiornaliero.getData().add(guadagni);
    }

    @FXML
    void avviaNotifica(MouseEvent event) throws SQLException {
        if(MyInfo.getInstance().getNotifica() == 0 && MyInfo.getInstance().getContatore() == 0){
            String data = GestoreData.getInstance().generaDataOdierna();
            String appuntamentiNotifica = GeneratoreStringheAlert.getInstance().generaNotificaTesto(data);
            SceneHandler.getInstance().generaAlert(appuntamentiNotifica,true);
            MyInfo.getInstance().setContatore(1);
        }
    }

    // inizializza tutto

    @FXML
    void initialize() throws SQLException, IOException {
        String data = GestoreData.getInstance().generaDataOdierna();
        impostaTemi();
        impostaTable(data);
        impostaChart(data);
    }

}
