package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.GestoreDB;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import com.calendly.calendly.View.MyFont;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.sql.SQLException;

public class ImpostazioniController {

    @FXML
    private Label labelImpostazioni;

    @FXML
    private ComboBox<String> temiComboBox;

    @FXML
    private Text txtTemi;

    @FXML
    private Label labelTemi;

    @FXML
    private CheckBox checkDislessia;

    @FXML
    private Label labelDislessia;

    @FXML
    private Text txtDislessia;

    @FXML
    private CheckBox checkNotifica;

    @FXML
    private Label labelNotifica;

    @FXML
    private Text txtNotifica;

    @FXML
    private Label labelRipristina;
    @FXML
    private Text txtRipristina;
    @FXML
    private Button ripristinaButton;

    // Funzione che imposta i tutti i dettagli dei font scelti: Tema, Font e Size(quest'ultima non è modificabile all'utente)
    private void impostaFont(){
        labelImpostazioni.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        labelDislessia.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        labelTemi.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        labelNotifica.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        labelRipristina.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        txtTemi.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        txtDislessia.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        txtNotifica.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        txtRipristina.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
    }

    // É la funzione che imposta il tema dei testi e bottoni, ogni volta aggiorna anche i dati nel DB
    @FXML
    void scegliTema(ActionEvent event) throws SQLException {
        if (temiComboBox.getSelectionModel().isSelected(0)){
            MyFont.getInstance().setTema("DARK");
            String info = MyFont.getInstance().getTema()+";"+MyFont.getInstance().getFont();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
            SceneHandler.getInstance().setTheme();
        }else if (temiComboBox.getSelectionModel().isSelected(1)){
            MyFont.getInstance().setTema("LIGHT");
            String info = MyFont.getInstance().getTema()+";"+MyFont.getInstance().getFont();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
            SceneHandler.getInstance().setTheme();
        }else if (temiComboBox.getSelectionModel().isSelected(2)){
            MyFont.getInstance().setTema("BLU");
            String info = MyFont.getInstance().getTema()+";"+MyFont.getInstance().getFont();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
            SceneHandler.getInstance().setTheme();
        }
        initialize();
    }

    // Funzione che attiva il font in base alla check box
    @FXML
    void attivaDislessia(ActionEvent event) throws SQLException {
        if(checkDislessia.isSelected()){
            MyFont.getInstance().setFont(MyFont.getInstance().getVerdana());
            String info = MyFont.getInstance().getTema()+";"+MyFont.getInstance().getFont();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
        }else{
            MyFont.getInstance().setFont(MyFont.getInstance().getQuicksand());
            String info = MyFont.getInstance().getTema()+";"+MyFont.getInstance().getFont();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
        }
        initialize();
    }

    @FXML
    void attivaNotifica(ActionEvent event) {
        if(checkNotifica.isSelected()){
            Settings.NOTIFICA = true;
        }else{
            Settings.NOTIFICA = false;
        }
    }


    @FXML
    void ripristina(ActionEvent event) throws SQLException {
        GestoreDB.getInstance().svuota();
        SceneHandler.getInstance().launchLogin();
    }

    //Initialize, vengono inserite tutte le cose all'inizio
    @FXML
    public void initialize(){
        if(MyFont.getInstance().getFont().equals("Verdana")){
            checkDislessia.setSelected(true);
        }
        if(Settings.NOTIFICA){
            checkNotifica.setSelected(true);
        }

        if (temiComboBox.getItems().size() == 0){
            temiComboBox.getItems().add("Scuro");
            temiComboBox.getItems().add("Chiaro");
            temiComboBox.getItems().add("Blu");
        }
        String tema = MyFont.getInstance().getTema();
        if (tema.equals("DARK")){
            temiComboBox.setValue("Scuro");
        }else if(tema.equals("LIGHT")){
            temiComboBox.setValue("Chiaro");
        }else if(tema.equals("BLU")){
            temiComboBox.setValue("Blu");
        }
        impostaFont();
    }
}
