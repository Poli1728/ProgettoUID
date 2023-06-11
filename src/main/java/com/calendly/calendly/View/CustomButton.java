package com.calendly.calendly.View;

import com.calendly.calendly.Main;
import com.calendly.calendly.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.Objects;

public class CustomButton extends Button {
    public enum prefers {IMAGE_ONLY, BASED_ON_WINDOW_SIZE, ALWAYS_BOTH}

    private prefers preferenza;
    private String buttonText;
    private String imagePath;
    private String[] buttonDescription;


    private Node parent;
    private int buttonWidth;

    //Solo Immagini
    public CustomButton(String buttonText, String imagePath, String ... buttonDescription) {
        super();
        this.buttonText = buttonText;
        this.imagePath = imagePath;
        this.buttonDescription = buttonDescription;

        setButtonImageOnly();
    }

    //Basato sulla grandezze del nodo padre mostra entrambi o solo immagine
    public CustomButton(Node parent, int buttonWidth, String buttonText, String imagePath, String ... buttonDescription) {
        super();
        this.parent = parent;
        this.buttonWidth = buttonWidth;
        this.buttonText = " " + buttonText;
        this.imagePath = imagePath;
        this.buttonDescription = buttonDescription;

        setButtonBasedOnWindowSize();
    }




    private void setButtonBasedOnWindowSize() {
        setImageInButton(true);

        this.setAlignment(Pos.BASELINE_LEFT);

        if (parent.getClass().equals(AnchorPane.class)) {
            AnchorPane ap = (AnchorPane) parent;
            ap.widthProperty().addListener(((observableValue, oldValue, newValue) -> {
                if (newValue.doubleValue() < Settings.WIDTH_BREAKPOINT) {
                    this.setText("");
                    this.setPrefWidth(5);
                }
                else {
                    this.setText(buttonText);
                    this.setPrefWidth(buttonWidth);
                    this.setPadding(new Insets(
                                    this.getGraphic().getBoundsInLocal().getHeight()/3,
                                    10,
                                    this.getGraphic().getBoundsInLocal().getHeight()/3,
                                    10)
                    );
                }
            }));
        }


        setButtonDescription();
    }

    private void setButtonImageOnly() {
        setImageInButton(false);
        setButtonDescription();
    }







    // --------------------------------------


    private void setButtonDescription() {
        if (buttonDescription.length == 0)
            return;

        Tooltip tooltip = new Tooltip(buttonText.toUpperCase() + "\n" + buttonDescription[0]);
        tooltip.setFont(new Font(14));
        this.setTooltip(tooltip);

        if (buttonDescription.length > 1)
            System.out.println("Attenzione. La descrizione del bottone usa solo il primo parametro opzionale");
    }


    private void setImageInButton(boolean addRightPadding) {

        ImageView imageView = new ImageView();
        try {
            Image image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(imagePath)));
            imageView.setImage(image);
            imageView.setFitWidth(20);
            imageView.setFitWidth(20);
            imageView.setPreserveRatio(true);

            if (addRightPadding) {
                //todo aggiungere padding a destra dell'immagine
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        this.setGraphic(imageView);
    }


}
