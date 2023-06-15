package com.calendly.calendly.Controller;

import com.calendly.calendly.Main;
import com.calendly.calendly.SceneHandler;
import com.calendly.calendly.Settings;
import com.calendly.calendly.View.CustomButton;
import com.calendly.calendly.View.Dialog;
import com.calendly.calendly.View.MyInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public class HomeController {

    @FXML
    private AnchorPane anchorPaneParent;

    @FXML
    private AnchorPane anchorPaneImageView;

    @FXML
    private Label labelInfo;

    @FXML
    private ImageView logoView;

    @FXML
    private Text txtInfo;

    @FXML
    private Text txtInfo1;

    @FXML
    private Text txtInfo2;

    @FXML
    private Text txtInfo3;

    @FXML
    private Text txtInfo4;

    @FXML
    private Text txtInfo5;

    @FXML
    private Text txtInfo6;

    @FXML
    private Text txtInfo7;

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

        SceneHandler.getInstance().setRightPaneContainerContent(pane);

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

    private void impostaTemi() throws IOException {
        if(MyInfo.getInstance().getFont().equals("Dyslexie")){
            labelInfo.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeLabel()-1));
            txtInfo.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-2.5));
            txtInfo1.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-2.5));
            txtInfo2.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-4));
            txtInfo3.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-3));
            txtInfo4.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-2.5));
            txtInfo5.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-2.5));
            txtInfo6.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-2.5));
            txtInfo7.setFont(Font.loadFont(MyInfo.getInstance().getFontDyslexia(),MyInfo.getInstance().getSizeTxt()-2.5));
        }else{
            labelInfo.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeLabel()));
            txtInfo.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
            txtInfo1.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
            txtInfo2.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
            txtInfo3.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
            txtInfo4.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
            txtInfo5.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
            txtInfo6.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
            txtInfo7.setFont(Font.font(MyInfo.getInstance().getFontQuicksand(),MyInfo.getInstance().getSizeTxt()));
        }
    }

    @FXML
    void initialize() throws IOException{
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





        vbox.getChildren().addAll(dashboard, appuntamenti, statistiche, servizi, clienti, dipendenti, impostazioni, logout);


        logoView.setPreserveRatio(true);
        anchorPaneParent.widthProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue.doubleValue() >= Settings.WIDTH_BREAKPOINT) {
                logoView.setFitWidth(BUTTONS_WIDTH);
                logoView.setFitHeight(BUTTONS_WIDTH);
                AnchorPane.setLeftAnchor(viewPane, vbox.getLayoutX()*2 + BUTTONS_WIDTH);
            } else {
                logoView.setFitWidth(dashboard.getWidth());
                logoView.setFitWidth(dashboard.getWidth());
                AnchorPane.setLeftAnchor(viewPane, vbox.getLayoutX()*2 + dashboard.getWidth());            }
        }));

        anchorPaneParent.layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            if (vbox.getHeight() != 0)
                AnchorPane.setTopAnchor(vbox, (anchorPaneParent.getHeight() - vbox.getHeight())/2);
        });
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
