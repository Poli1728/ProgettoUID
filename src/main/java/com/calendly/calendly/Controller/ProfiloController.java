package com.calendly.calendly.Controller;

import com.calendly.calendly.SceneHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class ProfiloController{

    @FXML
    private ImageView immagineView;

    @FXML
    private static TextField emailField;

    @FXML
    private static TextField indirizzoField;

    @FXML
    private static TextField nomeField;

    @FXML
    private static TextField numeroField;


    @FXML
    void aggiungiImmagine(ActionEvent event) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(SceneHandler.getInstance().apriFileChooser()));
        immagineView.setImage(image);
    }

    public static void setEmail(String email) {
        //emailField.setText(email);
    }

    public static void setIndirizzo(String indirizzo) {
        //indirizzoField.setText(indirizzo);
    }

    public static void setNome(String nome) {
        //nomeField.setText(nome);
    }

    public static void setNumero(String numero) {
        //numeroField.setText(numero);
    }

}
