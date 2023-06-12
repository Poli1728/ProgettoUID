package com.calendly.calendly.Controller;

import com.calendly.calendly.Main;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import com.calendly.calendly.View.CustomButton;
import com.calendly.calendly.View.Dialog;
import com.calendly.calendly.View.MyFont;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private AnchorPane anchorPaneImageView;

    @FXML
    private ImageView logoView;

    @FXML
    private Pane viewPane;
    // Pane dove carichiamo le varie view per la dashboard ecc ecc

    @FXML
    private VBox vbox;

    //Carica nel pane della home view le altre view

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
    public void refresh(String s) throws IOException {
        switch(s){
            case "Appuntamenti" ->{avviaPane("fxml/Appuntamenti");}
            case "Dashboard" ->{avviaPane("fxml/Dashboard");}
            case "Servizi" ->{avviaPane("fxml/Servizi");}
            case "Impostazioni" ->{avviaPane("fxml/Impostazioni");}
            case "Dipendenti" ->{avviaPane("fxml/Dipendenti");}
            case "Statistiche" ->{avviaPane("fxml/Statistiche");}
            case "Logout" -> SceneHandler.getInstance().launchLogin();
        }
    }

    private void impostaTemi(){
        /*appuntamentiButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        dashboardButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        dipendentiButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        logoutButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        impostazioniButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        serviziButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        statisticheButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));
        clientiButton.setFont(Font.font(MyFont.getInstance().getFont(), MyFont.getInstance().getSizeTxt()));*/
    }

    @FXML
    void initialize() throws IOException {
        impostaTemi();
        Image image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("img/logo.png")));
        logoView.setImage(image);

        int BUTTONS_WIDTH = 140;

        CustomButton dashboard = new CustomButton(anchorPaneParent, BUTTONS_WIDTH, "Dashboard", "img/dashboard.png");
        CustomButton appuntamenti = new CustomButton(anchorPaneParent, BUTTONS_WIDTH, "Appuntamenti", "img/event.png");
        CustomButton statistiche = new CustomButton(anchorPaneParent, BUTTONS_WIDTH, "Statistiche", "img/stats.png");
        CustomButton servizi = new CustomButton(anchorPaneParent, BUTTONS_WIDTH, "Servizi", "img/task.png");
        CustomButton clienti = new CustomButton(anchorPaneParent, BUTTONS_WIDTH, "Clienti", "img/clients.png");
        CustomButton dipendenti = new CustomButton(anchorPaneParent, BUTTONS_WIDTH, "Dipendenti", "img/employee.png");
        CustomButton impostazioni = new CustomButton(anchorPaneParent, BUTTONS_WIDTH, "Impostazioni", "img/settings.png");
        CustomButton logout = new CustomButton(anchorPaneParent, BUTTONS_WIDTH, "Logout", "img/logout.png");

        dashboard.getStyleClass().add("sidebar-button");
        appuntamenti.getStyleClass().add("sidebar-button");
        statistiche.getStyleClass().add("sidebar-button");
        servizi.getStyleClass().add("sidebar-button");
        clienti.getStyleClass().add("sidebar-button");
        dipendenti.getStyleClass().add("sidebar-button");
        impostazioni.getStyleClass().add("sidebar-button");
        logout.getStyleClass().add("sidebar-logout-button");

        clickButtonAction(dashboard, appuntamenti, statistiche, servizi, clienti, dipendenti, impostazioni, logout);


        //todo aggiungere un listner all'apfather per modificare la grandezza del right pane
        //todo da sostituire i bottoni che vengono aggiunti da scenebuilder con CustomButton

        vbox.getChildren().addAll(dashboard, appuntamenti, statistiche, servizi, clienti, dipendenti, impostazioni, logout);


        logoView.setPreserveRatio(true);
        anchorPaneParent.widthProperty().addListener(((observableValue, oldValue, newValue) -> {
            System.out.println(newValue.doubleValue());
            if (newValue.doubleValue() >= Settings.WIDTH_BREAKPOINT) {
                logoView.setFitWidth(BUTTONS_WIDTH);
                logoView.setFitHeight(BUTTONS_WIDTH);
                AnchorPane.setLeftAnchor(viewPane, vbox.getLayoutX()*2 + BUTTONS_WIDTH);
            } else {
                logoView.setFitWidth(50);
                logoView.setFitWidth(50);
                AnchorPane.setLeftAnchor(viewPane, vbox.getLayoutX()*2 + dashboard.getWidth());            }
        }));

        avviaPane("fxml/Dashboard");
    }


    private void clickButtonAction(CustomButton ... button) {
        for(int i = 0; i < button.length; i++) {
            int iCopy = i;
            button[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (button[iCopy].getButtonText().trim().equals("Logout")) {
                        SceneHandler.getInstance().launchLogin();
                        return;
                    }
                    try {
                        avviaPane("fxml/" + button[iCopy].getButtonText().trim());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }


}
