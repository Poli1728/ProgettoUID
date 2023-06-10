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
    private CheckBox checkDislessia;

    @FXML
    private Label labelDislessia;

    @FXML
    private Label labelTemi;
    @FXML
    private Text txtDislessia;


    private void impostaFontSize(){
        labelImpostazioni.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        labelDislessia.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        labelTemi.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        txtTemi.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        txtDislessia.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
    }

    @FXML
    void scegliTema(ActionEvent event) throws SQLException {
        if (temiComboBox.getSelectionModel().isSelected(0)){
            SceneHandler.getInstance().setTheme(Settings.theme.DARK);
            MyFont.getInstance().setTema("DARK");
            String info = MyFont.getInstance().getTema()+";"+MyFont.getInstance().getFont();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
        }else if (temiComboBox.getSelectionModel().isSelected(1)){
            SceneHandler.getInstance().setTheme(Settings.theme.LIGHT);
            MyFont.getInstance().setTema("LIGHT");
            String info = MyFont.getInstance().getTema()+";"+MyFont.getInstance().getFont();
            GestoreDB.getInstance().aggiornamento(GestoreDB.getInstance().getTemplate(), info.split(";"));
        }
        initialize();
    }

    @FXML
    void attivaDislessia(ActionEvent event) throws SQLException {
        if(checkDislessia.isSelected()){
            MyFont.getInstance().setFont(MyFont.getInstance().getCantarell());
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
    public void initialize(){
        if(MyFont.getInstance().getFont().equals("Cantarell")){
            checkDislessia.setSelected(true);
        }

        if (temiComboBox.getItems().size() == 0){

            temiComboBox.getItems().add("Scuro");
            temiComboBox.getItems().add("Chiaro");
        }
        String tema = MyFont.getInstance().getTema();
        if (tema.equals("DARK")){
            temiComboBox.setValue("Scuro");
        }else if(tema.equals("LIGHT")){
            temiComboBox.setValue("Chiaro");
        }
        impostaFontSize();
    }
}
