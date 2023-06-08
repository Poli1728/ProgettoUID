package com.calendly.calendly.Controller;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class ImpostazioniController implements Initializable {

    @FXML
    private Slider slideBar;

    @FXML
    private Label labelTema;

    @FXML
    private ComboBox<String> temiComboBox;

    @FXML
    private Text txtTemi;

    @FXML
    private CheckBox checkDislessia;
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
            Font font = Font.loadFont("/home/marco/Documenti/GitHub/Calendly/src/main/resources/com/calendly/calendly/font/OpenDyslexicAlta-Italic.otf", 24);
            //txtTemi.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            txtTemi.setFont(font);
        }else{
            System.out.println("Puzzi");
        }
    }
    @FXML
    void cambiaGrandezza(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        temiComboBox.getItems().add("Scuro");
        temiComboBox.getItems().add("Chiaro");
        txtTemi.setFont(Font.font("Cantarell"));
    }
}
