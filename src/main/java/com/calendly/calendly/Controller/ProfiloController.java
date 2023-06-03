package com.calendly.calendly.Controller;

import com.calendly.calendly.Main;
import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class ProfiloController {

    @FXML
    private ImageView immagineView;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelIndirizzo;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelNumero;

    @FXML
    void aggiungiImmagine(ActionEvent event) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(SceneHandler.getInstance().apriFileChooser()));
        immagineView.setImage(image);
    }

}
