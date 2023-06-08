package com.calendly.calendly.Controller;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import com.calendly.calendly.View.MyFont;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ImpostazioniController {

    @FXML
    private Label labelImpostazioni;

    @FXML
    private Slider slideBar;

    @FXML
    private ComboBox<String> temiComboBox;

    @FXML
    private Text txtTemi;

    @FXML
    private CheckBox checkDislessia;

    @FXML
    private Label labelDislessia;

    @FXML
    private Label labelSize;

    @FXML
    private Label labelTemi;
    @FXML
    private Text txtDislessia;

    @FXML
    private Text txtSize;

    private void impostaFontSize(){
        labelImpostazioni.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        labelSize.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        labelDislessia.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        labelTemi.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeLabel()));
        txtTemi.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        txtSize.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        txtDislessia.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
    }

    @FXML
    void esci(ActionEvent event) {
        SceneHandler.getInstance().launchLogin();
    }

    @FXML
    void scegliTema(ActionEvent event) {
        if (temiComboBox.getSelectionModel().isSelected(0)){
            SceneHandler.getInstance().setTheme(Settings.theme.DARK);
        }else if (temiComboBox.getSelectionModel().isSelected(1)){
            SceneHandler.getInstance().setTheme(Settings.theme.LIGHT);
        }
    }

    @FXML
    void attivaDislessia(ActionEvent event) {
        if(checkDislessia.isSelected()){
            MyFont.getInstance().setFont(MyFont.getInstance().getCantarell());
        }else{
            MyFont.getInstance().setFont(MyFont.getInstance().getQuicksand());
        }
        initialize();
    }

    @FXML
    void cambiaGrandezza(ActionEvent event) {
        MyFont.getInstance().setSizeTxt((int)slideBar.getValue());
        MyFont.getInstance().setSizeLabel((int)slideBar.getValue());
        initialize();
    }

    @FXML
    public void initialize() {
        if(MyFont.getInstance().getFont().equals("Cantarell")){
            checkDislessia.setSelected(true);
        }
        slideBar.setValue(MyFont.getInstance().getSizeTxt());
        temiComboBox.getItems().add("Scuro");
        temiComboBox.getItems().add("Chiaro");
        impostaFontSize();
    }
}
