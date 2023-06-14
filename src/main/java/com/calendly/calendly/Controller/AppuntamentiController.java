package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.Appuntamento;
import com.calendly.calendly.Model.GestoreAppuntamenti;
import com.calendly.calendly.Model.GestoreDB;
import com.calendly.calendly.Model.GestoreData;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.View.Dialog;
import com.calendly.calendly.View.MyFont;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppuntamentiController{
    @FXML
    private Label labelAppuntamenti;
    @FXML
    private Button removeButton;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private TextField idScontrino;
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
    private Button cercaButton;

    @FXML
    private Button generaButton;

    @FXML
    private TableView<Appuntamento> table;

    @FXML
    private AnchorPane ancorPane;

    @FXML
    private ComboBox<String> filtroBox;

    // Associa le colonne della table alle variabili della classe Appuntamento
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

    // aggiunge gli items al combo box

    private void aggiungiItems(){
        filtroBox.getItems().addAll("Id","Email","Nome","Cognome","Numero","Data","Dipendente","Servizio","Prezzo");
    }

    // aggiunge l'appuntamento al db

    @FXML
    void aggiungiAppuntamento(ActionEvent event) throws IOException {
        //controlla se c'è l'appuntamento indicato
        //Dialog.getInstance().requestDialog(Dialog.from.APPUNTAMENTI, Dialog.actions.AGGIUNGI, -1, ancorPane);
    }

    // modifica l'appuntamento presente nel db

    @FXML
    void modificaAppuntamento(ActionEvent event) throws IOException {
        //controlla se c'è l'appuntamento indicato
        //Dialog.getInstance().requestDialog(Dialog.from.APPUNTAMENTI, Dialog.actions.MODIFICA, -1, ancorPane);
    }

    // rimuove l'appuntamento dal db

    @FXML
    void rimuoviAppuntamento(ActionEvent event) throws IOException {
        //controlla se c'è l'appuntamento indicato
        //Dialog.getInstance().requestDialog(Dialog.from.APPUNTAMENTI, Dialog.actions.RIMUOVI, -1, ancorPane);
    }

    // Questa funzione genera uno scontrino all'interno con presenti dei dati dell'appuntamento

    @FXML
    void generaScontrino(ActionEvent event) throws DocumentException, FileNotFoundException, SQLException {
        if(idScontrino.getText().equals("")){
            SceneHandler.getInstance().generaAlert("Non hai inserito l'id da cercare.");
        }else {
            if(!GestoreDB.getInstance().selezionaValore(GestoreDB.getInstance().getAppuntamenti(),"Id", idScontrino.getText()).equals(idScontrino.getText())){
                SceneHandler.getInstance().generaAlert("L'id inserito non esiste.");
            }else{
                Document doc = new Document();
                String id = idScontrino.getText();
                PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(SceneHandler.getInstance().apriDirectoryChooser()+"/Scontrino"+id+".pdf"));
                doc.open();
                doc.addTitle("Scontrino "+id);
                ArrayList<Appuntamento> app = GestoreAppuntamenti.getInstance().listaAppuntamenti(true,"A.Id", id);
                Appuntamento a = app.get(0);
                String data = String.valueOf(java.time.LocalDateTime.now());
                StringBuilder giorno = GestoreData.getInstance().giornoCorrente(data);
                StringBuilder anno = GestoreData.getInstance().annoCorrente(data);
                StringBuilder mese = GestoreData.getInstance().meseCorrente(data);
                data = giorno.toString()+"/"+mese.toString()+"/"+anno.toString() +" "+ data.substring(11, 19);
                String parag = "Id appuntamento: "+a.getId()+"\n"+"Email: "+a.getEmail()+"\n"+"Nome e cognome: "+a.getIdentificativo()+"\n"+"Numero: "+a.getNumero()+"\n"+"Data appuntamento: "+a.getData()+"\nData generazione scontrino: "+data+"\n"+"Dipendente: "+a.getDipendente()+"\n"+"Servizio: "+a.getServizio()+"\n"+"Prezzo: "+a.getPrezzo()+"\n"+"Grazie per averci scelto\n";
                doc.add(new Paragraph(parag));
                doc.close();
                writer.close();
            }

        }
        idScontrino.setText("");
        idScontrino.setPromptText("Id");
    }

    // Questa è la funzione di ricerca tramite combo box e text field

    @FXML
    void cerca(ActionEvent event) throws SQLException {
        if(filtroBox.getValue() == null){
            SceneHandler.getInstance().generaAlert("Non hai selezionato il filtro.");
        } else if (cercaField.getText().equals("")) {
            SceneHandler.getInstance().generaAlert("Non hai inserito il valore da cercare.");
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
            ObservableList<Appuntamento> app = FXCollections.observableArrayList(GestoreAppuntamenti.getInstance().listaAppuntamenti(true,filtro, cercaField.getText()));
            table.setItems(app);
            setCellValue();
        }
        cercaField.setText("");
        cercaField.setPromptText("Cerca");
    }

    //Funzione initialize che inserisci gli oggetti nella tabella e imposta le scritte e il combo box

    @FXML
    public void initialize() {
        labelAppuntamenti.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
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
