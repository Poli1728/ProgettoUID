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
import java.util.ArrayList;

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
            GestoreDB.getInstance().aggiornaTemplate(MyFont.getInstance().getTema(),MyFont.getInstance().getFont());
        }else if (temiComboBox.getSelectionModel().isSelected(1)){
            SceneHandler.getInstance().setTheme(Settings.theme.LIGHT);
            MyFont.getInstance().setTema("LIGHT");
            GestoreDB.getInstance().aggiornaTemplate(MyFont.getInstance().getTema(),MyFont.getInstance().getFont());
        }
        temiComboBox.getItems().clear();
        initialize();
    }

    @FXML
    void attivaDislessia(ActionEvent event) throws SQLException {
        if(checkDislessia.isSelected()){
            MyFont.getInstance().setFont(MyFont.getInstance().getCantarell());
            GestoreDB.getInstance().aggiornaTemplate(MyFont.getInstance().getTema(),MyFont.getInstance().getFont());
        }else{
            MyFont.getInstance().setFont(MyFont.getInstance().getQuicksand());
            GestoreDB.getInstance().aggiornaTemplate(MyFont.getInstance().getTema(),MyFont.getInstance().getFont());
        }
        initialize();
    }

    @FXML
    public void initialize(){
        if(MyFont.getInstance().getFont().equals("Cantarell")){
            checkDislessia.setSelected(true);
        }
        temiComboBox.getItems().add("Scuro");
        temiComboBox.getItems().add("Chiaro");
        impostaFontSize();
    }
}
