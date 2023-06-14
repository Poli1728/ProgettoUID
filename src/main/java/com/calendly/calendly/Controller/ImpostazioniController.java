package com.calendly.calendly.Controller;

import com.calendly.calendly.Model.GestoreDB;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import com.calendly.calendly.View.MyInfo;
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


    @FXML
    private Text txtBasso;

    // Funzione che imposta i tutti i dettagli dei font scelti: Tema, Font e Size(quest'ultima non è modificabile all'utente)
    private void impostaFont(){
        labelImpostazioni.setFont(Font.font(MyInfo.getInstance().getFont(), MyInfo.getInstance().getSizeLabel()));
        labelDislessia.setFont(Font.font(MyInfo.getInstance().getFont(), MyInfo.getInstance().getSizeLabel()));
        labelTemi.setFont(Font.font(MyInfo.getInstance().getFont(), MyInfo.getInstance().getSizeLabel()));
        labelNotifica.setFont(Font.font(MyInfo.getInstance().getFont(), MyInfo.getInstance().getSizeLabel()));
        labelRipristina.setFont(Font.font(MyInfo.getInstance().getFont(), MyInfo.getInstance().getSizeLabel()));
        txtTemi.setFont(Font.font(MyInfo.getInstance().getFont(), MyInfo.getInstance().getSizeTxt()));
        txtDislessia.setFont(Font.font(MyInfo.getInstance().getFont(), MyInfo.getInstance().getSizeTxt()));
        txtNotifica.setFont(Font.font(MyInfo.getInstance().getFont(), MyInfo.getInstance().getSizeTxt()));
        txtRipristina.setFont(Font.font(MyInfo.getInstance().getFont(), MyInfo.getInstance().getSizeTxt()));
        txtBasso.setFont(Font.font(MyInfo.getInstance().getFont(), MyInfo.getInstance().getSizeTxt()));
    }

    // É la funzione che imposta il tema dei testi e bottoni, ogni volta aggiorna anche i dati nel DB
    @FXML
    void scegliTema(ActionEvent event) throws SQLException {
        if (temiComboBox.getSelectionModel().isSelected(0)){
            MyInfo.getInstance().setTema("DARK");
            String info = MyInfo.getInstance().getTema()+";"+ MyInfo.getInstance().getFont()+";"+MyInfo.getInstance().getNotifica();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
            SceneHandler.getInstance().setTheme();
        }else if (temiComboBox.getSelectionModel().isSelected(1)){
            MyInfo.getInstance().setTema("LIGHT");
            String info = MyInfo.getInstance().getTema()+";"+ MyInfo.getInstance().getFont()+";"+MyInfo.getInstance().getNotifica();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
            SceneHandler.getInstance().setTheme();
        }else if (temiComboBox.getSelectionModel().isSelected(2)){
            MyInfo.getInstance().setTema("BLU");
            String info = MyInfo.getInstance().getTema()+";"+ MyInfo.getInstance().getFont()+";"+MyInfo.getInstance().getNotifica();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
            SceneHandler.getInstance().setTheme();
        }
        initialize();
    }

    // Funzione che attiva il font in base alla check box
    @FXML
    void attivaDislessia(ActionEvent event) throws SQLException {
        if(checkDislessia.isSelected()){
            MyInfo.getInstance().setFont(MyInfo.getInstance().getVerdana());
            String info = MyInfo.getInstance().getTema()+";"+ MyInfo.getInstance().getFont()+";"+MyInfo.getInstance().getNotifica();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
        }else{
            MyInfo.getInstance().setFont(MyInfo.getInstance().getQuicksand());
            String info = MyInfo.getInstance().getTema()+";"+ MyInfo.getInstance().getFont()+";"+MyInfo.getInstance().getNotifica();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
        }
        initialize();
    }

    @FXML
    void attivaNotifica(ActionEvent event) throws SQLException {
        if(checkNotifica.isSelected()){
            MyInfo.getInstance().setNotifica(0);
            String info = MyInfo.getInstance().getTema()+";"+ MyInfo.getInstance().getFont()+";"+MyInfo.getInstance().getNotifica();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
        }else{
            MyInfo.getInstance().setNotifica(1);
            String info = MyInfo.getInstance().getTema()+";"+ MyInfo.getInstance().getFont()+";"+MyInfo.getInstance().getNotifica();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
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
        if(MyInfo.getInstance().getFont().equals("Verdana")){
            checkDislessia.setSelected(true);
        }
        if(MyInfo.getInstance().getNotifica() == 0){
            checkNotifica.setSelected(true);
        }

        if (temiComboBox.getItems().size() == 0){
            temiComboBox.getItems().add("Scuro");
            temiComboBox.getItems().add("Chiaro");
            temiComboBox.getItems().add("Blu");
        }
        String tema = MyInfo.getInstance().getTema();
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
