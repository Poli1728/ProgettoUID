package com.calendly.calendly.Controller;

import com.calendly.calendly.Main;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.View.CustomButton;
import com.calendly.calendly.View.Dialog;
import com.calendly.calendly.View.MyFont;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Objects;

public class HomeController {

    @FXML
    private AnchorPane anchorPaneParent;

    @FXML
    private Button appuntamentiButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button dipendentiButton;

    @FXML
    private Button impostazioniButton;

    @FXML
    private ImageView logoView;

    @FXML
    private Button logoutButton;

    @FXML
    private Button serviziButton;

    @FXML
    private Button statisticheButton;

    @FXML
    private Pane viewPane;
    // Pane dove carichiamo le varie view per la dashboard ecc ecc ~ Marco

    @FXML
    private VBox vbox;


    private void avviaPane(String s) throws IOException {
        viewPane.getChildren().clear();
        FXMLLoader loader = SceneHandler.getInstance().creaPane(s);
        Pane pane = (Pane) loader.load();

        pane.setPrefSize(viewPane.getWidth(), viewPane.getHeight());

        viewPane.layoutBoundsProperty().addListener(obs -> {
            pane.setPrefSize(viewPane.getWidth(), viewPane.getHeight());
        });

        Dialog.getInstance().setAnchorPaneFather(anchorPaneParent);
        viewPane.getChildren().add(pane);
    }
    public void refersh(String s) throws IOException {
        switch(s){
            case "Appuntamenti" ->{avviaPane("fxml/Appuntamenti");}
            case "Dashboard" ->{avviaPane("fxml/Dashboard");}
            case "Servizi" ->{avviaPane("fxml/Servizi");}
            case "Impostazioni" ->{avviaPane("fxml/Impostazioni");}
            case "Dipendenti" ->{avviaPane("fxml/Dipendenti");}
            case "Statistiche" ->{avviaPane("fxml/Statistiche");}
        }
    }
    @FXML
    void apriAppuntamenti(ActionEvent event) throws IOException {
        avviaPane("fxml/Appuntamenti");
    }

    @FXML
    void apriDashboard(ActionEvent event) throws IOException {
        avviaPane("fxml/Dashboard");
    }

    @FXML
    void apriServizi(ActionEvent event) throws IOException {
        avviaPane("fxml/Servizi");
    }

    @FXML
    void apriImpostazioni(ActionEvent event) throws IOException {
        avviaPane("fxml/Impostazioni");
    }

    @FXML
    void apriDipendenti(ActionEvent event) throws IOException {
        avviaPane("fxml/Dipendenti");
    }

    @FXML
    void apriStatistiche(ActionEvent event) throws IOException {
        avviaPane("fxml/Statistiche");
    }

    @FXML
    void esci(ActionEvent event) {
        viewPane.getChildren().clear();
        SceneHandler.getInstance().launchLogin();
    }

    private void impostaTemi(){
        appuntamentiButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        dashboardButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        dipendentiButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        logoutButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        impostazioniButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        serviziButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        statisticheButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
    }

    @FXML
    void initialize() throws IOException {
        impostaTemi();
        Image image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/logo.png")));
        logoView.setImage(image);

        int BUTTONS_WIDTH = 140;

        CustomButton dashboard = new CustomButton(anchorPaneParent, BUTTONS_WIDTH, "Dashboard", "img/dashboard.png");
        CustomButton appuntamenti = new CustomButton(anchorPaneParent, BUTTONS_WIDTH, "Appuntamenti", "img/dashboard.png");

        dashboard.getStyleClass().add("secondaryButton");
        appuntamenti.getStyleClass().add("secondaryButton");

        //todo da sostituire i bottoni che vengono aggiunti da scenebuilder con CustomButton

        vbox.getChildren().addAll(dashboard, appuntamenti);


        avviaPane("fxml/Dashboard");
    }


}
